package com.nazifenaz.gorevyonetim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//Nesneyi daha okunabilir şekilde kurmak için builder kullandık
@Builder
@AllArgsConstructor
public class AuthMesajCevap {

    private String mesaj;
}
