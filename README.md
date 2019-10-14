# banco.pan.avaliacao
Visao geral da API:
http://localhost:8888/swagger-ui.html#/

Link para postman collection com exemplos:
https://www.getpostman.com/collections/3f84562d4ba59ba5f8a5


Respostas:

Cenário 1 - Consultar Cliente
Dado que o cliente deseja consultar o seu cadastro.
Criar endpoint para consulta dos dados cadastrais utlizando CPF como parâmetro.

Implementado em (exemplo):
http://localhost:8888/clientes/list/43508588068


Cenário 2 - Consultar CEP
Dado que o cliente deseja consultar o seu endereço pelo CEP.
Criar endpoint para consulta de endereço utlizando CEP como parâmetro.

Implementado em (exemplo):
http://localhost:8888/servicos/consultaEndereco/91330500


Cenário 3 – Consultar Estados
Dado que o cliente deseja escolher o seu estado.
Criar endpoint para consulta de estados.
Bônus:
Ordenar os estados da seguinte maneira:
• O primeiro e o segundo estado devem ser São Paulo e Rio de Janeiro, respectivamente;
• Os demais estados devem estar ordenados em ordem alfabética.

Implementado em (exemplo):
http://localhost:8888/servicos/consultaEstados


Cenário 4 – Consultar Municípios
Dado que o cliente deseja escolher o seu município.
Criar endpoint para consulta de municípios com base no estado.

Implementado em (exemplo):
http://localhost:8888/servicos/consultaMunicipios/RS


Bônus:
Cenário 5 – Alterar endereço
Dado que o cliente deseja alterar o seu endereço.
Criar endpoint para alterar o endereço do cliente.

Implementado com PUT request para:
localhost:8888/clientes/alteraCliente/43508588068

headers:
key: Content-Type
value: application/json

payload(exemplo):
{
"nome" : "Christian",
"sobrenome" : "Burger",
"cpf" : "43508588068",
"endereco" : {
"cpf" : "43508588068",
"cep": "91330-290",
"logradouro":"Rua Matias Jose Bins, 514",
"bairro":"Tres Figueiras",
"municipio":"Porto Alegre",
"estado":"RS",
"pais":"Brazil"}
}

Implementacao 
Linguagem: JAVA: Sim.
Framework: Spring Boot: Sim.
Testes unitários: Junit: Sim.
Mock: Mockito: Sim.
RESTFull: Sim.
Database em memória: Sim, implementado com: com.h2database

Abs,
Christian
