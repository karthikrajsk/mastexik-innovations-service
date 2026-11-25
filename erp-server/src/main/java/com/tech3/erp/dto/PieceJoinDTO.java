package com.tech3.erp.dto;

import com.tech3.erp.entity.PieceJoin;
import com.tech3.erp.entity.PieceJoinDetail;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PieceJoinDTO {
    private Long id;
    private Integer operationId;
    private Date operationDate;
    private Long companyId;
    private String pieceJoinNo;
    private Long loomId;
    private Long flangeId;
    private String flangeNo;
    private Long beamId;
    private String beamNo;
    private Long fabricMasterId;
    private String joinedPieceNo;
    private String joinedPieceMeter;
    private Integer isFactoryFabric;
    private Long modeId;
    private List<PieceJoinDetailDTO> joinPiecesDetails;

    public PieceJoinDTO() {}

    public PieceJoinDTO(PieceJoin entity) {
        this.id = entity.getId();
        this.operationId = entity.getOperationId();
        this.operationDate = entity.getOperationDate();
        this.companyId = entity.getCompanyId();
        this.pieceJoinNo = entity.getPieceJoinNo();
        this.loomId = entity.getLoomId();
        this.flangeId = entity.getFlangeId();
        this.flangeNo = entity.getFlangeNo();
        this.beamId = entity.getBeamId();
        this.beamNo = entity.getBeamNo();
        this.fabricMasterId = entity.getFabricMasterId();
        this.joinedPieceNo = entity.getJoinedPieceNo();
        this.joinedPieceMeter = entity.getJoinedPieceMeter();
        this.isFactoryFabric = entity.getIsFactoryFabric();
        this.modeId = entity.getModeId();
        if (entity.getJoinPiecesDetails() != null) {
            this.joinPiecesDetails = entity.getJoinPiecesDetails().stream()
                    .map(PieceJoinDetailDTO::new)
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

    public String getPieceJoinNo() { return pieceJoinNo; }
    public void setPieceJoinNo(String pieceJoinNo) { this.pieceJoinNo = pieceJoinNo; }

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

    public String getJoinedPieceNo() { return joinedPieceNo; }
    public void setJoinedPieceNo(String joinedPieceNo) { this.joinedPieceNo = joinedPieceNo; }

    public String getJoinedPieceMeter() { return joinedPieceMeter; }
    public void setJoinedPieceMeter(String joinedPieceMeter) { this.joinedPieceMeter = joinedPieceMeter; }

    public Integer getIsFactoryFabric() { return isFactoryFabric; }
    public void setIsFactoryFabric(Integer isFactoryFabric) { this.isFactoryFabric = isFactoryFabric; }

    public List<PieceJoinDetailDTO> getJoinPiecesDetails() { return joinPiecesDetails; }
    public void setJoinPiecesDetails(List<PieceJoinDetailDTO> joinPiecesDetails) { this.joinPiecesDetails = joinPiecesDetails; }

    public Long getModeId() { return modeId; }
    public void setModeId(Long modeId) { this.modeId = modeId; }
}
