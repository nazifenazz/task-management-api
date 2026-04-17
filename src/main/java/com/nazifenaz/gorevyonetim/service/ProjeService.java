package com.nazifenaz.gorevyonetim.service;

import com.nazifenaz.gorevyonetim.entity.Proje;
import com.nazifenaz.gorevyonetim.exception.BulunamadiException;
import com.nazifenaz.gorevyonetim.exception.IslemGecersizException;
import com.nazifenaz.gorevyonetim.repository.GorevRepository;
import com.nazifenaz.gorevyonetim.repository.ProjeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjeService {

    private static final Logger log = LoggerFactory.getLogger(ProjeService.class);

    private final ProjeRepository projeRepository;
    private final GorevRepository gorevRepository;

    public ProjeService(ProjeRepository projeRepository, GorevRepository gorevRepository) {
        this.projeRepository = projeRepository;
        this.gorevRepository = gorevRepository;
    }

    public Proje projeOlustur(Proje proje) {
        log.info("Yeni proje olusturuluyor: {}", proje.getAd());
        return projeRepository.save(proje);
    }

    public List<Proje> tumProjeleriGetir() {
        return projeRepository.findAll();
    }

    public Proje projeGetir(Long id) {
        return projeRepository.findById(id)
                .orElseThrow(() -> new BulunamadiException("Proje bulunamadı"));
    }

    public Proje projeGuncelle(Long id, Proje guncelProje) {
        Proje proje = projeGetir(id);

        proje.setAd(guncelProje.getAd());
        proje.setAciklama(guncelProje.getAciklama());

        log.info("Proje guncellendi. Id: {}", id);
        return projeRepository.save(proje);
    }

    public void projeSil(Long id) {
        Proje proje = projeGetir(id);

        boolean gorevVarMi = gorevRepository.existsByProjeId(id);
        if (gorevVarMi) {
            log.warn("Proje silinemedi. Bu projeye ait gorevler var. Proje id: {}", id);
            throw new IslemGecersizException("Bu projeye ait görevler olduğu için proje silinemez");
        }

        log.info("Proje silindi. Id: {}", id);
        projeRepository.delete(proje);
    }
}
