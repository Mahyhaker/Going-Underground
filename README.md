# 🚀 TaskFlow

Sistema completo de gerenciamento de tarefas com autenticação JWT, desenvolvido com **Spring Boot + Angular**.

---

## 📌 Sobre o projeto

O **TaskFlow** é uma aplicação fullstack que permite aos usuários:

* Criar e gerenciar tarefas
* Definir status e prioridade
* Filtrar e buscar tarefas
* Autenticar via JWT (login/registro)
* Visualizar suas tarefas de forma organizada

Projeto desenvolvido com foco em **boas práticas**, **arquitetura limpa** e **experiência do usuário**.

---

## 🛠️ Tecnologias utilizadas

### 🔙 Backend

* Java 21
* Spring Boot
* Spring Security
* JWT (Json Web Token)
* Spring Data JPA
* MySQL
* Maven

### 🔜 Frontend

* Angular
* TypeScript
* RxJS
* HTML + CSS

---

## 🔐 Funcionalidades

### Autenticação

* Registro de usuário
* Login com JWT
* Proteção de rotas
* Logout

### Tasks

* Criar tarefa
* Listar tarefas
* Editar tarefa
* Excluir tarefa
* Buscar por título
* Filtrar por status
* Filtrar por prioridade

### UX

* Interface moderna
* Feedback visual (toasts)
* Confirmação de exclusão

---

## 🧱 Arquitetura

### Backend

* Controller → Service → Repository
* DTOs para entrada/saída
* Segurança com filtro JWT
* Banco relacional com JPA

### Frontend

* Componentização por feature
* Serviços para API
* Interceptors (JWT)
* Formulários reativos

---

## ⚙️ Como rodar o projeto

### 🔙 Backend

```bash
# entrar na pasta do backend
cd taskflow

# rodar aplicação
./mvnw spring-boot:run
```

Backend rodando em:

```
http://localhost:8080
```

---

### 🔜 Frontend

```bash
cd taskflow-front

npm install
ng serve
```

Frontend rodando em:

```
http://localhost:4200
```

---

## 🗄️ Configuração do banco

Configure no arquivo:

```properties
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskflow_db
spring.datasource.username=root
spring.datasource.password=123456
```

---

## 🔑 Endpoints principais

### Auth

* `POST /auth/register`
* `POST /auth/login`

### Tasks

* `GET /tasks`
* `POST /tasks`
* `PUT /tasks/{id}`
* `DELETE /tasks/{id}`

---

## 🌐 Deploy (em andamento)

* Backend: Render
* Frontend: Vercel
* Banco: Railway

---

## 📸 Screenshots

*(adicione prints aqui depois)*

---

## 👨‍💻 Autor

Desenvolvido por **Anthony Mahyhaker**

* GitHub: https://github.com/Mahyhaker

---

## 🎯 Objetivo

Este projeto foi desenvolvido para:

* Prática de desenvolvimento fullstack
* Aplicação de segurança com JWT
* Construção de portfólio profissional

---

## 📄 Licença

Este projeto está sob a licença MIT.
