package com.nazifenaz.gorevyonetim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "kullanicilar", schema = "public")
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Kullanıcı adı boş olamaz")
    @Column(name = "kullanici_adi", nullable = false)
    private String ad;

    @Email(message = "Geçerli bir email girilmelidir")
    @NotBlank(message = "Email boş olamaz")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "sifre", nullable = false)
    private String sifre;

    @OneToMany(mappedBy = "kullanici")
    @JsonIgnore
    private List<Gorev> gorevler;
}
