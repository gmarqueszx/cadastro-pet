package br.com.gmarqueszx.cadastropet;

import br.com.gmarqueszx.cadastropet.repository.FormRepository;
import br.com.gmarqueszx.cadastropet.service.RegisterPet;

public class Application {
    public static void main(String[] args) {
        RegisterPet.registerPet();
    }
}
