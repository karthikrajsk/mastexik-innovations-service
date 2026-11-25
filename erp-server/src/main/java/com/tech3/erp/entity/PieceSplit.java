package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "piece_split", schema = "masters")
public class PieceSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "operation_id")
    private Integer operationId;

    @Temporal(TemporalType.DATE)
    @Column(name = "operation_date")
    private Date operationDate;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "piece_split_no")
    private String pieceSplitNo;

    @Column(name = "parent_piece_no", length = 100)
    private String parentPieceNo;

    @Column(name = "parent_piece_meter", length = 50)
    private String parentPieceMeter;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "flange_no", length = 100)
    private String flangeNo;

    @Column(name = "beam_id")
    private Long beamId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "beam_no", length = 100)
    private String beamNo;

    @Column(name = "no_of_pieces")
    private Integer noOfPieces;

    @Column(name = "mode_id")
    private Long modeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @OneToMany(mappedBy = "pieceSplit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PieceSplitDetail> splitPiecesDetails = new ArrayList<>();

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

    public Long getFabricMasterId() { return fabricMasterId; }
    public void setFabricMasterId(Long fabricMasterId) { this.fabricMasterId = fabricMasterId; }

    public String getBeamNo() { return beamNo; }
    public void setBeamNo(String beamNo) { this.beamNo = beamNo; }

    public Integer getNoOfPieces() { return noOfPieces; }
    public void setNoOfPieces(Integer noOfPieces) { this.noOfPieces = noOfPieces; }

    public Long getModeId() { return modeId; }
    public void setModeId(Long modeId) { this.modeId = modeId; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public List<PieceSplitDetail> getSplitPiecesDetails() { return splitPiecesDetails; }
    public void setSplitPiecesDetails(List<PieceSplitDetail> splitPiecesDetails) { this.splitPiecesDetails = splitPiecesDetails; }
}
