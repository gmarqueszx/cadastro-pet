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

    private static final String DIRECTORY_PATH = "registeredPets";

    public PetRepository() {
        File dir = new File(DIRECTORY_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void save(Pet pet) {
        System.out.println("--- DEBUG: O MÉTODO repository.save() FOI CHAMADO PARA O PET: " + pet.getName() + " ---");
        String filePath = pet.getSourceFilePath();

        if (filePath == null || filePath.isBlank()) {
            createNewFileForPet(pet);
        } else {
            updateExistingFile(pet);
        }
    }

    private void createNewFileForPet(Pet pet) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm");
        String timestamp = LocalDateTime.now().format(formatter);
        String petNameFormatted = pet.getName().toUpperCase().replace(" ", "");
        String fileName = timestamp + "-" + petNameFormatted + ".txt";
        String newFilePath = DIRECTORY_PATH + File.separator + fileName;

        pet.setSourceFilePath(newFilePath);
        writeToFile(pet, newFilePath);
    }

    private void updateExistingFile(Pet pet) {
        writeToFile(pet, pet.getSourceFilePath());
    }

    private void writeToFile(Pet pet, String filePath) {
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
        } catch (IOException e) {
            System.err.println("❌ Erro ao escrever no arquivo " + filePath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteByPath(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return;
        }
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            if (!fileToDelete.delete()) {
                System.err.println("AVISO: Falha ao remover arquivo: " + filePath);
            }
        }
    }

    public List<Pet> findAll() {
        List<Pet> pets = new ArrayList<>();
        File dir = new File(DIRECTORY_PATH);

        System.out.println("=====================================================================");
        System.out.println("DEBUG: Procurando por arquivos no diretório: " + dir.getAbsolutePath());

        File[] files = dir.listFiles();

        if (files == null) {
            System.out.println("DEBUG: ERRO CRÍTICO - dir.listFiles() retornou null.");
            System.out.println("         Isso significa que o diretório não foi encontrado ou não é um diretório.");
        } else {
            System.out.println("DEBUG: Encontrados " + files.length + " itens no diretório.");
        }
        System.out.println("=====================================================================");

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Pet pet = readPetFromFile(file);
                    if (pet != null) {
                        pets.add(pet);
                    }
                }
            }
        }
        return pets;
    }

    private Pet readPetFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Pet pet = new Pet();
            Address address = new Address();

            String name = reader.readLine();
            String speciesStr = reader.readLine();
            String genderStr = reader.readLine();
            String addressLine = reader.readLine();
            String ageLine = reader.readLine();
            String weightLine = reader.readLine();
            String breed = reader.readLine();

            if (name == null || speciesStr == null) {
                return null;
            }

            pet.setName(name);
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
            return null;
        }
    }
    public void delete(Pet pet) {
        if (pet == null || pet.getSourceFilePath() == null || pet.getSourceFilePath().isBlank()) {
            System.err.println("AVISO: Tentativa de deletar um pet sem um arquivo de origem válido.");
            return;
        }

        File fileToDelete = new File(pet.getSourceFilePath());

        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("LOG: Arquivo deletado com sucesso: " + pet.getSourceFilePath());
            } else {
                System.err.println("❌ ERRO: Falha ao deletar o arquivo: " + pet.getSourceFilePath());
            }
        } else {
            System.err.println("AVISO: Arquivo a ser deletado não foi encontrado: " + pet.getSourceFilePath());
        }
    }
}
