# Microserviços com Spring Boot: Cadastro de Usuário e Notificação por E-mail
Este repositório contém um projeto de exemplo que demonstra uma arquitetura de microserviços utilizando Spring Boot e Java. A aplicação simula um fluxo de cadastro de usuário, onde um serviço é responsável por gerenciar os usuários e outro é responsável por enviar notificações por e-mail de forma assíncrona.

Sobre o Projeto
A aplicação é dividida em dois microserviços independentes que se comunicam através de um message broker (RabbitMQ), garantindo desacoplamento e resiliência.

**user-service**: Uma API REST para o gerenciamento de usuários. Sua principal responsabilidade é receber os dados de um novo usuário, persisti-los em seu próprio banco de dados e publicar um evento de "usuário criado".

**email-service**: Um serviço de background que atua como um consumidor de eventos. Ele escuta os eventos de "usuário criado", processa-os para enviar um e-mail de boas-vindas e registra o status do envio (enviado com sucesso ou com erro) em seu banco de dados.

# Arquitetura e Fluxo de Dados
O fluxo de dados na aplicação ocorre da seguinte forma:

Um cliente (ex: um frontend ou Postman) envia uma requisição POST para o endpoint /users do user-service contendo o nome e o e-mail do novo usuário.

O user-service valida os dados, salva o novo usuário em sua base de dados (PostgreSQL) e, em seguida, publica uma mensagem em uma fila do RabbitMQ. A mensagem contém as informações necessárias para o envio do e-mail.

O email-service, que está conectado à mesma fila do RabbitMQ, consome a mensagem assim que ela é publicada.

Ao receber a mensagem, o email-service utiliza um serviço de e-mail (configurado via SMTP) para enviar uma mensagem de boas-vindas ao usuário.

Após a tentativa de envio, o email-service salva um registro da operação em sua própria base de dados (PostgreSQL), incluindo o status do envio (SENT ou ERROR).

# Tecnologias Utilizadas
Este projeto foi construído com as seguintes tecnologias e ferramentas:

* Java 21
* Framework Spring (Spring Web, Data JPA, AMQP, Starter Mail)
* Jakarta Bean Validation (para validação de DTOs)
* PostgreSQL
* RabbitMQ (como message broker para comunicação assíncrona)
