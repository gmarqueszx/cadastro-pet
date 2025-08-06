🐾 Sistema de Cadastro para Adoção de Pets

Este projeto é uma aplicação de console (CLI) desenvolvida em Java para gerenciar o cadastro de pets em um abrigo de animais, seguindo o desafio: https://github.com/karilho/desafioCadastro. A aplicação permite realizar as operações básicas de um sistema de gerenciamento de dados (CRUD - Criar, Ler, Atualizar e Deletar) de forma interativa.

Este foi um desafio prático para aplicar e solidificar conceitos de Programação Orientada a Objetos, manipulação de arquivos, tratamento de exceções e boas práticas de arquitetura de software.

✨ Funcionalidades
[x] Cadastrar Novos Pets: Adiciona novos animais ao sistema através de um formulário interativo no console.

[x] Validação de Dados: Garante que todos os dados inseridos sigam as regras de negócio predefinidas (ex: limites de idade e peso, formato do nome, etc.).

[x] Listar Todos os Pets: Exibe uma lista completa de todos os animais cadastrados.

[x] Busca por Critérios: Permite filtrar a lista de pets por um ou mais critérios (nome, idade, espécie, etc.) com busca case-insensitive e parcial.

[x] Alterar Dados: Permite buscar um pet e alterar suas informações (exceto tipo e sexo).

[x] Deletar Pets: Permite buscar um pet e removê-lo do sistema de forma permanente.

[x] Persistência em Arquivos: Todos os dados dos pets são salvos em arquivos .txt individuais, garantindo que as informações não sejam perdidas ao fechar a aplicação.

🏛️ Arquitetura do Projeto
O sistema foi desenvolvido seguindo uma arquitetura de 3 camadas para garantir a separação de responsabilidades, manutenibilidade e testabilidade do código:

View (view): Camada responsável por toda a interação com o usuário via console (menus, leitura de dados, exibição de resultados).

Service (service): Camada que contém a lógica de negócio da aplicação. Ela orquestra as ações, valida os dados e serve como ponte entre a View e o Repository.

Repository (repository): Camada de acesso a dados. É a única responsável por ler e escrever nos arquivos .txt que armazenam as informações dos pets.

🛠️ Tecnologias Utilizadas

Java 21: Linguagem principal do projeto.

Maven: Gerenciador de dependências e de build do projeto.

Lombok: Biblioteca para reduzir código boilerplate em classes de modelo (ex: getters, setters, construtores).

🚀 Como Executar o Projeto
Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina:

Java JDK (versão 21 ou superior)

Apache Maven (versão 3.8 ou superior)

Git

1. Pelo Terminal (com Maven)
Bash

# Clone este repositório
$ git clone https://github.com/seu-usuario/nome-do-repositorio.git

# Acesse a pasta do projeto
$ cd nome-do-repositorio

# Compile o projeto e gere o arquivo .jar
$ mvn clean install

# Execute a aplicação
$ java -jar target/CadastroPet-1.0-SNAPSHOT.jar
(Observação: O nome do arquivo .jar pode variar. Verifique o nome gerado na pasta target/)


2. Pela IDE (IntelliJ IDEA)
Abra o IntelliJ e selecione File -> Open....

Navegue até a pasta onde você clonou o projeto e a selecione.

Aguarde o IntelliJ carregar e indexar o projeto (pode levar um momento para baixar as dependências do Maven).

Encontre a classe Application.java no pacote br.com.gmarqueszx.cadastropet.
Clique com o botão direito no arquivo e selecione Run 'Application.main()'.


👨‍💻 Autor:
João Gabriel - www.linkedin.com/in/joaogabrielmarques/
