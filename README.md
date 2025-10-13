
🐾 Sistema de Cadastro para Adoção de Pets

Este projeto é uma aplicação de console (CLI) desenvolvida em Java para gerenciar o cadastro de pets em um abrigo de animais. A aplicação permite realizar as operações básicas de um sistema de gerenciamento de dados (CRUD - Criar, Ler, Atualizar e Deletar) de forma interativa.

O projeto foi iniciado seguindo o desafio original proposto por @karilho e posteriormente evoluído para uma solução mais robusta com persistência em banco de dados.

📈 Evolução do Projeto
Inicialmente implementado com persistência em arquivos de texto, o projeto foi refatorado para utilizar JPA (Jakarta Persistence API) e Hibernate, conectando-se a um banco de dados PostgreSQL. Esta evolução demonstra a capacidade de migrar uma aplicação de um sistema de arquivos simples para uma solução de banco de dados relacional, uma habilidade essencial no desenvolvimento de software.

✨ Funcionalidades
[x] Cadastrar Novos Pets: Adiciona novos animais ao sistema através de um formulário interativo.

[x] Validação de Dados: Garante que todos os dados inseridos sigam as regras de negócio predefinidas.

[x] Listar Todos os Pets: Exibe uma lista completa de todos os animais cadastrados no banco de dados.

[x] Busca por Critérios: Permite filtrar a lista de pets com buscas parciais e case-insensitive, delegando a filtragem para o banco de dados para maior eficiência.

[x] Alterar Dados: Permite buscar um pet e alterar suas informações, com as mudanças sendo persistidas no banco.

[x] Deletar Pets: Permite buscar um pet e removê-lo permanentemente do banco de dados.

🏛️ Arquitetura do Projeto
O sistema foi desenvolvido seguindo uma arquitetura de 3 camadas para garantir a separação de responsabilidades, manutenibilidade e testabilidade do código:

View (view): Camada responsável por toda a interação com o usuário via console (menus, leitura de dados, exibição de resultados).

Service (service): Camada que contém a lógica de negócio da aplicação. Ela orquestra as ações, valida os dados e serve como ponte entre a View e o Repository.

Repository (repository): Camada de acesso a dados. É a única responsável por realizar as operações CRUD no banco de dados através do EntityManager do JPA.

🛠️ Tecnologias Utilizadas
Java 21

Maven (Gerenciador de Dependências)

JPA (Jakarta Persistence API) & Hibernate (Implementação ORM)

PostgreSQL (Banco de Dados Relacional)

Lombok (Para redução de código boilerplate)

⚙️ Configuração do Ambiente
Antes de executar, é necessário configurar a conexão com o banco de dados.

Banco de Dados: Certifique-se de ter uma instância do PostgreSQL rodando e crie um banco de dados.

SQL

CREATE DATABASE cadastro_pet;
Arquivo de Propriedades: Na pasta src/main/resources, crie um arquivo chamado db.properties. Este arquivo não é versionado no Git (.gitignore) para proteger informações sensíveis. Cole o conteúdo abaixo e ajuste com suas credenciais:

Properties

# src/main/resources/db.properties
jakarta.persistence.jdbc.url=jdbc:postgresql://localhost:5432/cadastro_pet
jakarta.persistence.jdbc.user=seu_usuario
jakarta.persistence.jdbc.password=sua_senha
🚀 Como Executar o Projeto
Pré-requisitos
Java JDK (versão 21 ou superior)

Apache Maven (versão 3.8 ou superior)

Git

PostgreSQL

1. Pelo Terminal (com Maven)
Bash

# Clone este repositório
$ git clone https://github.com/gmarqueszx/cadastro-pet.git

# Acesse a pasta do projeto
$ cd cadastro-pet

# Compile o projeto e gere o arquivo .jar
$ mvn clean install

# Execute a aplicação
$ java -jar target/CadastroPet-1.0-SNAPSHOT.jar
(Observação: O nome do arquivo .jar pode variar. Verifique o nome gerado na pasta target/)

2. Pela IDE (IntelliJ IDEA)
Abra o IntelliJ e selecione File -> Open....

Navegue até a pasta onde você clonou o projeto e a selecione.

Aguarde o IntelliJ carregar e indexar o projeto.

Certifique-se de ter criado e configurado o arquivo src/main/resources/db.properties conforme as instruções acima.

Encontre a classe Application.java no pacote br.com.gmarqueszx.cadastropet.

Clique com o botão direito no arquivo e selecione Run 'Application.main()'.

👨‍💻 Autor
João Gabriel Marques
