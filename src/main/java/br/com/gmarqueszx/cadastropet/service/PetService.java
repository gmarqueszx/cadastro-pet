package br.com.gmarqueszx.cadastropet.service;

import br.com.gmarqueszx.cadastropet.dto.PetRegistrationData;
import br.com.gmarqueszx.cadastropet.exception.DataValidationException;
import br.com.gmarqueszx.cadastropet.model.Address;
import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.model.enums.PetGender;
import br.com.gmarqueszx.cadastropet.model.enums.PetSpecies;
import br.com.gmarqueszx.cadastropet.repository.PetRepository;
import br.com.gmarqueszx.cadastropet.util.Constants;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
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
        repository.save(pet);
    }


    public List<Pet> searchByName(String name) {
        return repository.findByPetName(name);
    }

    public List<Pet> searchByGender(String gender) {
        return repository.findByPetGender(gender);
    }

    public List<Pet> searchByAge(String age) {
        String formattedAge = age.replace(",", ".").trim();
        double finalAge = Double.parseDouble(formattedAge);
        return repository.findByPetAge(finalAge);
    }

    public List<Pet> searchByWeight(String weight) {
        String formattedWeight = weight.replace(",", ".").trim();
        double finalWeight = Double.parseDouble(formattedWeight);
        return repository.findByPetWeight(finalWeight);
    }

    public List<Pet> searchByBreed(String breed) {
        return repository.findByPetBreed(breed);
    }

    public List<Pet> searchByCity(String city) {
        return repository.findByPetCity(city);
    }

    public void removePet(int id) {
        repository.delete(id);
    }

    public void updatePetName(int petId, String newName) {
        Pet petToUpdate = findPetByIdOrThrow(petId);

        if (newName == null || newName.trim().isBlank() || !newName.contains(" ")) {
            throw new DataValidationException("O novo nome deve conter nome e sobrenome.");
        }
        petToUpdate.setName(newName.trim());

        repository.save(petToUpdate);
    }

    public void updatePetAge(int petId, String newAgeStr) {
        Pet petToUpdate = findPetByIdOrThrow(petId);
        try {
            double newAge = Double.parseDouble(newAgeStr.replace(',', '.'));
            if (newAge <= 0 || newAge > 20) {
                throw new DataValidationException("Idade inválida.");
            }
            petToUpdate.setAge(newAge);
            repository.save(petToUpdate);
        } catch (NumberFormatException e) {
            throw new DataValidationException("Formato de idade inválido.");
        }
    }

    public void updatePetWeight(int petId, String newWeightStr) {
        Pet petToUpdate = findPetByIdOrThrow(petId);
        try {
            double newWeight = Double.parseDouble(newWeightStr.replace(',', '.'));
            if (newWeight <= 0.5 || newWeight > 60) {
                throw new DataValidationException("Peso inválido.");
            }
            petToUpdate.setWeight(newWeight);
            repository.save(petToUpdate);
        } catch (NumberFormatException e) {
            throw new DataValidationException("Formato de peso inválido.");
        }
    }

    public void updatePetBreed(int petId, String newBreed) {
        Pet petToUpdate = findPetByIdOrThrow(petId);

        if (newBreed.isBlank()) {
            petToUpdate.setBreed(Constants.notInformed);
        } else if (!newBreed.matches("[a-zA-Z ]+")) {
            throw new DataValidationException("O nome da raça do pet não pode conter caracteres especiais e números. Tente novamente.");
        }

        petToUpdate.setBreed(newBreed);
        repository.save(petToUpdate);
    }

    private Pet findPetByIdOrThrow(int id) {
        Pet pet = repository.findById(id);
        if (pet == null) {
            throw new NoSuchElementException("Pet com ID " + id + " não encontrado.");
        }
        return pet;
    }


}

