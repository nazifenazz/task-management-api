package com.nazifenaz.gorevyonetim.controller;

import com.nazifenaz.gorevyonetim.entity.Gorev;
import com.nazifenaz.gorevyonetim.enums.GorevDurumu;
import com.nazifenaz.gorevyonetim.enums.GorevOnceligi;
import com.nazifenaz.gorevyonetim.service.GorevService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class GorevController {

    private final GorevService gorevService;

    @PostMapping
    public Gorev gorevOlustur(@Valid @RequestBody Gorev gorev) {
        return gorevService.gorevOlustur(gorev);
    }

    @GetMapping
    public Page<Gorev> tumGorevleriGetir(
            @RequestParam(required = false) GorevDurumu durum,
            @RequestParam(required = false) GorevOnceligi oncelik,
            @RequestParam(required = false) Long projeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueBefore,
            @RequestParam(defaultValue = "0") int sayfa,
            @RequestParam(defaultValue = "10") int boyut
    ) {
        return gorevService.tumGorevleriGetir(durum, oncelik, projeId, dueBefore, sayfa, boyut);
    }

    @GetMapping("/{id}")
    public Gorev gorevGetir(@PathVariable Long id) {
        return gorevService.gorevGetir(id);
    }

    @PutMapping("/{id}")
    public Gorev gorevGuncelle(@PathVariable Long id, @Valid @RequestBody Gorev gorev) {
        return gorevService.gorevGuncelle(id, gorev);
    }

    @DeleteMapping("/{id}")
    public void gorevSil(@PathVariable Long id) {
        gorevService.gorevSil(id);
    }
}
