package com.tecsup.aspect;

import com.tecsup.model.Producto;
import com.tecsup.service.AuditoriaService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AuditoriaAspect {

    @Autowired
    private AuditoriaService auditoriaService;

    @AfterReturning(pointcut = "execution(* com.tecsup.service.ProductoService.guardar(..))",
            returning = "resultado")
    public void auditarGuardar(JoinPoint joinPoint, Producto resultado) {

        auditoriaService.registrar(
                "CREAR",
                joinPoint.getSignature().getName(),
                "Se registró producto con ID: " + resultado.getId() + " (" + resultado.getNombre() + ")"
        );
    }

    @AfterReturning(pointcut = "execution(* com.tecsup.service.ProductoService.actualizar(..))",
            returning = "resultado")
    public void auditarActualizar(JoinPoint joinPoint, Producto resultado) {

        Long id = (Long) joinPoint.getArgs()[0];

        auditoriaService.registrar(
                "ACTUALIZAR",
                joinPoint.getSignature().getName(),
                "Se actualizó producto con ID: " + id + " (" + resultado.getNombre() + ")"
        );
    }

    @AfterReturning(pointcut = "execution(* com.tecsup.service.ProductoService.listar(..))",
            returning = "resultado")
    public void auditarListar(JoinPoint joinPoint, List<?> resultado) {

        auditoriaService.registrar(
                "LISTAR",
                joinPoint.getSignature().getName(),
                "Se listaron " + resultado.size() + " productos"
        );
    }

    @AfterReturning("execution(* com.tecsup.service.ProductoService.eliminar(..))")
    public void auditarEliminar(JoinPoint joinPoint) {

        Long id = (Long) joinPoint.getArgs()[0];

        auditoriaService.registrar(
                "ELIMINAR",
                joinPoint.getSignature().getName(),
                "Se eliminó producto con ID: " + id
        );
    }
}