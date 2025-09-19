## Screenmatch
Screenmatch é uma plataforma fictícia que permite ao usuário consultar informações sobre suas séries favoritas, como número de temporadas, melhores episódios, atores, etc.

<br>

## 🚀 Tecnologias 
- Java 21+
- Spring Boot 3.x
- Spring Data JPA
- Jackson (serialização de dados JSON da OmdbAPI)
- PostgreSQL
- Maven (gerenciamento de dependências)

<br>

## ⚙️ Funcionalidades:
- Buscar séries por nome
- Buscar séries em que um ator/atriz trabalhou
- Filtrar séries por número de temporadas
- Filtrar séries por categoria
- Listar os 5 melhores episódios de uma série

<br>

## 📡 Endpoints Principais

| Método | Endpoint                         | Descrição                              |
|--------|----------------------------------|----------------------------------------| 
| GET    | /series                          | Lista todos as séries                  |
| GET    | /series/{id}                     | Busca uma série por ID                 |
| GET    | /series/{id}/temporadas/todas    | Lista todas os episódios               |
| GET    | /series/{id}/temporadas/{numero} | Lista episódios por temporada          |
| GET    | /series/top5                     | Lista as 5 séries melhores avaliadas   |
| GET    | /series/{id}/temporadas/top      | Lista os 5 melhores episódios da série |
| GET    | /categoria/{categoria}           | Lista séries por categoria             |

<br>

## ℹ️ Sobre o projeto
Este é um projeto fictício desenvolvido sem fins comerciais com o propósito de fixar os conhecimentos aprendidos ao longo da formação Java Web Spring da Alura.
