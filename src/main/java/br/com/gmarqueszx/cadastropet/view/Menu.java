package br.com.gmarqueszx.cadastropet.view;

import java.util.Scanner;

public class Menu {
    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Cadastrar pet");
            System.out.println("2 - Alterar os dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
            System.out.println("5 - Listar pets por algum critério (idade, nome, raça)");
            System.out.println("6 - Sair\n");

            int op1 = sc.nextInt();

            switch (op1) {
                case 1:

            }




        }



    }
}
