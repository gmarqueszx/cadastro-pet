package br.com.gmarqueszx.cadastropet.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FormRepository {
    public static void readForm() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\bielp\\OneDrive\\" +
                    "Área de Trabalho\\João Gabriel\\Intelli_Projects\\CadastroPet\\src\\form.txt"));
            br.lines().forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
