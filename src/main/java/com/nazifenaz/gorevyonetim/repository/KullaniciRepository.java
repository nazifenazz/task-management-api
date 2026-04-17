package com.nazifenaz.gorevyonetim.repository;

import com.nazifenaz.gorevyonetim.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    Optional<Kullanici> findByEmailIgnoreCase(String email);
}
