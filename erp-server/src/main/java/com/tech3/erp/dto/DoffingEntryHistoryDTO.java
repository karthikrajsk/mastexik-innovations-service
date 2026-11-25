package com.tech3.erp.dto;

import java.math.BigDecimal;

public record DoffingEntryHistoryDTO(
        Long id,
        String pieceNumber,
        Integer pieceNo,
        Long loomId,
        Long beamId,
        String beamNo,
        String pieceMeter,
        BigDecimal remainingMtr,
        Integer isFactoryFabric,
        Integer isLastPiece) {
}
