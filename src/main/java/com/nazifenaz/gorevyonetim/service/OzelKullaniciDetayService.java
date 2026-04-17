package com.nazifenaz.gorevyonetim.service;

import com.nazifenaz.gorevyonetim.entity.Kullanici;
import com.nazifenaz.gorevyonetim.repository.KullaniciRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OzelKullaniciDetayService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(OzelKullaniciDetayService.class);

    private final KullaniciRepository kullaniciRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String temizEmail = email.trim().toLowerCase();
        log.info("Security kullaniciyi yuklemeye calisiyor: [{}]", temizEmail);

        Kullanici kullanici = kullaniciRepository.findByEmailIgnoreCase(temizEmail)
                .orElseThrow(() -> {
                    log.warn("Security kullaniciyi bulamadi: [{}]", temizEmail);
                    return new UsernameNotFoundException("Kullanıcı bulunamadı");
                });

        log.info("Security kullaniciyi buldu: [{}]", temizEmail);

        return new User(
                kullanici.getEmail(),
                kullanici.getSifre(),
                List.of()
        );
    }
}
