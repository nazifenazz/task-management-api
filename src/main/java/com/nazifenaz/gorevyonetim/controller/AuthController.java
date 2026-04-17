package com.nazifenaz.gorevyonetim.controller;

import com.nazifenaz.gorevyonetim.dto.AuthGirisIstek;
import com.nazifenaz.gorevyonetim.dto.AuthMesajCevap;
import com.nazifenaz.gorevyonetim.entity.Kullanici;
import com.nazifenaz.gorevyonetim.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Kullanici kayitOl(@Valid @RequestBody Kullanici kullanici) {
        return authService.kayitOl(kullanici);
    }

    @PostMapping("/login")
    public AuthMesajCevap girisYap(@Valid @RequestBody AuthGirisIstek girisIstek) {
        String mesaj = authService.girisYap(girisIstek.getEmail(), girisIstek.getSifre());
        return AuthMesajCevap.builder()
                .mesaj(mesaj)
                .build();
    }
}
