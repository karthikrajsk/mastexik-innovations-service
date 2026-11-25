package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "piece_join", schema = "masters")
public class PieceJoin {

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

    @Column(name = "piece_join_no")
    private String pieceJoinNo;

    @Column(name = "loom_id")
    private Long loomId;

    @Column(name = "flange_id")
    private Long flangeId;

    @Column(name = "flange_no", length = 100)
    private String flangeNo;

    @Column(name = "beam_id")
    private Long beamId;

    @Column(name = "beam_no", length = 100)
    private String beamNo;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "joined_piece_no", length = 1024)
    private String joinedPieceNo;

    @Column(name = "joined_piece_meter", length = 100)
    private String joinedPieceMeter;

    @Column(name = "is_factory_fabric")
    private Integer isFactoryFabric;

    @Column(name = "mode_id")
    private Long modeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @OneToMany(mappedBy = "pieceJoin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PieceJoinDetail> joinPiecesDetails = new ArrayList<>();

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

    public Long getModeId() { return modeId; }
    public void setModeId(Long modeId) { this.modeId = modeId; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public List<PieceJoinDetail> getJoinPiecesDetails() { return joinPiecesDetails; }
    public void setJoinPiecesDetails(List<PieceJoinDetail> joinPiecesDetails) { this.joinPiecesDetails = joinPiecesDetails; }
}
