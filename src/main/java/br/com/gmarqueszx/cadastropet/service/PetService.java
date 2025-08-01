package br.com.gmarqueszx.cadastropet.service;

import br.com.gmarqueszx.cadastropet.dto.PetRegistrationData;
import br.com.gmarqueszx.cadastropet.exception.DataValidationException;
import br.com.gmarqueszx.cadastropet.model.Address;
import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.model.enums.PetGender;
import br.com.gmarqueszx.cadastropet.model.enums.PetSpecies;
import br.com.gmarqueszx.cadastropet.repository.PetRepository;
import br.com.gmarqueszx.cadastropet.util.Constants;

import java.util.List;

public class PetService {
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    public void registerPet(PetRegistrationData data) throws DataValidationException {
        Pet pet = new Pet();

        boolean isOnlyLetters = data.name().matches("[a-zA-Z ]+");
        if (data.name().split(" ").length > 1) {
            if (isOnlyLetters) {
                pet.setName(data.name());
            } else {
                throw new DataValidationException("Não é permitido uso de caracteres especiais. " +
                        "Tente novamente.");
            }
        } else if (data.name().isBlank()) {
            pet.setName(Constants.notInformed);
        } else {
            throw new DataValidationException("É necessário inserir nome e sobrenome do pet para " +
                    "seguir com o cadastro. Tente novamente.");
        }

        if (data.species().equalsIgnoreCase("GATO")) {
            pet.setSpecies(PetSpecies.GATO);
        } else if (data.species().equalsIgnoreCase("CACHORRO")) {
            pet.setSpecies(PetSpecies.CACHORRO);
        } else {
            throw new DataValidationException("As únicas espécies permitidas para cadastro são " +
                    "gato" +
                    " " +
                    "ou cachorro. tente novamente.");
        }

        if (data.gender().equalsIgnoreCase("FEMEA")) {
            pet.setGender(PetGender.FÊMEA);
        } else if (data.gender().equalsIgnoreCase("MACHO")) {
            pet.setGender(PetGender.MACHO);
        } else {
            throw new DataValidationException("Os únicas gêneros permitidas para cadastro são " +
                    "fêmea " +
                    "ou macho. tente novamente.");
        }

        int finalHomeNumber;
        Address address = new Address();
        if (data.homeNumber().isBlank() || data.homeNumber().equals("0")) {
            System.out.println("Entrada vazia ou entrada 0. O valor padrão será '0'.");
            finalHomeNumber = 0;
            address.setHomeNumber(finalHomeNumber);
        } else {
            try {
                finalHomeNumber = Integer.parseInt(data.homeNumber());
                address.setHomeNumber(finalHomeNumber);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida para o número. Será usado '0' como " +
                        "padrão");
                address.setHomeNumber(0);
            }
        }

        address.setCity(data.city());
        address.setStreet(data.street());
        pet.setAddress(address);


        String normalizedAge = data.age().replace(",", ".").trim();
        double finalAge;
        if (data.age().isBlank()) {
            System.out.println("Entrada vazia. Será usado '0.0' como padrão");
            pet.setAge(0.0);
        } else {
            try {
                finalAge = Double.parseDouble(normalizedAge);
                if (finalAge > 20) {
                    throw new DataValidationException("O pet não pode ter mais de 20 anos. Tente novamente.");
                } else if (finalAge <= 0) {
                    throw new DataValidationException("A idade não pode ser um valor negativo. Tente " +
                            "novamente.");
                } else {
                    pet.setAge(finalAge);
                }
            } catch (NumberFormatException e) {
                System.out.println("O valor informado não é número, a entrada padrão serã" +
                        "'0.0'.");
                pet.setAge(0.0);
            }
        }

        String normalizedWeight = data.weight().replace(",", ".").trim();
        if (data.weight().isBlank()) {
            System.out.println("Entrada vazia. Será usado '0.0' como padrão");
            pet.setWeight(0.0);
        } else {
            try {
                double finalWeight = Double.parseDouble(normalizedWeight);
                if (finalWeight > 60) {
                    throw new DataValidationException("O peso do pet não pode ser superior a 60kg. Tente " +
                            "novamente.");
                } else if (finalWeight <= 0.5) {
                    throw new DataValidationException("O peso do pet não pode ser inferior a 0.5kg. Tente" +
                            " " +
                            "novamente.");
                } else {
                    pet.setWeight(finalWeight);
                }
            } catch (NumberFormatException e) {
                System.out.println("O valor informado não é número, a entrada padrão serã" +
                        "'0.0'.");
                pet.setWeight(0.0);
            }
        }

        if (data.breed().isBlank()) {
            pet.setBreed(Constants.notInformed);
        } else {
            isOnlyLetters = data.breed().matches("[a-zA-Z ]+");
            if (isOnlyLetters) {
                pet.setBreed(data.breed());
            } else {
                throw new DataValidationException("O nome da raça do pet não pode conter caracteres " +
                        "especiaIs e números. Tente novamente.");
            }
        }
        PetRepository.create(pet);
    }

    public void showAllPets() {
        List<Pet> allPets = repository.findAll();
        for (Pet pet : allPets) {
            System.out.println(allPets.indexOf(pet) + " - " + pet);
        }
    }

    public void showFilteredPets() {

    }


}

