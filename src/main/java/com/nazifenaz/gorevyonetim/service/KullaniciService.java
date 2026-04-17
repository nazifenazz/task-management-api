package com.nazifenaz.gorevyonetim.service;

import com.nazifenaz.gorevyonetim.entity.Kullanici;
import com.nazifenaz.gorevyonetim.exception.BulunamadiException;
import com.nazifenaz.gorevyonetim.repository.KullaniciRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    public KullaniciService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Kullanici kullaniciGetir(Long id) {
        return kullaniciRepository.findById(id)
                .orElseThrow(() -> new BulunamadiException("Kullanıcı bulunamadı"));
    }
}
