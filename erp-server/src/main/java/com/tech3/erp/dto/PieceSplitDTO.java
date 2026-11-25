package com.tech3.erp.dto;

import com.tech3.erp.entity.PieceSplit;
import com.tech3.erp.entity.PieceSplitDetail;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PieceSplitDTO {
    private Long id;
    private Integer operationId;
    private Date operationDate;
    private Long companyId;
    private String pieceSplitNo;
    private String parentPieceNo;
    private String parentPieceMeter;
    private Long loomId;
    private Long flangeId;
    private String flangeNo;
    private Long beamId;
    private String beamNo;
    private Long fabricMasterId;
    private Integer noOfPieces;
    private Long modeId;
    private List<PieceSplitDetailDTO> splitPiecesDetails;

    public PieceSplitDTO() {}

    public PieceSplitDTO(PieceSplit entity) {
        this.id = entity.getId();
        this.operationId = entity.getOperationId();
        this.operationDate = entity.getOperationDate();
        this.companyId = entity.getCompanyId();
        this.pieceSplitNo = entity.getPieceSplitNo();
        this.parentPieceNo = entity.getParentPieceNo();
        this.parentPieceMeter = entity.getParentPieceMeter();
        this.loomId = entity.getLoomId();
        this.flangeId = entity.getFlangeId();
        this.flangeNo = entity.getFlangeNo();
        this.beamId = entity.getBeamId();
        this.beamNo = entity.getBeamNo();
        this.fabricMasterId = entity.getFabricMasterId();
        this.noOfPieces = entity.getNoOfPieces();
        this.modeId = entity.getModeId();
        if (entity.getSplitPiecesDetails() != null) {
            this.splitPiecesDetails = entity.getSplitPiecesDetails().stream()
                    .map(PieceSplitDetailDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getOperationId() { return operationId; }
    public void setOperationId(Integer operationId) { this.operationId = operationId; }

    public Date getOperationDate() { return operationDate; }
    public void setOperationDate(Date operationDate) { this.operationDate = operationDate; }

    public Long getCompanyId() { return companyId; }
    public void setCompanyId(Long companyId) { this.companyId = companyId; }

    public String getPieceSplitNo() { return pieceSplitNo; }
    public void setPieceSplitNo(String pieceSplitNo) { this.pieceSplitNo = pieceSplitNo; }

    public String getParentPieceNo() { return parentPieceNo; }
    public void setParentPieceNo(String parentPieceNo) { this.parentPieceNo = parentPieceNo; }

    public String getParentPieceMeter() { return parentPieceMeter; }
    public void setParentPieceMeter(String parentPieceMeter) { this.parentPieceMeter = parentPieceMeter; }

    public Long getLoomId() { return loomId; }
    public void setLoomId(Long loomId) { this.loomId = loomId; }

    public Long getFlangeId() { return flangeId; }
    public void setFlangeId(Long flangeId) { this.flangeId = flangeId; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public Long getBeamId() { return beamId; }
    public void setBeamId(Long beamId) { this.beamId = beamId; }

    public String getBeamNo() { return beamNo; }
    public void setBeamNo(String beamNo) { this.beamNo = beamNo; }

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public Integer getNoOfPieces() { return noOfPieces; }
    public void setNoOfPieces(Integer noOfPieces) { this.noOfPieces = noOfPieces; }

    public List<PieceSplitDetailDTO> getSplitPiecesDetails() { return splitPiecesDetails; }
    public void setSplitPiecesDetails(List<PieceSplitDetailDTO> splitPiecesDetails) { this.splitPiecesDetails = splitPiecesDetails; }

    public Long getModeId() { return modeId; }
    public void setModeId(Long modeId) { this.modeId = modeId; }
    
}
