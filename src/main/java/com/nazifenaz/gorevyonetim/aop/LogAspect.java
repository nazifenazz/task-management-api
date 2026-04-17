package com.nazifenaz.gorevyonetim.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Value("${spring.application.name}")
    private String uygulamaAdi;

    @Around("execution(* com.nazifenaz.gorevyonetim.controller..*(..))")
    public Object controllerLogla(ProceedingJoinPoint joinPoint) throws Throwable {
        long baslangicZamani = System.currentTimeMillis();

        log.info("[{}] Controller metodu basladi: {}", uygulamaAdi, joinPoint.getSignature().toShortString());

        try {
            Object sonuc = joinPoint.proceed();
            long bitisZamani = System.currentTimeMillis();
            log.info("[{}] Controller metodu bitti: {} - Sure: {} ms",
                    uygulamaAdi,
                    joinPoint.getSignature().toShortString(),
                    bitisZamani - baslangicZamani);
            return sonuc;
        } catch (Throwable e) {
            long bitisZamani = System.currentTimeMillis();
            log.error("[{}] Controller hatasi: {} - Mesaj: {} - Sure: {} ms",
                    uygulamaAdi,
                    joinPoint.getSignature().toShortString(),
                    e.getMessage(),
                    bitisZamani - baslangicZamani);
            throw e;
        }
    }

    @Around("execution(* com.nazifenaz.gorevyonetim.service..*(..))")
    public Object serviceLogla(ProceedingJoinPoint joinPoint) throws Throwable {
        long baslangicZamani = System.currentTimeMillis();

        log.info("[{}] Service metodu basladi: {}", uygulamaAdi, joinPoint.getSignature().toShortString());

        try {
            Object sonuc = joinPoint.proceed();
            long bitisZamani = System.currentTimeMillis();
            log.info("[{}] Service metodu bitti: {} - Sure: {} ms",
                    uygulamaAdi,
                    joinPoint.getSignature().toShortString(),
                    bitisZamani - baslangicZamani);
            return sonuc;
        } catch (Throwable e) {
            long bitisZamani = System.currentTimeMillis();
            log.error("[{}] Service hatasi: {} - Mesaj: {} - Sure: {} ms",
                    uygulamaAdi,
                    joinPoint.getSignature().toShortString(),
                    e.getMessage(),
                    bitisZamani - baslangicZamani);
            throw e;
        }
    }
}
