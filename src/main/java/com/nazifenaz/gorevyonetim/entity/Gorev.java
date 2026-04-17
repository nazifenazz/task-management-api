package com.nazifenaz.gorevyonetim.entity;

import com.nazifenaz.gorevyonetim.enums.GorevDurumu;
import com.nazifenaz.gorevyonetim.enums.GorevOnceligi;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "gorevler",schema = "public")
public class Gorev extends BaseEntity {

    @NotBlank(message = "Görev başlığı boş olamaz")
    @Column(name = "gorev_basligi", nullable = false)
    private String baslik;

    @Column(name = "gorev_aciklamasi")
    private String aciklama;

    @NotNull(message = "Görev durumu boş olamaz")
    @Enumerated(EnumType.STRING)
    @Column(name = "gorev_durumu", nullable = false)
    private GorevDurumu durum;

    @NotNull(message = "Görev önceliği boş olamaz")
    @Enumerated(EnumType.STRING)
    @Column(name = "gorev_onceligi", nullable = false)
    private GorevOnceligi oncelik;

    @FutureOrPresent(message = "Son tarih geçmiş bir tarih olamaz")
    @Column(name = "son_tarih")
    private LocalDate sonTarih;

    @NotNull(message = "Görev bir projeye bağlı olmalıdır")
    @ManyToOne
    @JoinColumn(name = "proje_id", nullable = false)
    private Proje proje;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;
}
