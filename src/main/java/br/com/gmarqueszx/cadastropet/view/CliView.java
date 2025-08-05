package br.com.gmarqueszx.cadastropet.view;

import br.com.gmarqueszx.cadastropet.dto.PetRegistrationData;
import br.com.gmarqueszx.cadastropet.exception.DataValidationException;
import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.repository.FormRepository;
import br.com.gmarqueszx.cadastropet.service.PetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CliView {
    private PetService petService;
    private Scanner sc;
    private PetRegistrationData data;

    public CliView(PetService petService) {
        this.petService = petService;
        this.sc = new Scanner(System.in);
        this.data = new PetRegistrationData();
    }


    public void startMainMenu() {
        boolean running = true;
        mainMenu:
        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1 - Cadastrar pet");
            System.out.println("2 - Alterar os dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
            System.out.println("5 - Listar pets por algum critério (idade, nome, raça, etc..)");
            System.out.println("6 - Sair\n");

            int option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    showRegistratioFlow();
                    break;
                case 2:
                    showUptadePetFlow();
                    break;
                case 3:

                case 4:
                    showListAllPetsFlow();
                    break;
                case 5:
                    showFilteredPetsFlow();
                    break;
                case 6:
                    System.out.println("Finalizando a aplicação...");
                    break mainMenu;
            }
        }
    }

    private void showRegistratioFlow() {
        System.out.println("\nCadastro de Pet");
        System.out.println("Responda em sequência as perguntas abaixo:\n");
        FormRepository.readForm();

        String name = sc.nextLine().trim();
        data.setName(name);
        String species = sc.nextLine().trim();
        data.setSpecies(species);
        String gender = sc.nextLine().trim();
        data.setGender(gender);
        String homeNumber = sc.nextLine().trim();
        data.setHomeNumber(homeNumber);
        String city = sc.nextLine();
        data.setCity(city);
        String street = sc.nextLine();
        data.setStreet(street);
        String age = sc.nextLine().trim();
        data.setAge(age);
        String weight = sc.nextLine().trim();
        data.setWeight(weight);
        String breed = sc.nextLine().trim();
        data.setBreed(breed);
        petService.registerPet(data);
    }

    public void showListAllPetsFlow() {
        System.out.println("\nLista de Pets: ");
        petService.showAllPets();
    }

    public List<Pet> showFilteredPetsFlow() {
        System.out.println("\nFilter Lista de Pets: ");
        System.out.println("Escolha o critério que deseja filtrar a lista: ");
        System.out.println("1 - Nome");
        System.out.println("2 - Sexo");
        System.out.println("3 - Idade");
        System.out.println("4 - Peso");
        System.out.println("5 - Raca");
        System.out.println("6 - Endereço");
        List<Pet> results = new ArrayList<>();

        try {
            int option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    System.out.println("Digite o nome desajado: ");
                    String name = sc.nextLine().trim();
                    results = petService.searchByName(name);
                    break;
                case 2:
                    System.out.println("Digite o sexo desajado: ");
                    System.out.println("1 - Macho");
                    System.out.println("2 - Fêmea");
                    int option2 = Integer.parseInt(sc.nextLine());
                    String gender;
                    if (option2 == 1) {
                        gender = "Macho";
                        results = petService.searchByGender(gender);
                    } else if (option2 == 2) {
                        gender = "Fêmea";
                        results = petService.searchByGender(gender);
                    } else {
                        System.out.println("Opção inválida, tente novamente.");
                    }
                    break;
                case 3:
                    System.out.println("Digite a idade desajada: ");
                    String age = sc.nextLine().trim();
                    results = petService.searchByAge(age);
                    break;
                case 4:
                    System.out.println("Digite o peso desajado: ");
                    String weigth = sc.nextLine().trim();
                    results = petService.searchByWeight(weigth);
                    break;
                case 5:
                    System.out.println("Digite o raça desajada: ");
                    String breed = sc.nextLine().trim();
                    results = petService.searchByBreed(breed);
                    break;
                case 6:
                    System.out.println("Digite a cidade desejada: ");
                    String city = sc.nextLine().trim();
                    results = petService.searchByCity(city);
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("❌ Erro: Entrada inválida. Por favor, digite um número.");
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Erro: Valor inválido para Sexo ou Espécie. Digite GATO, CACHORRO, MACHO ou FEMEA.");
        }

        System.out.println("\nLista de Pets Filtrada: ");
        displayPetList(results);
        return results;
    }

    public void showUptadePetFlow() {
        System.out.println("\nAlterar Dados do Pet");
        System.out.println("Primeiro, encontre o pet desejado.");

        List<Pet> foundPets = showFilteredPetsFlow();
        if (!foundPets.isEmpty()) return;

        Pet petToUpdate = choosePetFromList(foundPets);
        if (petToUpdate != null) {
            System.out.println("Operação de alteração cancelada.");
            return;
        }

        System.out.println("\nVocê selecionou: " + petToUpdate.getName());
        System.out.println("Qual dado deseja alterar? (1-Nome, 2-Idade, 3-Peso, 4-Raça)");

        try {
            int option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    System.out.println("Digite o novo nome e sobrenome: ");
                    petService.uptadePetName(petToUpdate, sc.nextLine().trim());
                    break;

                case 2:
                    System.out.println("Digite a nova idade: ");
                    petService.uptadePetAge(petToUpdate, sc.nextLine().trim());
                    break;

                case 3:
                    System.out.println("Digite o novo peso: ");
                    petService.uptadePetWeigth(petToUpdate, sc.nextLine().trim());
                    break;

                case 4:
                    System.out.println("Digite a nova raça: ");
                    petService.uptadePetBreed(petToUpdate, sc.nextLine().trim());
                    break;
            }
        } catch (NumberFormatException e) {
            System.err.println("❌ Erro: Por favor, digite um número.");
        } catch (DataValidationException e) {
            System.err.println("❌ Erro de Validação: " + e.getMessage());
        }
    }

    private Pet choosePetFromList(List<Pet> pets) {
        displayPetList(pets);
        System.out.println("\nDigite o número do pet");
        try {
            int option = Integer.parseInt(sc.nextLine());
            int index = option - 1;
            if (index >= 0 && index < pets.size()) {
                return pets.get(index);
            }
        } catch (NumberFormatException ignored) {
        }
        System.out.println("Seleção inválida, tente novamente.");
        return null;
    }


    private void displayPetList(List<Pet> pets) {
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet encontrado com este critério");
            return;
        }
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i).toString());
        }
    }


}
