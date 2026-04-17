package com.nazifenaz.gorevyonetim.service;

import com.nazifenaz.gorevyonetim.entity.Kullanici;
import com.nazifenaz.gorevyonetim.exception.IslemGecersizException;
import com.nazifenaz.gorevyonetim.repository.KullaniciRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;

    public Kullanici kayitOl(Kullanici kullanici) {
        String email = kullanici.getEmail().trim().toLowerCase();

        boolean emailKullanimda = kullaniciRepository.findByEmailIgnoreCase(email).isPresent();
        if (emailKullanimda) {
            log.warn("Kayit basarisiz. Email zaten kullanimda: {}", email);
            throw new IslemGecersizException("Bu email zaten kullanılıyor");
        }

        kullanici.setEmail(email);
        kullanici.setSifre(passwordEncoder.encode(kullanici.getSifre()));
        log.info("Yeni kullanici kaydi olusturuluyor: {}", email);
        return kullaniciRepository.save(kullanici);
    }

    public String girisYap(String email, String sifre) {
        String temizEmail = email.trim().toLowerCase();

        Kullanici kullanici = kullaniciRepository.findByEmailIgnoreCase(temizEmail)
                .orElseThrow(() -> new IslemGecersizException("Email veya şifre hatalı"));

        boolean sifreDogruMu = passwordEncoder.matches(sifre, kullanici.getSifre());
        if (!sifreDogruMu) {
            log.warn("Hatali giris denemesi: {}", temizEmail);
            throw new IslemGecersizException("Email veya şifre hatalı");
        }

        log.info("Kullanici giris yapti: {}", temizEmail);
        return "Giriş başarılı";
    }
}
