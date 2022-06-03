<h1 align="center">
   <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" width="25" height="25"/>
    <a>Workshop APIRest</a>
</h1>

<h3 align="center">APIRest para ecommerce desenvolvida utilizando Java e Springboot</h3>

<h1 align="center">
    <img align="center" width=600 src="https://i.imgur.com/IsRrTzg.png">
</h1>

<h3 align="center">
    <a href="https://springboot-workshop.herokuapp.com/swagger-ui/index.html#/">Documentação</a>
</h3>

# Indice

- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Rodando localmente](#rodando-localmente)

## 🛠 Tecnologias utilizadas

- [Java](https://www.java.com/pt-BR/)
- [Maven](https://maven.apache.org/)
- [Springboot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2 Database](https://mvnrepository.com/artifact/com.h2database/h2)
- [Swagger](https://mvnrepository.com/artifact/io.springfox/springfox-swagger2)
- [PostgreSQL](https://www.postgresql.org/)
- [Junit](https://junit.org/junit5/)
- [Heroku](https://https://heroku.com/)
- [GitHub Actions](https://docs.github.com/pt/actions)
  <br>

## 💻 Funcionalidades
- CRUD de usuarios, produtos, categorias e ordens
- Integração total com o Spring Framework mais recente para o desenvolvimento da API.
- Mapeamento de exceções para a resposta HTTP correta com detalhes de exceção no corpo.
- Um banco de dados na memória (H2) para armazenar os dados para fins de testes.
- Testes automatizados com Junit/Mockito.
- Heroku Cloud para fazer deploy da API.
- CI/CD utilizando Github actions para compilar, testar e fazer deploy na cloud(HEROKU) sempre que houver alteração na branch master.
- Toda a API é "autodocumentada" pelo Swagger2.

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/lucasfroque/WorkShopAPI
```

Entre no diretório do projeto

```bash
  cd WorkShopAPI
```

Instale as dependências

```bash
  mvn clean install
```

Inicie o servidor

```bash
  mvn spring-boot:run
```


## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```
