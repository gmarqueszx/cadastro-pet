package br.com.gmarqueszx.cadastropet.service;

import br.com.gmarqueszx.cadastropet.model.Address;
import br.com.gmarqueszx.cadastropet.repository.PetRepository;

import java.util.Scanner;

import static br.com.gmarqueszx.cadastropet.service.SearchRegisteredPets.allPets;
import static br.com.gmarqueszx.cadastropet.service.SearchRegisteredPets.filteredPets;

public class ChangePetData {
    public static void ChangePetData() {
        SearchRegisteredPets searchRegisteredPets = new SearchRegisteredPets();
        SearchRegisteredPets.filterPets();

        Scanner sc = new Scanner(System.in);

        System.out.println("\nDigite o número do pet que deseja alterar dados: ");
        int petId = sc.nextInt();
        sc.nextLine();

        System.out.println("\nEscolha o dado que deseja alterar do pet: ");
        System.out.println("1 - Nome");
        System.out.println("2 - Idade");
        System.out.println("3 - Peso");
        System.out.println("4 - Raça");
        System.out.println("5 - Endereço");

        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                System.out.println("Digite o novo nome do pet: ");
                String name = sc.nextLine();
                filteredPets.get(petId).setNome(name);
                PetRepository.update(filteredPets.get(petId));
                break;
            case 2:
                System.out.println("Digite o nova idade do pet: ");
                double age = sc.nextDouble();
                sc.nextLine();
                filteredPets.get(petId).setAge(age);
                PetRepository.update(filteredPets.get(petId));
                break;
            case 3:
                System.out.println("Digite o novo peso do pet: ");
                double weight = sc.nextDouble();
                sc.nextLine();
                filteredPets.get(petId).setWeight(weight);
                PetRepository.update(filteredPets.get(petId));
                break;
            case 4:
                System.out.println("Digite a nova raça do pet: ");
                String breed = sc.nextLine();
                filteredPets.get(petId).setBreed(breed);
                PetRepository.update(filteredPets.get(petId));
                break;
            case 5:
                System.out.println("Digite o novo endereço do pet: ");
                System.out.println("Cidade: ");
                String city = sc.nextLine();
                System.out.println("Rua: ");
                String street = sc.nextLine();
                System.out.println("Número da casa: ");
                int number = sc.nextInt();
                sc.nextLine();
                filteredPets.get(petId).setAddress(new Address(number, city, street));
                PetRepository.update(filteredPets.get(petId));
                break;
        }

    }
}
