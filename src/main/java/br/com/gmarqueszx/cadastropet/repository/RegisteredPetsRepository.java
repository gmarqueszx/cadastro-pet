package br.com.gmarqueszx.cadastropet.repository;

import br.com.gmarqueszx.cadastropet.model.Pet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class RegisteredPetsRepository {
    public static void saveRegisteredPets(Pet pet){
        BufferedWriter bw = null;
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = String.valueOf(today).replace("-" , "");
        String formattedTime = String.valueOf(formatter.format(time)).replace(":" , "");
        final String DIRECTORY_PATH = "src\\main\\java\\br\\com\\gmarqueszx" +
                "\\cadastropet\\registeredpets\\" + formattedDate + "T" + formattedTime + "-" + pet.getNome().toUpperCase().replace(" " , "") + ".txt";
        try {
            bw = new BufferedWriter(new FileWriter(DIRECTORY_PATH));
            bw.write(pet.getNome() + '\n');
            bw.write("" +pet.getSpecies() + '\n');
            bw.write("" +pet.getGender() + '\n');
            bw.write(pet.getAddress().getStreet() + ", "
                    + pet.getAddress().getHomeNumber() + ", " + pet.getAddress().getCity() + '\n');
            bw.write(pet.getAge() + " anos\n");
            bw.write(pet.getWeight() + " kg\n");
            bw.write(pet.getBreed() + '\n');
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
