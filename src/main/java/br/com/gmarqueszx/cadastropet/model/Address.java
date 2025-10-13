package br.com.gmarqueszx.cadastropet.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Embeddable
public class Address {
    private int homeNumber;
    private String city;
    private String street;

    @Override
    public String toString() {
        return "" + street + ", " + homeNumber + ", " + city;
    }
}
