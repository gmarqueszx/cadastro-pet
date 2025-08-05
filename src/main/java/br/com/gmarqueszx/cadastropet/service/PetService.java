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
import java.util.Locale;
import java.util.stream.Collectors;

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

        boolean isOnlyLetters = data.getName().matches("[a-zA-Z ]+");
        if (data.getName().split(" ").length > 1) {
            if (isOnlyLetters) {
                pet.setName(data.getName());
            } else {
                throw new DataValidationException("Não é permitido uso de caracteres especiais. " +
                        "Tente novamente.");
            }
        } else if (data.getName().isBlank()) {
            pet.setName(Constants.notInformed);
        } else {
            throw new DataValidationException("É necessário inserir nome e sobrenome do pet para " +
                    "seguir com o cadastro. Tente novamente.");
        }

        if (data.getSpecies().equalsIgnoreCase("GATO")) {
            pet.setSpecies(PetSpecies.GATO);
        } else if (data.getSpecies().equalsIgnoreCase("CACHORRO")) {
            pet.setSpecies(PetSpecies.CACHORRO);
        } else {
            throw new DataValidationException("As únicas espécies permitidas para cadastro são " +
                    "gato" +
                    " " +
                    "ou cachorro. tente novamente.");
        }

        if (data.getGender().equalsIgnoreCase("FEMEA")) {
            pet.setGender(PetGender.FÊMEA);
        } else if (data.getGender().equalsIgnoreCase("MACHO")) {
            pet.setGender(PetGender.MACHO);
        } else {
            throw new DataValidationException("Os únicas gêneros permitidas para cadastro são " +
                    "fêmea " +
                    "ou macho. tente novamente.");
        }

        int finalHomeNumber;
        Address address = new Address();
        if (data.getHomeNumber().isBlank() || data.getHomeNumber().equals("0")) {
            System.out.println("Entrada vazia ou entrada 0. O valor padrão será '0'.");
            finalHomeNumber = 0;
            address.setHomeNumber(finalHomeNumber);
        } else {
            try {
                finalHomeNumber = Integer.parseInt(data.getHomeNumber());
                address.setHomeNumber(finalHomeNumber);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida para o número. Será usado '0' como " +
                        "padrão");
                address.setHomeNumber(0);
            }
        }

        address.setCity(data.getCity());
        address.setStreet(data.getStreet());
        pet.setAddress(address);


        String normalizedAge = data.getAge().replace(",", ".").trim();
        double finalAge;
        if (data.getAge().isBlank()) {
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

        String normalizedWeight = data.getWeight().replace(",", ".").trim();
        if (data.getWeight().isBlank()) {
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

        if (data.getBreed().isBlank()) {
            pet.setBreed(Constants.notInformed);
        } else {
            isOnlyLetters = data.getBreed().matches("[a-zA-Z ]+");
            if (isOnlyLetters) {
                pet.setBreed(data.getBreed());
            } else {
                throw new DataValidationException("O nome da raça do pet não pode conter caracteres " +
                        "especiaIs e números. Tente novamente.");
            }
        }
        PetRepository.save(pet);
    }

    public void showAllPets() {
        List<Pet> allPets = repository.findAll();
        for (Pet pet : allPets) {
            System.out.println(allPets.indexOf(pet) + " - " + pet);
        }
    }

    public List<Pet> searchByName(String name) {
        List<Pet> allPets = repository.findAll();
        String lowerCaseName = name.toLowerCase();

        return allPets.stream()
                .filter(pet -> pet.getName().toLowerCase().contains(lowerCaseName))
                .collect(Collectors.toList());
    }

    public List<Pet> searchByGender(String gender) {
        List<Pet> allPets = repository.findAll();
        String lowerCaseGender = gender.toLowerCase();

        return allPets.stream()
                .filter(pet -> pet.getGender().toString().toLowerCase().equals(lowerCaseGender))
                .collect(Collectors.toList());
    }

    public List<Pet> searchByAge(String age) {
        List<Pet> allPets = repository.findAll();
        String formattedAge = age.replace(",", ".").trim();
        double finalAge = Double.parseDouble(formattedAge);

        return allPets.stream()
                .filter(pet -> pet.getAge() == finalAge)
                .collect(Collectors.toList());
    }

    public List<Pet> searchByWeight(String weight) {
        List<Pet> allPets = repository.findAll();
        String formattedWeight = weight.replace(",", ".").trim();
        double finalWeight = Double.parseDouble(formattedWeight);

        return allPets.stream()
                .filter(pet -> pet.getWeight() == finalWeight)
                .collect(Collectors.toList());
    }

    public List<Pet> searchByBreed(String breed) {
        List<Pet> allPets = repository.findAll();
        String lowerCaseBreed = breed.toLowerCase();

        return allPets.stream()
                .filter(pet -> pet.getBreed().toLowerCase().contains(lowerCaseBreed))
                .collect(Collectors.toList());
    }

    public List<Pet> searchByCity(String city) {
        List<Pet> allPets = repository.findAll();
        String lowerCaseCity = city.toLowerCase();

        return allPets.stream()
                .filter(pet -> pet.getAddress().getCity().toLowerCase().contains(city))
                .collect(Collectors.toList());
    }

    public void uptadePetName(Pet petToUpdate, String newName) {
        String oldFilePath = petToUpdate.getSourceFilePath();
        petToUpdate.setSourceFilePath(newName);
        repository.save(petToUpdate);
        repository.deleteByPath(oldFilePath);
    }

    public void uptadePetAge(Pet petToUpdate, String newAgeStr) {
        try {
            double newAge = Double.parseDouble(newAgeStr);
            petToUpdate.setAge(newAge);
            repository.save(petToUpdate);
        } catch (NumberFormatException e) {
            throw new DataValidationException("Formato de idade inválido");
        }
    }

    public void uptadePetWeigth(Pet petToUpdate, String newWeigthStr) {
        try {
            double newWeigth = Double.parseDouble(newWeigthStr);
            petToUpdate.setAge(newWeigth);
            repository.save(petToUpdate);
        } catch (NumberFormatException e) {
            throw new DataValidationException("Formato de peso inválido.");
        }
    }

    public void uptadePetBreed(Pet petToUpdate, String newBreed) {
        petToUpdate.setBreed(newBreed);
        repository.save(petToUpdate);
    }


}

