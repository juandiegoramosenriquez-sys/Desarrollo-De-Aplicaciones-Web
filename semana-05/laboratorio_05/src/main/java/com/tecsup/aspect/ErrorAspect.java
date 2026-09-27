package com.tecsup.aspect;

import com.tecsup.service.AuditoriaService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ErrorAspect {

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private HttpServletRequest request;

    @AfterThrowing(
            pointcut = "execution(* com.tecsup.service.*.*(..)) && !within(com.tecsup.service.AuditoriaService)",
            throwing = "ex"
    )
    public void capturarError(JoinPoint joinPoint, Exception ex) {

        System.out.println("❌ ERROR AOP: " + ex.getMessage());

        String usuario = request.getHeader("Usuario");

        auditoriaService.registrar(
                "ERROR",
                joinPoint.getSignature().getName(),
                ex.getMessage() + " | parámetros: " + Arrays.toString(joinPoint.getArgs()),
                usuario != null ? usuario : "ANONIMO"
        );
    }
}