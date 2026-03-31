# Mindscribe API (Projeto Evernote)

Uma API RESTful desenvolvida em **Java** com **Spring Boot** para o gerenciamento de notas pessoais, inspirada no Evernote. A aplicação permite o cadastro de usuários, autenticação segura via **JWT**, e um CRUD completo para o gerenciamento de notas individuais de cada usuário.

## 🚀 Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e práticas:

- **Java 17**
- **Spring Boot 3.x**
  - **Spring Web** (para criação da API REST)
  - **Spring Data JPA** (persistência de dados)
  - **Spring Security** (segurança e controle de acesso)
  - **Spring Validation** (validação de dados de entrada)
- **PostgreSQL** (Banco de dados relacional)
- **Flyway** (Migrations e controle de versão do banco de dados)
- **JWT via Java-JWT (Auth0)** (Autenticação baseada em tokens)
- **Lombok** (Redução de boilerplate de código)
- **Docker & Docker Compose** (Containerização do banco de dados)

## 📂 Estrutura e Arquitetura do Projeto

O projeto segue uma arquitetura em camadas bem definida para separar responsabilidades:

- `controller`: Expõe os endpoints (APIs) HTTP.
- `service` / `service.impl`: Regras de negócio da aplicação.
- `repository`: Interfaces do Spring Data JPA para acesso ao banco.
- `domain`: Entidades JPA representando as tabelas do banco de dados (`UserEntity`, `NoteEntity`).
- `dto`: Objetos de Transferência de Dados para requests e responses, garantindo a separação entre a camada de apresentação e persistência.
- `security`: Configurações do Spring Security, filtros de requisição e geração/validação de tokens JWT.
- `exception`: Manipulador global de exceções (`GlobalExceptionHandler`).

### Banco de Dados (Flyway)
O banco de dados é versionado usando Flyway. As migrations estão localizadas em `src/main/resources/db/migration`, contendo:
- `V1__create_tables_users_and_notes.sql`: Cria as tabelas `tb_users` e `tb_notes`.

## ⚙️ Pré-requisitos

Para rodar este projeto, você precisará ter instalado em sua máquina:
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/) (opcional, o wrapper `mvnw` está incluído)
- [Docker e Docker Compose](https://www.docker.com/) (para subir o PostgreSQL facilmente)

## 🛠️ Como Executar a Aplicação

1. **Clone o repositório** (se ainda não o fez):
   ```bash
   git clone <url-do-repositorio>
   cd project
   ```

2. **Inicie o Banco de Dados com Docker**:
   Na raiz do projeto (onde está o `docker-compose.yml`), rode o comando:
   ```bash
   docker-compose up -d
   ```
   *Isso iniciará um contêiner do PostgreSQL (`mindscribe-db`) na porta 5432 com as credenciais padrão definidas na aplicação.*

3. **Inicie a aplicação Spring Boot**:
   Utilize o wrapper do Maven para executar a aplicação:
   ```bash
   # Em sistemas Unix/Linux/macOS
   ./mvnw spring-boot:run

   # No Windows
   mvnw.cmd spring-boot:run
   ```

4. A API estará disponível no endereço: `http://localhost:8080`.

## 📌 Documentação das Rotas (Endpoints)

Todas as requisições (exceto Cadastro e Login) precisam enviar o header `Authorization` contendo o token JWT: `Bearer <seu_token>`.

### Autenticação e Usuários
| Método | Rota | Descrição | Requer Autenticação |
| :--- | :--- | :--- | :--- |
| `POST` | `/users` | Cadastra um novo usuário. | ❌ Não |
| `POST` | `/auth/login` | Autentica e retorna um Token JWT. | ❌ Não |

### Notas
| Método | Rota | Descrição | Requer Autenticação |
| :--- | :--- | :--- | :--- |
| `POST` | `/notes` | Cria uma nova nota para o usuário. | ✅ Sim |
| `GET` | `/notes` | Lista todas as notas do usuário autenticado. | ✅ Sim |
| `PUT` | `/notes/{noteId}`| Atualiza os dados de uma nota específica. | ✅ Sim |
| `DELETE` | `/notes/{noteId}`| Deleta uma nota do usuário. | ✅ Sim |

## 🔒 Segurança

- As senhas dos usuários são criptografadas (hashing) no banco de dados antes do salvamento (`PasswordEncoder`).
- Os endpoints de `/notes` estão protegidos e avaliam automaticamente o contexto de segurança (`@AuthenticationPrincipal`) para garantir que os usuários só modifiquem ou visualizem suas próprias notas.
- O segredo JWT pode ser customizado alterando a propriedade `api.security.token.secret` no arquivo `application.properties`.
