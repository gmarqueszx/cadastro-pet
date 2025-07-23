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
        } else{
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
        int homeNumber = sc.nextInt();
        sc.nextLine();
        String city = sc.nextLine();
        String street = sc.nextLine();
        Address address = new Address(homeNumber, city, street);
        pet.setAddress(address);

        //Resposta Pergunta 5
        double age = sc.nextDouble();
        sc.nextLine();
        if (age > 20) {
            throw new DataValidationException("O pet não pode ter mais de 20 anos. Tente novamente.");
        } else if (age <= 0) {
            throw new DataValidationException("A idade não pode ser um valor negativo. Tente " +
                    "novamente.");
        } else if (age == 0) {
            pet.setNome(Constants.notInformed);
        }else {
            pet.setAge(age);
        }

        //Resposta Pergunta 6
        double weight = sc.nextDouble();
        sc.nextLine();
        if (weight > 60) {
            throw new DataValidationException("O peso do pet não pode ser superior a 60kg. Tente " +
                    "novamente.");
        } else if (weight <= 0.5) {
            throw new DataValidationException("O peso do pet não pode ser inferior a 0.5kg. Tente" +
                    " " +
                    "novamente.");
        } else if (weight == 0) {
            pet.setNome(Constants.notInformed);
        } else {
            pet.setWeight(weight);
        }

        //Resposta Pergunta 7
        String breed = sc.nextLine();
        isOnlyLetters = breed.matches("[a-zA-Z ]+");
        if (isOnlyLetters) {
            pet.setBreed(breed);
        } else if (breed.equals(" ")) {
            pet.setNome(Constants.notInformed);
        } else {
            throw new DataValidationException("O nome da raça do pet não pode conter caracteres " +
                    "especiaIs e números. Tente novamente.");
        }
    }
}
