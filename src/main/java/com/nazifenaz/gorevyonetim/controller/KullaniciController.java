package com.nazifenaz.gorevyonetim.controller;

import com.nazifenaz.gorevyonetim.entity.Kullanici;
import com.nazifenaz.gorevyonetim.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @GetMapping
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciService.tumKullanicilariGetir();
    }

    @GetMapping("/{id}")
    public Kullanici kullaniciGetir(@PathVariable Long id) {
        return kullaniciService.kullaniciGetir(id);
    }
}
