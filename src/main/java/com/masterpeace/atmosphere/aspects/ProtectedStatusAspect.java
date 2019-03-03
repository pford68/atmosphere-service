package com.masterpeace.atmosphere.aspects;

import com.masterpeace.atmosphere.configuration.AtmosphereSecurityConfig;
import com.masterpeace.atmosphere.model.*;
import com.masterpeace.atmosphere.services.InstanceService;
import com.masterpeace.atmosphere.services.UserService;
import com.masterpeace.atmosphere.services.VolumeService;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Applies advice around methods that delete entities in order to protect those resources from
 *  accidental deletion.
 */
@Aspect
@Component
public class ProtectedStatusAspect{

    private static final Logger LOGGER = Logger.getLogger(ProtectedStatusAspect.class);


    @Value("${atmosphere.deletion.started}")
    private String msgDeleteRequested;


    @Value("${atmosphere.deletion.completed}")
    private String msgDeleteCompleted;


    @Autowired
    private UserService userService;


    @Autowired
    private VolumeService volumeService;


    @Autowired
    private InstanceService instanceService;


    @Autowired
    private AtmosphereSecurityConfig webSecurityConfig;


    @Autowired
    private AuditEventRepository auditEventRepository;



    private boolean userInitiatedDelete(User user, Protectable protectable){
        if (protectable.getLastModifiedDate() == 0) return false;
        List<AuditEvent> records = auditEventRepository.find(user.getEmail(), new Date(protectable.getLastModifiedDate()));
        Map<String, Object> data = null;
        for (AuditEvent record : records){
            data = record.getData();
            Long auditedId = data != null && data.get("id") != null ? (Long) data.get("id") : -1;   // The ID recorded in the current audit record.
            if (record.getPrincipal().equals(user.getEmail())
                    && auditedId == protectable.getId()
                    && record.getType().equals(protectable.getClass().getSimpleName())){ // Should probably change the type to "Instance Delete Attempted (PF 20150424)
                return true;
            }
        }
        return false;
    }



    private boolean isUserValid(User user, Protectable protectable) throws Exception {
        if (user== null){
            String msg = "The user was not found.";
            throw new Exception(msg);
        }
        return !userInitiatedDelete(user, protectable) && user.isGroupAdmin(protectable);
    }


    private boolean isDisposable(Protectable content) {
        return content.getStatus() == null || content.getStatus().getRules().contains("DELETE") || content.getState().getId() == -1;
    }


    private Map<String, Object> setAuditData(Long id, String message){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", id);
        data.put("message", message);
        return data;
    }


    /**
     * Advice for methods that delete resources.  It surrounds the methods with checks for authorization and whether
     * resource is free to be deleted, or should merely be suspended.
     *
     * @param joinPoint             The original method
     * @return
     * @throws Throwable
     */
    @Around("this(com.masterpeace.atmosphere.services.InstanceService) && @annotation(com.masterpeace.atmosphere.annotations.Protected)")
    public Instance aroundInstanceDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Instance content = (Instance) args[0];
        User user = userService.getUserByUserName(webSecurityConfig.currentUser().get());
        boolean authorized = isUserValid(user, content);
        String contentClassName = content.getClass().getSimpleName();

        if (isDisposable(content) && authorized) {
            // The joinpoint
            joinPoint.proceed();

            // Notifying admins in the user group, not Atmosphere admins per se.
            userService.notifyAll(String.format(msgDeleteCompleted, contentClassName, content.getId()), userService.getGroupAdmins(content));
            LOGGER.info(String.format("%s has deleted %s %d", user, contentClassName, content.getId()));

            // Logging the event for auditing
            Map<String, Object> auditData = setAuditData(content.getId(), String.format(msgDeleteRequested, contentClassName, content.getId()));
            auditEventRepository.add(new AuditEvent(user.getEmail(), contentClassName, auditData));
        } else if (authorized) {
            instanceService.suspend(content);
            LOGGER.info(String.format("%s has attempted to delete %s %d", user, contentClassName, content.getId()));

            // Notifying admins in the user group, not Atmosphere admins per se.
            userService.notifyAll(String.format(msgDeleteRequested, contentClassName, content.getId()), userService.getGroupAdmins(content));

            // Logging the event for auditing
            Map<String, Object> auditData = setAuditData(content.getId(), String.format(msgDeleteCompleted, contentClassName, content.getId()));
            auditEventRepository.add(new AuditEvent(user.getEmail(), content.getClass().getSimpleName(), auditData));
        } else {
            throw new Exception(String.format("You do not have permission to delete this %s.", contentClassName));
        }
        return content;
    }


    /*
    Incomplete
     */
    @Around("this(com.masterpeace.atmosphere.services.VolumeService) && @annotation(com.masterpeace.atmosphere.annotations.Protected)")
    public Volume aroundDeleteVolume(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Volume content = (Volume) args[0];
        User user = userService.getUserByUserName(webSecurityConfig.currentUser().get());
        boolean authorized = isUserValid(user, content);
        if (isDisposable(content) && authorized) {
            joinPoint.proceed();
            userService.notifyAll(String.format(msgDeleteCompleted, "volume", content.getId()), userService.getGroupAdmins(content));
            LOGGER.info(String.format("%s has attempted to delete volume %d", user, content.getId()));
        } else if (authorized) {
            volumeService.suspend(content);
            LOGGER.info(String.format("%s has attempted to delete volume %d", user, content.getId()));
            userService.notifyAll(String.format(msgDeleteRequested, "Volume", content.getId()), userService.getGroupAdmins(content));
        } else {
            throw new Exception("You do not have permission to delete this volume.");
        }
        return content;
    }


}
