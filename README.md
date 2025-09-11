# APi REST de Help-desk utilizando arquitetura de Microsserviços

<p>A API de Helpdesk é um serviço RESTful desenvolvido para gerenciar tickets de suporte ao cliente e facilitar a comunicação entre usuários e agentes de suporte. Ela permite que os usuários criem, visualizem, atualizem e finalizem tickets de atendimento, enquanto administradores podem atribuir agentes, acompanhar o status dos tickets e gerenciar interações.</p>

## Ticket Microservice

- Responsável pela criação, atualização e acompanhamento de tickets de suporte abertos pelos clientes.

- Gerencia o relacionamento entre usuários, incluindo clientes que abrem chamados e técnicos de suporte que os atendem.

 ## Analysis Microservice

- Responsável pelo gerenciamento de SLAs (Service Level Agreements), permitindo criar, editar e remover regras.

- Monitora os tickets em andamento para verificar se os SLAs definidos estão sendo cumpridos.

 ## Storage Microservice

- Responsável por centralizar a lógica de armazenamento de arquivos do sistema.

- Armazena logs e anexos relacionados aos tickets, contendo informações detalhadas sobre os problemas reportados.

- Realiza o gerenciamento do armazenamento na AWS S3.

 ## Server (Service Registry e Service Discovery)

- Responsável por registrar e gerenciar as instâncias ativas dos microsserviços do sistema.

- Fornece mecanismos de descoberta de serviços (Service Discovery), permitindo que os microsserviços se comuniquem entre si de forma dinâmica e desacoplada.

- Garante escalabilidade e balanceamento de carga, facilitando a alta disponibilidade do sistema.

 ## APi GATEWAY

- Atua como ponto de entrada único para todos os microsserviços da aplicação.

- Responsável por rotear as requisições dos clientes para o microsserviço correto.
