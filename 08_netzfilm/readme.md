# Netzfilm Grundprojekt
## build
Das Projekt wird mit Maven gebaut. Maven Wrapper ist konfiguriert, so dass kein Maven vorliegen muss.
## Java
Java 21 wird vorausgesetzt
## Spring Boot
Die Anwendung verwendet Spring Boot. Der Tomcat hört auf port 10000
## Datenbank
Es wird eine postgresql DB erwartet, diese ist im docker-compose.yml definiert.
Durch die spring-boot-docker-compose dependency wird diese gestartet. Docker (Desktop) muss laufen.
## Hibernate
Übernimmt die Schemaerzeugung. Grunddaten werden in data.sql angelegt.
## Technische Schichten
Die technischen Schichten sind
- adapter: Schnittstellen
- application: übergreifenden Logik
- domain: Domänenlogik
- values: value-Objekte, Exceptions
## Movie
Eine Vertikale für Filme-Crud Operationen. Sowohl als Rest-Schnittstelle als auch einem thymeleaf Frontend.
## User
Unser User-Management.
## Staff
Unserer Mitarbeiter. Sie sind User.
## Customer
Eine Vertikale für Kunden-Crud Operationen. Sowohl als Rest-Schnittstelle als auch einem thymeleaf Frontend. Kunden sind auch User
## Rent
Kunden können Filme leihen
## Configuration
Hier liegen alle Konfigurationen
## OpenApi
Wird zur Beschreibung der Rest-Schnittstelle benutzt. Außerdem ist die swagger-ui in Verwendung.
## Lombok
Wir eingesetzt, um Boilerplate code zu reduzieren
## Test
Es werden testcontainers eingesetzt, um für die Tests die DB starten zu können (docker muss laufen)
### Sonstiges
- surefire-plugin mockito als Instrumentierungsagent bekannt machen (sonst mit zukünftigen Java Versionen Fehler)

# Security - Method Level Security ist an
- Nur-Rest-Api: Customer können nur ihre eigenen Leihen sehen und nur für sich ausleihen
- Neue Rolle Vip_Staff, neues Feld in Customer: vip - nur Vip_Staff darf vip_customer sehen und bearbeiten
- Kunden sehen nur die Filme, die zu ihrem Alter passen

# List der Credentials
## Admin
- erik:erik
## Admin und Staff
- petra:petra
## Vip_Staff
- frank:frank (darf VIP bearbeiten)
## Customer
- anke:anke uuid: 77c4f5dd-b29c-4976-9c30-47cc00f0f7d2
- werner:werner uuid: 984008c7-afee-4067-955e-0b55a21de59e (ist VIP Kunde)
- fritz:fritz (10 Jahre)
