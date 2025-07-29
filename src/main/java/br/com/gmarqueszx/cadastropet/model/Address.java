package br.com.gmarqueszx.cadastropet.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int homeNumber;
    private String city;
    private String street;

    @Override
    public String toString() {
        return "" + street + ", " + homeNumber + ", " + city;
    }
}
