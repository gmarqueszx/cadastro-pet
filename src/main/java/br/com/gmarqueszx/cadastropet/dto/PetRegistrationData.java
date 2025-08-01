package br.com.gmarqueszx.cadastropet.dto;

import br.com.gmarqueszx.cadastropet.model.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record PetRegistrationData(String name, String species, String gender, String homeNumber, String city, String street, String age, String weight, String breed) {
}
