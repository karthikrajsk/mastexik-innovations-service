package com.tech3.erp.service;

import com.tech3.erp.dto.NotificationDTO;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.Notification;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public static final String EVENT_WEAVING_JOBWORK          = "WEAVING_JOBWORK";
    public static final String EVENT_WEFT_INWARD              = "WEFT_INWARD";
    public static final String EVENT_SIZING_JOBWORK           = "SIZING_JOBWORK";
    public static final String EVENT_SIZING_YARN_INWARD       = "SIZING_YARN_INWARD";
    public static final String EVENT_PURCHASE_INWARD_BALANCE_ZERO = "PURCHASE_INWARD_BALANCE_ZERO";
    public static final String EVENT_SIZING_PLAN_BALANCE_ZERO = "SIZING_PLAN_BALANCE_ZERO";
    public static final String EVENT_WEAVING_CONTRACT_BALANCE_ZERO = "WEAVING_CONTRACT_BALANCE_ZERO";

    private static final Map<String, String> SCREEN_PATHS = new HashMap<>();
    static {
        SCREEN_PATHS.put(EVENT_WEAVING_JOBWORK,                  "/transaction/weaving-jobwork-order-details");
        SCREEN_PATHS.put(EVENT_WEFT_INWARD,                      "/transaction/weft-inward-details");
        SCREEN_PATHS.put(EVENT_SIZING_JOBWORK,                   "/transaction/sizing-jobwork-order-details");
        SCREEN_PATHS.put(EVENT_SIZING_YARN_INWARD,               "/transaction/sizing-yarn-inward-details");
        SCREEN_PATHS.put(EVENT_PURCHASE_INWARD_BALANCE_ZERO,     "/transaction/purchase-order-details");
        SCREEN_PATHS.put(EVENT_SIZING_PLAN_BALANCE_ZERO,         "/transaction/sizing-plan-details");
        SCREEN_PATHS.put(EVENT_WEAVING_CONTRACT_BALANCE_ZERO,    "/transaction/weaving-contract-details");
    }

    public void createAndPush(Long targetCompanyId, Long tradingCompanyId,
                              String eventType, Long referenceId,
                              BigDecimal quantity, String unit) {
        String tradingCompanyName = resolveCompanyName(tradingCompanyId);
        String message = buildMessage(eventType, quantity, unit, tradingCompanyName);

        Notification notification = new Notification();
        notification.setCompanyId(targetCompanyId);
        notification.setTradingCompanyId(tradingCompanyId);
        notification.setTradingCompanyName(tradingCompanyName);
        notification.setEventType(eventType);
        notification.setQuantity(quantity);
        notification.setUnit(unit);
        notification.setMessage(message);
        notification.setReferenceId(referenceId);
        notification.setScreenPath(SCREEN_PATHS.getOrDefault(eventType, "/"));
        notification.setIsRead(false);

        Notification saved = notificationRepository.save(notification);
        NotificationDTO dto = toDTO(saved);

        messagingTemplate.convertAndSend("/topic/notifications/" + targetCompanyId, dto);
    }

    public Page<NotificationDTO> getByCompany(Long companyId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationRepository
                .findByCompanyIdOrderByCreatedAtDesc(companyId, pageable)
                .map(this::toDTO);
    }

    public long getUnreadCount(Long companyId) {
        return notificationRepository.countByCompanyIdAndIsReadFalse(companyId);
    }

    public boolean markAsRead(Long id, Long companyId) {
        return notificationRepository.markAsRead(id, companyId) > 0;
    }

    public int markAllAsRead(Long companyId) {
        return notificationRepository.markAllAsRead(companyId);
    }

    public boolean deleteById(Long id, Long companyId) {
        return notificationRepository.deleteByIdAndCompanyId(id, companyId) > 0;
    }

    private String resolveCompanyName(Long tradingCompanyId) {
        if (tradingCompanyId == null) return "Trading Company";
        Optional<Company> company = companyRepository.findById(tradingCompanyId);
        return company.map(Company::getCompanyName).orElse("Trading Company");
    }

    private String buildMessage(String eventType, BigDecimal quantity, String unit, String companyName) {
        switch (eventType) {
            case EVENT_WEAVING_JOBWORK:
                if (quantity != null && unit != null)
                    return quantity.stripTrailingZeros().toPlainString() + " " + unit + " weaving job work created by " + companyName;
                return "Weaving job work created by " + companyName;
            case EVENT_WEFT_INWARD:
                if (quantity != null && unit != null)
                    return quantity.stripTrailingZeros().toPlainString() + " " + unit + " yarn issued by " + companyName;
                return "Weft yarn inward from " + companyName;
            case EVENT_SIZING_JOBWORK:
                if (quantity != null && unit != null)
                    return quantity.stripTrailingZeros().toPlainString() + " " + unit + " sizing plan created by " + companyName;
                return "Sizing job work created by " + companyName;
            case EVENT_SIZING_YARN_INWARD:
                if (quantity != null && unit != null)
                    return quantity.stripTrailingZeros().toPlainString() + " " + unit + " sizing yarn inward from " + companyName;
                return "Sizing yarn inward from " + companyName;
            case EVENT_PURCHASE_INWARD_BALANCE_ZERO:
                String poNo = (unit != null && !unit.isEmpty()) ? unit : "";
                return "You have received all quantities for this purchase order" + (poNo.isEmpty() ? "." : " " + poNo + ".");
            case EVENT_SIZING_PLAN_BALANCE_ZERO:
                String planNo = (unit != null && !unit.isEmpty()) ? unit : "";
                return "You have issued all remaining quantities for sizing plan" + (planNo.isEmpty() ? "." : " " + planNo + ".");
            case EVENT_WEAVING_CONTRACT_BALANCE_ZERO:
                String contractNo = (unit != null && !unit.isEmpty()) ? unit : "";
                return "You have issued all remaining quantities for this" + (contractNo.isEmpty() ? "." : " " + contractNo + ".");
            default:
                return "New notification from " + companyName;
        }
    }

    public NotificationDTO toDTO(Notification n) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(n.getId());
        dto.setCompanyId(n.getCompanyId());
        dto.setTradingCompanyId(n.getTradingCompanyId());
        dto.setTradingCompanyName(n.getTradingCompanyName());
        dto.setEventType(n.getEventType());
        dto.setQuantity(n.getQuantity());
        dto.setUnit(n.getUnit());
        dto.setMessage(n.getMessage());
        dto.setReferenceId(n.getReferenceId());
        dto.setScreenPath(n.getScreenPath());
        dto.setIsRead(n.getIsRead());
        dto.setCreatedAt(n.getCreatedAt());
        return dto;
    }
}
