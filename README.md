Sistema Web de Massoterapia

Sistema web desenvolvido para gerenciamento de atendimentos de massoterapia, permitindo que clientes realizem agendamentos online e que a profissional gerencie sua agenda de forma prática e organizada.

Funcionalidades

Área do Cliente
 - Realizar agendamentos online
 - Consultar agendamentos por nome e telefone
 - Cancelar agendamentos
 - Visualizar status do atendimento

Área da Massoterapeuta
 - Login administrativo
 - Dashboard com métricas de agendamentos
 - Visualização da agenda por data
 - Conclusão de atendimentos
 - Cancelamento de atendimentos
 - Edição de agendamentos
 - Notificações
 - Envio automático de mensagens via WhatsApp para novos agendamentos

Tecnologias Utilizadas
 - Backend
 - Java 21
 - Spring Boot
 - Spring MVC
 - Spring Data JPA
 - Hibernate

Banco de Dados
 - MySQL

Frontend
 - Thymeleaf
 - HTML5
 - CSS3
 - Bootstrap Icons

Ferramentas
 - Maven
 - Git
 - GitHub
 - Railway

Integrações
 - CallMeBot API (WhatsApp)

Arquitetura

O projeto segue o padrão MVC (Model-View-Controller) e uma arquitetura em camadas:

 - Controller -> Service -> Repository -> MySQL

Camadas
 - Controller: Responsável por receber as requisições HTTP e encaminhá-las para as regras de negócio.

 - Service: Contém as validações e regras de negócio da aplicação.

 - Repository: Responsável pela comunicação com o banco de dados através do Spring Data JPA.

Funcionalidades Implementadas
 - Cadastro automático de clientes
 - Controle de horários disponíveis
 - Validação de conflitos de horário
 - Consulta de agendamentos
 - Atualização de status
 - Integração com WhatsApp
 - Persistência em banco de dados
 - Deploy em ambiente de produção

A aplicação está hospedada na plataforma Railway.

Aprendizados

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em:
 - Programação Orientada a Objetos
 - Spring Boot
 - Spring MVC
 - JPA/Hibernate
 - Banco de Dados Relacional
 - Integração com APIs Externas
 - Arquitetura em Camadas
 - Deploy de aplicações Java
 - Controle de versão com Git e GitHub

Sistema online: https://sistemamassoterapiaweb-production.up.railway.app/
