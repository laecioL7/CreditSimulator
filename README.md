# 💸 Credit Loan Simulator API

API REST desenvolvida com Spring Boot que realiza simulações de crédito (empréstimo), calculando o valor das parcelas mensais com base no valor solicitado, prazo de pagamento e faixa etária do cliente.

---

## 🚀 Tecnologias utilizadas

- ⚙️ Spring Boot
- 🧮 Spring Data JPA
- 🌐 Spring Web
- 📄 Swagger (OpenAPI)
- 🛢️ Banco de Dados H2
- 🧪 JUnit 5 + Mockito
- ☕ Java 17+
- 📦 Maven

> 💡 Projeto com foco em boas práticas, organização e clareza para fins avaliativos.

## Arquitetura
Utilizei a Arquitetura de Três Camadas(Three-Layer Architecture) separando a aplicação em:
- Controller Layer
- Service Layer
- Repository Layer

---

## 📑 Funcionalidade principal

### ✅ Endpoint de simulação de crédito

`POST /api/simulations`

#### 🔸 Request JSON

```json
{
  "clientBirthDate": "1990-01-01",
  "loanAmount": 10000.0,
  "paymentTermInMonths": 12
}
````
#### 🔸 Response JSON
````json
{
  "monthlyInstallmentAmount": 856.07,
  "totalAmountToBePaid": 10272.84,
  "totalInterestPaid": 272.84
}

````
💡 Regras de negócio
A taxa de juros é definida com base na faixa etária:

Até 25 anos → 5% ao ano

De 26 a 40 anos → 3% ao ano

De 41 a 60 anos → 2% ao ano

Acima de 60 anos → 4% ao ano

O cálculo das parcelas usa a fórmula de juros compostos.

---

Documentação swagger:
[http://localhost:8089/swagger-ui.html](http://localhost:8089/swagger-ui/index.html)

---

🧪 Como executar localmente
✅ Pré-requisitos
Java 17+
Maven 3.8+

▶️ Passos para rodar o projeto com maven

# Clone o repositório
````bash
git clone https://github.com/seu-usuario/seu-repo.git
cd seu-repo

# Build do projeto
./mvnw clean install

# Executar aplicação
./mvnw spring-boot:run
````
▶️ Passos para rodar o projeto com docker
✅ Pré-requisitos
Docker instalado

🛠️ Construa a imagem Docker
````bash
# constroi a imagem
docker build -t credit-simulator-app .

#executa
docker run -p 8089:8089 credit-simulator-app

````

🛢️ Banco de dados H2
Banco em memória para facilitar testes e desenvolvimento local.

Acesse o console:
http://localhost:8089/h2-console
Credenciais:
````yaml
JDBC URL:jdbc:h2:mem:simulationdb
Usuário: sa
Senha: (deixe em branco)
````
---
Curl para testes:
````bash
curl --request POST \
  --url http://localhost:8089/simulation \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/11.2.0' \
  --data '{
	"loanAmount": 1000.0,
	"clientBirthDate": "1989-06-15",
	"paymentTermInMonths": 24
}'
