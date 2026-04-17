package com.nazifenaz.gorevyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "projeler",schema = "public")
public class Proje extends BaseEntity {

    @NotBlank(message = "Proje adı boş olamaz")
    @Column(name = "proje_adi", nullable = false)
    private String ad;

    @NotBlank(message = "Proje açıklaması boş olamaz")
    @Column(name = "proje_aciklamasi", nullable = false)
    private String aciklama;

    @OneToMany(mappedBy = "proje")
    @JsonIgnore
    private List<Gorev> gorevler;
}
