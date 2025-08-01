package br.com.gmarqueszx.cadastropet;

import br.com.gmarqueszx.cadastropet.repository.PetRepository;
import br.com.gmarqueszx.cadastropet.service.PetService;
import br.com.gmarqueszx.cadastropet.view.CliView;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        PetRepository petRepository = new PetRepository();
        PetService petService = new PetService(petRepository);
        CliView cliView = new CliView(petService);
        cliView.startMainMenu();

    }
}
