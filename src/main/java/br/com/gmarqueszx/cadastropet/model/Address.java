package br.com.gmarqueszx.cadastropet.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    private int homeNumber;
    private String city;
    private String street;
}
