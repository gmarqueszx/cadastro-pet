package br.com.gmarqueszx.cadastropet.service;

import br.com.gmarqueszx.cadastropet.exception.DataValidationException;
import br.com.gmarqueszx.cadastropet.model.Address;
import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.model.enums.PetGender;
import br.com.gmarqueszx.cadastropet.model.enums.PetSpecies;
import br.com.gmarqueszx.cadastropet.repository.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchRegisteredPets {
    static PetRepository petRepository = new PetRepository();
    static List<Pet> allPets = petRepository.findAll();
    static List<Pet> filteredPets = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void filterPets() {
        System.out.println("\nListar Pets Por Algum Critério");

        System.out.println("\n Qual critério deseja utilizar na listagem: ");
        System.out.println("1 - Nome");
        System.out.println("2 - Sexo");
        System.out.println("3 - Idade");
        System.out.println("4 - Peso");
        System.out.println("5 - Raça");
        System.out.println("6 - Endereço");
        System.out.println("7 - Espécie");
        int op2 = sc.nextInt();
        sc.nextLine();
        switch (op2) {
            case 1:
                System.out.println("\n Critério escolhido: Nome");
                System.out.println("Digite o nome desejado: ");
                String name = sc.nextLine();
                filteredPets = allPets.stream()
                        .filter(pet -> pet.getName().toLowerCase().contains(name.toLowerCase()))
                        .collect(Collectors.toList());
                break;

            case 2:
                System.out.println("\n Critério escolhido: Sexo");
                System.out.println("Escolha o sexo desejado: ");
                System.out.println("1 - Macho");
                System.out.println("2 - Fêmea");
                int op3 = sc.nextInt();
                sc.nextLine();
                if (op3 == 1) {
                    filteredPets = allPets.stream()
                            .filter(pet -> pet.getGender().equals(PetGender.MACHO))
                            .collect(Collectors.toList());
                } else if (op3 == 2) {
                    filteredPets = allPets.stream()
                            .filter(pet -> pet.getGender().equals(PetGender.FÊMEA))
                            .collect(Collectors.toList());
                } else {
                    throw new DataValidationException("Opção Inválida, tente novamente");
                }
                break;

            case 3:
                System.out.println("\n Critério escolhido: Idade");
                System.out.println("Digite a idade desejada: ");
                double age = sc.nextDouble();
                sc.nextLine();
                filteredPets = allPets.stream()
                        .filter(pet -> pet.getAge() == age)
                        .collect(Collectors.toList());
                break;

            case 4:
                System.out.println("\n Critério escolhido: Peso");
                System.out.println("Digite o peso desejado: ");
                double weight = sc.nextDouble();
                sc.nextLine();
                filteredPets = allPets.stream()
                        .filter(pet -> pet.getWeight() == weight)
                        .collect(Collectors.toList());
                break;

            case 5:
                System.out.println("\n Critério escolhido: Raça");
                System.out.println("Digite a raça desejada: ");
                String breed = sc.nextLine();
                filteredPets = allPets.stream()
                        .filter(pet -> pet.getBreed().toLowerCase().contains(breed.toLowerCase()))
                        .collect(Collectors.toList());
                break;

            case 6:
                System.out.println("\n Critério escolhido: Endereço");
                System.out.println("Digite a cidade desejada: ");
                String city = sc.nextLine();
                filteredPets = allPets.stream()
                        .filter(pet -> pet.getAddress().getCity().contains(city))
                        .collect(Collectors.toList());
                break;

            case 7:
                System.out.println("\n Critério escolhido: Espécie");
                System.out.println("Escolha a espécie desejada: ");
                System.out.println("1- Gato");
                System.out.println("2- Cachorro");
                int op4 = sc.nextInt();
                sc.nextLine();

                if (op4 == 1) {
                    filteredPets = allPets.stream()
                            .filter(pet -> pet.getSpecies().equals(PetSpecies.GATO))
                            .collect(Collectors.toList());
                } else if (op4 == 2) {
                    filteredPets = allPets.stream()
                            .filter(pet -> pet.getSpecies().equals(PetSpecies.CACHORRO))
                            .collect(Collectors.toList());
                } else {
                    throw new DataValidationException("Opção Inválida, tente novamente");
                }
                break;

        }
        System.out.println("\n--- Resultado da Busca ---");
        if (filteredPets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com este critério.");
        } else {
            for (Pet pet : filteredPets) {
                System.out.println(filteredPets.indexOf(pet) + " - " + pet);
            }
        }
    }

    public static void showAllPets() {
        System.out.println("Listar todos os pets:");
        PetRepository petRepository = new PetRepository();
        List<Pet> allPets = petRepository.findAll();
        for (Pet pet : allPets) {
            System.out.println(allPets.indexOf(pet) + " - " + pet);
        }
    }
}

