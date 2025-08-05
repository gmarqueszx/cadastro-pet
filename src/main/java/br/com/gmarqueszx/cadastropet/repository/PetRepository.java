package br.com.gmarqueszx.cadastropet.repository;

import br.com.gmarqueszx.cadastropet.model.Address;
import br.com.gmarqueszx.cadastropet.model.Pet;
import br.com.gmarqueszx.cadastropet.model.enums.PetGender;
import br.com.gmarqueszx.cadastropet.model.enums.PetSpecies;
import br.com.gmarqueszx.cadastropet.util.Constants;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class PetRepository {

    // Define o nome do diretório como uma constante privada e estática.
    private static final String DIRECTORY_PATH = "src/main/java/br/com/gmarqueszx/cadastropet/registeredpets";

    /**
     */
    public static void save(Pet pet) {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdirs(); //
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String timestamp = LocalDateTime.now().format(formatter);
        String petNameFormatted = pet.getName().toUpperCase().replace(" ", "");
        String fileName = timestamp + "-" + petNameFormatted + ".txt";
        String filePath = DIRECTORY_PATH + File.separator + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String homeNumberStr = (pet.getAddress().getHomeNumber() == 0) ? Constants.notInformed : String.valueOf(pet.getAddress().getHomeNumber());
            String addressStr = pet.getAddress().getStreet() + ", " + homeNumberStr + ", " + pet.getAddress().getCity();
            String ageStr = (pet.getAge() == 0.0) ? Constants.notInformed : pet.getAge() + " anos";
            String weightStr = (pet.getWeight() == 0.0) ? Constants.notInformed : pet.getWeight() + " kg";

            writer.write(pet.getName() + "\n");
            writer.write(pet.getSpecies().name() + "\n");
            writer.write(pet.getGender().name() + "\n");
            writer.write(addressStr + "\n");
            writer.write(ageStr + "\n");
            writer.write(weightStr + "\n");
            writer.write(pet.getBreed() + "\n");

            System.out.println("✅ Pet salvo com sucesso em: " + filePath);
            pet.setSourceFilePath(filePath);

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar o arquivo do pet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void update(Pet pet) {
        String filePath = pet.getSourceFilePath();

        if (filePath != null || filePath.isEmpty()) {
            System.err.println("❌ Erro: Não foi possível atualizar o pet pois o arquivo de origem é desconhecido.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String homeNumberStr = (pet.getAddress().getHomeNumber() == 0) ? Constants.notInformed : String.valueOf(pet.getAddress().getHomeNumber());
            String addressStr = pet.getAddress().getStreet() + ", " + homeNumberStr + ", " + pet.getAddress().getCity();
            String ageStr = (pet.getAge() == 0.0) ? Constants.notInformed : pet.getAge() + " anos";
            String weightStr = (pet.getWeight() == 0.0) ? Constants.notInformed : pet.getWeight() + " kg";

            writer.write(pet.getName() + "\n");
            writer.write(pet.getSpecies().name() + "\n");
            writer.write(pet.getGender().name() + "\n");
            writer.write(addressStr + "\n");
            writer.write(ageStr + "\n");
            writer.write(weightStr + "\n");
            writer.write(pet.getBreed() + "\n");

            System.out.println("✅ Pet atualizado com sucesso em: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar o arquivo do pet: " + e.getMessage());
            return;
        }

    }

    public List<Pet> findAll() {
        List<Pet> pets = new ArrayList<>();
        File dir = new File(DIRECTORY_PATH);

        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Pet pet = readerPetFiles(file);
                    if (pet != null) {
                        pets.add(pet);
                    }
                }
            }
        }
        return pets;
    }


    private Pet readerPetFiles(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Pet pet = new Pet();
            Address address = new Address();

            String nome = reader.readLine();
            String speciesStr = reader.readLine();
            String genderStr = reader.readLine();
            String addressLine = reader.readLine();
            String ageLine = reader.readLine();
            String weightLine = reader.readLine();
            String breed = reader.readLine();

            if (nome == null || speciesStr == null) {
                System.err.println("Arquivo corrompido ou incompleto: " + file.getName());
                return null;
            }

            pet.setName(nome);
            pet.setBreed(breed);
            pet.setSpecies(PetSpecies.valueOf(speciesStr.toUpperCase()));
            pet.setGender(PetGender.valueOf(genderStr.toUpperCase()));

            if (ageLine.equals(Constants.notInformed)) {
                pet.setAge(0.0);
            } else {
                double age = Double.parseDouble(ageLine.replace(" anos", "").trim());
                pet.setAge(age);
            }

            if (weightLine.equals(Constants.notInformed)) {
                pet.setWeight(0.0);
            } else {
                double weight = Double.parseDouble(weightLine.replace(" kg", "").trim());
                pet.setWeight(weight);
            }

            String[] addressParts = addressLine.split(",");
            if (addressParts.length == 3) {
                address.setStreet(addressParts[0].trim());
                address.setCity(addressParts[2].trim());
                String numberStr = addressParts[1].trim();

                if (numberStr.equalsIgnoreCase(Constants.notInformed)) {
                    address.setHomeNumber(0);
                } else {
                    address.setHomeNumber(Integer.parseInt(numberStr));
                }
                pet.setAddress(address);
            }
            pet.setSourceFilePath(file.getPath());
            return pet;

        } catch (Exception e) {
            System.err.println("❌ Erro ao processar o arquivo " + file.getName() + ": " + e.getMessage());
            return null; // Retorna nulo se qualquer erro de parse ou leitura acontecer.
        }
    }

    public void deleteByPath(String filePath) {
        if (filePath == null || filePath.isBlank()) return;

        File fileToDelete = new File(filePath);
        if (fileToDelete.exists() && fileToDelete.delete()) {
            System.out.println("LOG: Arquivo antigo removido: " + filePath);
        } else {
            System.err.println("AVISO: Falha ao remover arquivo antigo: " + filePath);
        }
    }
}