package br.com.gmarqueszx.cadastropet.dto;

import br.com.gmarqueszx.cadastropet.model.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetRegistrationData {
    private String name;
    private String species;
    private String gender;
    private String homeNumber;
    private String city;
    private String street;
    private String age;
    private String weight;
    private String breed;
}
