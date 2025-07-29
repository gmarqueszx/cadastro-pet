package br.com.gmarqueszx.cadastropet.model;

import br.com.gmarqueszx.cadastropet.model.enums.PetGender;
import br.com.gmarqueszx.cadastropet.model.enums.PetSpecies;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private String nome;
    private PetSpecies species;
    private PetGender gender;
    private Address address;
    private double age;
    private double weight;
    private String breed;

    @Override
    public String toString() {
        return "" + nome + " - " + species + " - " + gender + " - " + address + " - " + age + " - " + weight + " - " + breed;
    }
}
