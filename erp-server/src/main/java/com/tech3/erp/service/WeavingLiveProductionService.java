package com.tech3.erp.service;

import com.tech3.erp.dto.WeavingLiveProductionBulkUploadDTO;
import com.tech3.erp.dto.WeavingLiveProductionDTO;

import java.util.List;

public interface WeavingLiveProductionService {

    List<WeavingLiveProductionDTO> bulkUpload(WeavingLiveProductionBulkUploadDTO bulkUploadDTO);

    List<WeavingLiveProductionDTO> getByCompanyId(Long companyId);

    WeavingLiveProductionDTO create(WeavingLiveProductionDTO dto);

    WeavingLiveProductionDTO getById(Long id);

    void delete(Long id);
}
