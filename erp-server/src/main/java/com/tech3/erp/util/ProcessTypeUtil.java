package com.tech3.erp.util;

/**
 * Utility class for Process Type handling
 */
public class ProcessTypeUtil {
    
    // Process Type Values
    public static final String FABRIC = "FABRIC";
    public static final String YARN = "YARN";
    
    // Default Process Type
    public static final String DEFAULT_PROCESS_TYPE = FABRIC;
    
    // Private constructor to prevent instantiation
    private ProcessTypeUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    /**
     * Check if the given process type is valid
     * @param processType String value to check
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String processType) {
        return FABRIC.equals(processType) || YARN.equals(processType);
    }
    
    /**
     * Get process type with default fallback
     * @param processType String value
     * @return Valid process type or default
     */
    public static String getValidProcessType(String processType) {
        if (processType == null || processType.trim().isEmpty() || !isValid(processType)) {
            return DEFAULT_PROCESS_TYPE;
        }
        return processType.trim().toUpperCase();
    }
    
    /**
     * Check if process type is FABRIC
     * @param processType String value
     * @return true if FABRIC, false otherwise
     */
    public static boolean isFabric(String processType) {
        return FABRIC.equals(processType);
    }
    
    /**
     * Check if process type is YARN
     * @param processType String value
     * @return true if YARN, false otherwise
     */
    public static boolean isYarn(String processType) {
        return YARN.equals(processType);
    }
}