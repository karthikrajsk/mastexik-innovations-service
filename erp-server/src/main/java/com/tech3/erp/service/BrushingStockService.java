package com.tech3.erp.service;

import com.tech3.erp.dto.BrushingStockDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.BrushingStock;
import com.tech3.erp.repository.BrushingStockRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrushingStockService {

    private final BrushingStockRepository brushingStockRepository;
    private final com.tech3.erp.repository.BrushingReceiveDetailRepository brushingReceiveDetailRepository;

    public BrushingStockService(BrushingStockRepository brushingStockRepository,
                                com.tech3.erp.repository.BrushingReceiveDetailRepository brushingReceiveDetailRepository) {
        this.brushingStockRepository = brushingStockRepository;
        this.brushingReceiveDetailRepository = brushingReceiveDetailRepository;
    }

    public List<BrushingStockDTO> getBrushingStockByCompany(Long companyId) {
        return brushingStockRepository.findByCompanyId(companyId)
                .stream()
                .sorted(Comparator.comparing(BrushingStock::getId, Comparator.reverseOrder()))
                .map(BrushingStockDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BrushingStockDTO> getBrushingStockByCompany(Long companyId, Long offset, Long limit, String fabricStatus) {
        if (offset == null || limit == null) {
            List<BrushingStock> all;
            if (fabricStatus != null && !fabricStatus.trim().isEmpty() && !"ALL".equalsIgnoreCase(fabricStatus)) {
                all = brushingStockRepository.findByCompanyId(companyId).stream()
                        .filter(item -> fabricStatus.equalsIgnoreCase(item.getFabricStatus()))
                        .collect(Collectors.toList());
            } else {
                all = brushingStockRepository.findByCompanyId(companyId);
            }
            all.sort(Comparator.comparing(BrushingStock::getId).reversed());
            List<BrushingStockDTO> dtos = all.stream().map(BrushingStockDTO::new).collect(Collectors.toList());
            return new PaginatedResponse<>(
                    (long) dtos.size(),
                    false,
                    false,
                    0L,
                    (long) dtos.size(),
                    dtos
            );
        }

        int pageIndex = (int) (offset / limit);
        Pageable pageable = PageRequest.of(pageIndex, limit.intValue(), Sort.by("id").descending());

        Page<BrushingStock> page;
        if (fabricStatus != null && !fabricStatus.trim().isEmpty() && !"ALL".equalsIgnoreCase(fabricStatus)) {
            page = brushingStockRepository.findByCompanyIdAndFabricStatus(companyId, fabricStatus, pageable);
        } else {
            page = brushingStockRepository.findByCompanyId(companyId, pageable);
        }

        List<BrushingStockDTO> content = page.getContent().stream()
                .map(BrushingStockDTO::new)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious(),
                offset,
                limit,
                content
        );
    }

    @Transactional
    public List<BrushingStockDTO> saveBulk(List<BrushingStockDTO> dtos) {
        List<BrushingStock> entities = dtos.stream()
                .map(BrushingStockDTO::toEntity)
                .collect(Collectors.toList());

        List<BrushingStock> savedEntities = new ArrayList<>();
        int batchSize = 500;
        for (int i = 0; i < entities.size(); i += batchSize) {
            List<BrushingStock> batch = entities.subList(i, Math.min(i + batchSize, entities.size()));
            List<BrushingStock> savedBatch = brushingStockRepository.saveAll(batch);
            brushingStockRepository.flush(); // Flush each batch to save DB memory and avoid parameter limits
            savedEntities.addAll(savedBatch);
        }

        // Update BrushingReceiveDetail rows if fabricStatus is "DP" (Dispatched)
        List<String> dispatchedPieceNumbers = dtos.stream()
                .filter(dto -> "DP".equalsIgnoreCase(dto.getFabricStatus()) && dto.getRollNo() != null && !dto.getRollNo().trim().isEmpty())
                .map(dto -> dto.getRollNo().trim())
                .distinct()
                .collect(Collectors.toList());

        if (!dtos.isEmpty() && !dispatchedPieceNumbers.isEmpty()) {
            Long companyId = dtos.get(0).getCompanyId();
            List<com.tech3.erp.entity.BrushingReceiveDetail> receiveDetails = brushingReceiveDetailRepository
                    .findByPieceNumberInAndBrushingReceiveCompanyId(dispatchedPieceNumbers, companyId);

            java.util.Set<com.tech3.erp.entity.BrushingReceiveDetail> toUpdate = new java.util.HashSet<>();

            for (BrushingStockDTO dto : dtos) {
                if (!"DP".equalsIgnoreCase(dto.getFabricStatus())) {
                    continue;
                }
                String dRollNo = dto.getRollNo() != null ? dto.getRollNo().trim() : "";
                java.math.BigDecimal dRecMtr = dto.getReceivingMtr() != null ? dto.getReceivingMtr() : java.math.BigDecimal.ZERO;
                String dHoInvoice = dto.getHoInvoiceNo() != null ? dto.getHoInvoiceNo().trim() : "";

                if (dRollNo.isEmpty()) {
                    continue;
                }

                // Find matching rows in receiveDetails
                for (com.tech3.erp.entity.BrushingReceiveDetail detail : receiveDetails) {
                    String rPieceNo = detail.getPieceNumber() != null ? detail.getPieceNumber().trim() : "";
                    java.math.BigDecimal rGreigeMtr = detail.getGreigeMeters() != null ? detail.getGreigeMeters() : java.math.BigDecimal.ZERO;
                    String rInvoiceNo = detail.getInvoiceNo() != null ? detail.getInvoiceNo().trim() : "";

                    boolean rollNoMatches = rPieceNo.equalsIgnoreCase(dRollNo);
                    boolean mtrMatches = rGreigeMtr.compareTo(dRecMtr) == 0;
                    boolean invoiceMatches = rInvoiceNo.equalsIgnoreCase(dHoInvoice)
                            || rInvoiceNo.isEmpty()
                            || dHoInvoice.isEmpty();

                    if (rollNoMatches && mtrMatches && invoiceMatches) {
                        detail.setIsDispatched(1);
                        detail.setIsInvoice(1);
                        detail.setBrushingStatus("DISPATCHED");
                        detail.setDispatchDate(dto.getDispatchDate() != null ? dto.getDispatchDate() : java.time.LocalDate.now());
                        if ((detail.getInvoiceNo() == null || detail.getInvoiceNo().trim().isEmpty()) && !dHoInvoice.isEmpty()) {
                            detail.setInvoiceNo(dHoInvoice);
                        }
                        toUpdate.add(detail);
                    }
                }
            }

            if (!toUpdate.isEmpty()) {
                brushingReceiveDetailRepository.saveAll(toUpdate);
            }
        }

        return savedEntities.stream()
                .map(BrushingStockDTO::new)
                .collect(Collectors.toList());
    }
}
