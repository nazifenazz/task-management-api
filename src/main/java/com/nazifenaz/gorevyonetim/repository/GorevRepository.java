package com.nazifenaz.gorevyonetim.repository;

import com.nazifenaz.gorevyonetim.entity.Gorev;
import com.nazifenaz.gorevyonetim.enums.GorevDurumu;
import com.nazifenaz.gorevyonetim.enums.GorevOnceligi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface GorevRepository extends JpaRepository<Gorev, Long> {

    Page<Gorev> findByDurum(GorevDurumu durum, Pageable pageable);

    Page<Gorev> findByOncelik(GorevOnceligi oncelik, Pageable pageable);

    Page<Gorev> findByProjeId(Long projeId, Pageable pageable);

    Page<Gorev> findBySonTarihBefore(LocalDate dueBefore, Pageable pageable);

    Page<Gorev> findByKullaniciEmail(String email, Pageable pageable);

    Page<Gorev> findByKullaniciEmailAndDurum(String email, GorevDurumu durum, Pageable pageable);

    Page<Gorev> findByKullaniciEmailAndOncelik(String email, GorevOnceligi oncelik, Pageable pageable);

    Page<Gorev> findByKullaniciEmailAndProjeId(String email, Long projeId, Pageable pageable);

    Page<Gorev> findByKullaniciEmailAndSonTarihBefore(String email, LocalDate dueBefore, Pageable pageable);

    boolean existsByProjeId(Long projeId);

    boolean existsByBaslikAndProjeId(String baslik, Long projeId);

    boolean existsByBaslikAndProjeIdAndIdNot(String baslik, Long projeId, Long id);
}
