package com.nazifenaz.gorevyonetim.controller;

import com.nazifenaz.gorevyonetim.entity.Proje;
import com.nazifenaz.gorevyonetim.service.ProjeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projeler")
public class ProjeController {

    private final ProjeService projeService;

    @PostMapping
    public Proje projeOlustur(@Valid @RequestBody Proje proje) {
        return projeService.projeOlustur(proje);
    }

    @GetMapping
    public List<Proje> tumProjeleriGetir() {
        return projeService.tumProjeleriGetir();
    }

    @GetMapping("/{id}")
    public Proje projeGetir(@PathVariable Long id) {
        return projeService.projeGetir(id);
    }

    @PutMapping("/{id}")
    public Proje projeGuncelle(@PathVariable Long id, @Valid @RequestBody Proje proje) {
        return projeService.projeGuncelle(id, proje);
    }

    @DeleteMapping("/{id}")
    public void projeSil(@PathVariable Long id) {
        projeService.projeSil(id);
    }
}
