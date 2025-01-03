package com.onlinebookstore.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.servlets.TaskRegistrationServlet.*(..))")
    public void logBeforeTaskRegistration() {
        logger.info("TaskRegistrationServlet method execution started");
    }
}
