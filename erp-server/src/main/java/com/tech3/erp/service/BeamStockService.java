package com.tech3.erp.service;

import com.tech3.erp.dto.BeamStockDTO;
import java.util.List;

public interface BeamStockService {
    BeamStockDTO saveBeamStock(BeamStockDTO dto);
    BeamStockDTO.BulkUploadResponse saveBulkBeamStock(BeamStockDTO.BulkUploadRequest request);
    BeamStockDTO.BulkUploadResponse updateOnLoomMtrBulk(BeamStockDTO.OnLoomMtrUpdateRequest request);
    List<BeamStockDTO> getAllBeamStock();
    Object getAllBeamStockWithPagination(Long offset, Long limit);
    List<BeamStockDTO> getAllBeamStockByCompany(Long companyId);
    Object getAllBeamStockByCompanyWithPagination(Long companyId, Long offset, Long limit);
    BeamStockDTO getBeamStockById(Long id);
    BeamStockDTO updateBeamStock(Long id, BeamStockDTO dto);
    void deleteBeamStock(Long id);
}
