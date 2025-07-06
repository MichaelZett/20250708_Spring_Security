# Netzfim Grundprojekt
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
## Configuration
Hier liegen alle Konfigurationen
## OpenApi
Wird zur Beschreibung der Rest-Schnittstelle benutzt. Außerdem ist die swagger-ui in Verwendung.
## Lombok
Wir eingesetzt, um Boilerplate code zu reduzieren
## Test
Es werden testcontainers eingesetzt, um für die Tests die DB starten zu können (docker muss laufen)
### Sonstiges
-surefire-plugin mockito als Instrumentierungsagent bekannt machen (sonst mit zukünftigen Java Versionen Fehler)

