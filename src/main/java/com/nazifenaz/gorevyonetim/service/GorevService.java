package com.nazifenaz.gorevyonetim.service;

import com.nazifenaz.gorevyonetim.entity.Gorev;
import com.nazifenaz.gorevyonetim.enums.GorevDurumu;
import com.nazifenaz.gorevyonetim.enums.GorevOnceligi;
import com.nazifenaz.gorevyonetim.exception.BulunamadiException;
import com.nazifenaz.gorevyonetim.exception.IslemGecersizException;
import com.nazifenaz.gorevyonetim.repository.GorevRepository;
import com.nazifenaz.gorevyonetim.repository.KullaniciRepository;
import com.nazifenaz.gorevyonetim.repository.ProjeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class GorevService {

    private static final Logger log = LoggerFactory.getLogger(GorevService.class);

    private final GorevRepository gorevRepository;
    private final ProjeRepository projeRepository;
    private final KullaniciRepository kullaniciRepository;

    public Gorev gorevOlustur(Gorev gorev) {
        Long projeId = gorev.getProje().getId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName().trim().toLowerCase();

        var proje = projeRepository.findById(projeId)
                .orElseThrow(() -> new BulunamadiException("Proje bulunamadı"));

        var kullanici = kullaniciRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BulunamadiException("Kullanıcı bulunamadı"));

        boolean ayniBaslikVarMi = gorevRepository.existsByBaslikAndProjeId(gorev.getBaslik(), projeId);
        if (ayniBaslikVarMi) {
            log.warn("Gorev olusturulamadi. Ayni proje icinde ayni baslik var: {}", gorev.getBaslik());
            throw new IslemGecersizException("Aynı proje içinde aynı başlıkta görev olamaz");
        }

        gorev.setProje(proje);
        gorev.setKullanici(kullanici);
        log.info("Yeni gorev olusturuluyor: {}", gorev.getBaslik());
        return gorevRepository.save(gorev);
    }

    public Page<Gorev> tumGorevleriGetir(GorevDurumu durum, GorevOnceligi oncelik, Long projeId, LocalDate dueBefore, int sayfa, int boyut) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Pageable pageable = PageRequest.of(sayfa, boyut);

        if (durum != null) {
            return gorevRepository.findByKullaniciEmailAndDurum(email, durum, pageable);
        }

        if (oncelik != null) {
            return gorevRepository.findByKullaniciEmailAndOncelik(email, oncelik, pageable);
        }

        if (projeId != null) {
            return gorevRepository.findByKullaniciEmailAndProjeId(email, projeId, pageable);
        }

        if (dueBefore != null) {
            return gorevRepository.findByKullaniciEmailAndSonTarihBefore(email, dueBefore, pageable);
        }

        return gorevRepository.findByKullaniciEmail(email, pageable);
    }

    public Gorev gorevGetir(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Gorev gorev = gorevRepository.findById(id)
                .orElseThrow(() -> new BulunamadiException("Görev bulunamadı"));

        if (!gorev.getKullanici().getEmail().equals(email)) {
            throw new IslemGecersizException("Sadece kendi görevinizi görebilirsiniz");
        }

        return gorev;
    }

    public Gorev gorevGuncelle(Long id, Gorev guncelGorev) {
        var gorev = gorevGetir(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (!gorev.getKullanici().getEmail().equals(email)) {
            log.warn("Kullanici kendi gorevi olmayan bir kaydi guncellemeye calisti. Gorev id: {}", id);
            throw new IslemGecersizException("Sadece kendi görevinizi güncelleyebilirsiniz");
        }

        Long projeId = guncelGorev.getProje().getId();

        var proje = projeRepository.findById(projeId)
                .orElseThrow(() -> new BulunamadiException("Proje bulunamadı"));

        boolean ayniBaslikVarMi = gorevRepository.existsByBaslikAndProjeIdAndIdNot(
                guncelGorev.getBaslik(),
                projeId,
                id
        );

        if (ayniBaslikVarMi) {
            log.warn("Gorev guncellenemedi. Ayni proje icinde ayni baslik var: {}", guncelGorev.getBaslik());
            throw new IslemGecersizException("Aynı proje içinde aynı başlıkta görev olamaz");
        }

        gorev.setBaslik(guncelGorev.getBaslik());
        gorev.setAciklama(guncelGorev.getAciklama());
        gorev.setDurum(guncelGorev.getDurum());
        gorev.setOncelik(guncelGorev.getOncelik());
        gorev.setSonTarih(guncelGorev.getSonTarih());
        gorev.setProje(proje);

        log.info("Gorev guncellendi. Id: {}", id);
        return gorevRepository.save(gorev);
    }

    public void gorevSil(Long id) {
        var gorev = gorevGetir(id);
        log.info("Gorev silindi. Id: {}", id);
        gorevRepository.delete(gorev);
    }
}
