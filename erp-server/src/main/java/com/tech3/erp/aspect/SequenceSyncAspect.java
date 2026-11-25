// package com.tech3.erp.aspect;

// import com.tech3.erp.service.SequenceSyncService;
// import jakarta.persistence.Table;
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Before;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import java.util.Collection;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.Map;

// @Aspect
// @Component
// public class SequenceSyncAspect {

//     @Autowired
//     private SequenceSyncService sequenceSyncService;

//     // Cache to avoid repeated reflection checks
//     private final Map<Class<?>, Boolean> masterEntityCache = new ConcurrentHashMap<>();

//     /**
//      * Intercepts repository save methods.
//      */
//     @Pointcut("execution(* org.springframework.data.repository.CrudRepository+.save(..)) || " +
//               "execution(* org.springframework.data.jpa.repository.JpaRepository+.saveAndFlush(..))")
//     public void repositorySaveMethods() {}

//     @Before("repositorySaveMethods()")
//     public void syncSequenceBeforeSave(JoinPoint joinPoint) {
//         Object[] args = joinPoint.getArgs();
//         if (args.length > 0) {
//             Object entity = args[0];
//             if (entity != null) {
//                 if (entity instanceof Iterable) {
//                     for (Object item : (Iterable<?>) entity) {
//                         checkAndSync(item);
//                     }
//                 } else {
//                     checkAndSync(entity);
//                 }
//             }
//         }
//     }

//     private void checkAndSync(Object entity) {
//         if (entity == null) return;
        
//         Class<?> entityClass = entity.getClass();
        
//         // Check cache first
//         if (masterEntityCache.computeIfAbsent(entityClass, this::isMasterEntity)) {
//             sequenceSyncService.syncSequence(entityClass);
//         }
//     }

//     private boolean isMasterEntity(Class<?> clazz) {
//         Table tableAnnotation = clazz.getAnnotation(Table.class);
//         return tableAnnotation != null && "masters".equals(tableAnnotation.schema());
//     }
// }
package com.tech3.erp.aspect;

import com.tech3.erp.service.SequenceSyncService;
import jakarta.persistence.Table;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Aspect
@Component
public class SequenceSyncAspect {

    @Autowired
    private SequenceSyncService sequenceSyncService;

    // Cache to avoid repeated reflection checks
    private final Map<Class<?>, Boolean> masterEntityCache = new ConcurrentHashMap<>();

    /**
     * Intercepts repository save methods.
     */
    @Pointcut("execution(* org.springframework.data.repository.CrudRepository+.save(..)) || " +
              "execution(* org.springframework.data.jpa.repository.JpaRepository+.saveAndFlush(..))")
    public void repositorySaveMethods() {}

    @Before("repositorySaveMethods()")
    public void syncSequenceBeforeSave(JoinPoint joinPoint) {
        // Disabled to prevent concurrent duplicate key errors
        /*
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            Object entity = args[0];
            if (entity != null) {
                if (entity instanceof Iterable) {
                    for (Object item : (Iterable<?>) entity) {
                        checkAndSync(item);
                    }
                } else {
                    checkAndSync(entity);
                }
            }
        }
        */
    }

    private void checkAndSync(Object entity) {
        if (entity == null) return;
        
        Class<?> entityClass = entity.getClass();
        
        // Check cache first
        if (masterEntityCache.computeIfAbsent(entityClass, this::isMasterEntity)) {
            sequenceSyncService.syncSequence(entityClass);
        }
    }

    private boolean isMasterEntity(Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        return tableAnnotation != null && "masters".equals(tableAnnotation.schema());
    }
}
