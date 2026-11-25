package com.tech3.erp.service;

import com.tech3.erp.dto.CodesDetailDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.repository.CodesDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreenCodeService {

    private static final String SCREEN_CODE_GROUP = "Complete Status";
    
    @Autowired
    private CodesDetailRepository codesDetailRepository;

    public List<CodesDetailDTO> getScreenCodes() {
        List<CodesDetail> screenCodes = codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue(SCREEN_CODE_GROUP);
        return screenCodes.stream()
                .sorted(Comparator.comparingInt(o -> o.getSortOrder() != null ? o.getSortOrder() : 0))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public String getScreenNameByCode(String code) {
        List<CodesDetail> screenCodes = codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue(SCREEN_CODE_GROUP);
        return screenCodes.stream()
                .filter(sc -> sc.getCode().equals(code))
                .findFirst()
                .map(CodesDetail::getValue)
                .orElse(code);
    }

    public String getCodeByValue(String value) {
        List<CodesDetail> screenCodes = codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue(SCREEN_CODE_GROUP);
        return screenCodes.stream()
                .filter(sc -> sc.getValue().equals(value))
                .findFirst()
                .map(CodesDetail::getCode)
                .orElse(value);
    }

    public Long getScreenIdByCode(String code) {
        return codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue(SCREEN_CODE_GROUP).stream()
                .filter(screenCode -> screenCode.getCode().equals(code))
                .findFirst()
                .map(CodesDetail::getId)
                .orElseThrow(() -> new IllegalStateException("Screen code is not configured: " + code));
    }

    private CodesDetailDTO toDTO(CodesDetail entity) {
        return new CodesDetailDTO(
                entity.getId(),
                entity.getCode(),
                entity.getValue(),
                entity.getSortOrder(),
                entity.getIsActive()
        );
    }
}
