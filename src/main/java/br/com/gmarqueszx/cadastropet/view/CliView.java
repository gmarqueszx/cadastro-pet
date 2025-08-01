package br.com.gmarqueszx.cadastropet.view;

import br.com.gmarqueszx.cadastropet.dto.PetRegistrationData;
import br.com.gmarqueszx.cadastropet.repository.FormRepository;
import br.com.gmarqueszx.cadastropet.repository.PetRepository;
import br.com.gmarqueszx.cadastropet.service.PetService;

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

                case 3:

                case 4:
                    showListAllPetsFlow();
                    break;
                case 5:

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

}
