package br.com.gmarqueszx.cadastropet.service;

import br.com.gmarqueszx.cadastropet.exception.DataValidationException;
import br.com.gmarqueszx.cadastropet.model.Address;
import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.model.enums.PetGender;
import br.com.gmarqueszx.cadastropet.model.enums.PetSpecies;
import br.com.gmarqueszx.cadastropet.repository.FormRepository;
import br.com.gmarqueszx.cadastropet.util.Constants;
import br.com.gmarqueszx.cadastropet.util.StringUtil;


import java.util.Scanner;

public class RegisterPet {
    public static void registerPet() {
        Pet pet = new Pet();
        Scanner sc = new Scanner(System.in);
        System.out.println("\nCadastro de Pet");
        System.out.println("Responda em sequência as perguntas abaixo:\n");
        FormRepository.readForm();

        //Resposta Pergunta 1
        String name = sc.nextLine().trim();
        boolean isOnlyLetters = name.matches("[a-zA-Z ]+");
        if (name.split(" ").length > 1) {
            if (isOnlyLetters) {
                pet.setNome(name);
            } else {
                throw new DataValidationException("Não é permitido uso de caracteres especiais. " +
                        "Tente novamente.");
            }
        } else if (name.isBlank()) {
            pet.setNome(Constants.notInformed);
        } else {
            throw new DataValidationException("É necessário inserir nome e sobrenome do pet para " +
                    "seguir com o cadastro. Tente novamente.");
        }

        //Resposta Pergunta 2
        String species = sc.nextLine().trim();
        if (species.equalsIgnoreCase("GATO")) {
            pet.setSpecies(PetSpecies.GATO);
        } else if (species.equalsIgnoreCase("CACHORRO")) {
            pet.setSpecies(PetSpecies.CACHORRO);
        } else {
            throw new DataValidationException("As únicas espécies permitidas para cadastro são " +
                    "gato" +
                    " " +
                    "ou cachorro. tente novamente.");
        }

        //Resposta Pergunta 3
        String gender = sc.nextLine().trim();
        String normalizedGender = StringUtil.removeAccents(gender);
        if (normalizedGender.equalsIgnoreCase("FEMEA")) {
            pet.setGender(PetGender.FÊMEA);
        } else if (normalizedGender.equalsIgnoreCase("MACHO")) {
            pet.setGender(PetGender.MACHO);
        } else {
            throw new DataValidationException("Os únicas gêneros permitidas para cadastro são " +
                    "fêmea " +
                    "ou macho. tente novamente.");
        }

        //Resposta Pergunta 4
        Address address = new Address();
        String homeNumber = sc.nextLine().trim();
        int finalHomeNumber;
        if (homeNumber.isBlank() || homeNumber.equals("0")) {
            System.out.println("Entrada vazia ou entrada 0. O valor padrão será '0'.");
            finalHomeNumber = 0;
            address.setHomeNumber(finalHomeNumber);
        } else {
            try {
                finalHomeNumber = Integer.parseInt(homeNumber);
                address.setHomeNumber(finalHomeNumber);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida para o número. Será usado '0' como " +
                        "padrão");
                address.setHomeNumber(0);
            }
        }
        String city = sc.nextLine();
        address.setCity(city);
        String street = sc.nextLine();
        address.setStreet(street);
        pet.setAddress(address);

        //Resposta Pergunta 5
        String age = sc.nextLine().trim();
        String normalizedAge = age.replace(",", ".").trim();
        double finalAge = 0;
        if (age.isBlank()) {
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

        //Resposta Pergunta 6
        String weight = sc.nextLine().trim();
        String normalizedWeight = weight.replace(",", ".").trim();

        if (weight.isBlank()) {
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


        //Resposta Pergunta 7
        String breed = sc.nextLine().trim();
        if (breed.isBlank()) {
            pet.setBreed(Constants.notInformed);
        } else {
            isOnlyLetters = breed.matches("[a-zA-Z ]+");
            if (isOnlyLetters) {
                pet.setBreed(breed);
            } else {
                throw new DataValidationException("O nome da raça do pet não pode conter caracteres " +
                        "especiaIs e números. Tente novamente.");
            }
        }
        System.out.println(pet);
    }
}