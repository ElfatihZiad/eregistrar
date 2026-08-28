# Lab 7: Spring Boot Web Applications

**Author:** Ziad El Fatih, 618971

Two Spring Boot / Spring WebMVC applications: **eLibrary** (the tutorial
application, with the homepage banner renamed as the lab requires) and
**eRegistrar** (a second application built from scratch, whose content is the
course project from Labs 1 to 5). eLibrary is also published as its own
repository: <https://github.com/ElfatihZiad/elibrary>.

## Prerequisites

- Java SE JDK 11 (OpenJDK 11.0.30)
- Apache Maven, via the Maven Wrapper (`mvnw`) bundled with each project

Both projects are built against Spring Boot 2.7.18 on Java 11, since Spring
Initializr now targets Spring Boot 4.0 and Java 17+, which isn't available on
this machine. The tutorial's application layout, controller, templates and
DevTools workflow are otherwise unchanged.

## eLibrary

Directory: [elibrary/](elibrary/) · package `edu.mum.cs.cs425.elibrary`

The homepage banner reads *"Ziad El Fatih's elibrary - a digital library for
everyone"*, as the lab requires.

![eLibrary homepage](elibrary/screenshots/homepage.png)

```bash
cd Lab7_SpringBoot/elibrary && ./mvnw spring-boot:run
```

Open <http://localhost:8080>.

## eRegistrar

Directory: [eregistrar/](eregistrar/) · package `edu.mum.cs.cs425.eregistrar`

The course project itself: a layered Spring Boot application (controller,
service, repository, JPA entities, H2 database) implementing UC4 (Register
for Course) end to end, not just a static view. See the
[root README](../README.md) for the full write-up: architecture, layer
structure, install/run/database instructions, tests and evidence,
screenshots, and known limitations.

```bash
cd Lab7_SpringBoot/eregistrar && ./mvnw spring-boot:run
```

Open <http://localhost:8081>.

## DevTools and LiveReload

Both projects enable `spring-boot-devtools` in `application.properties`, so
editing a Java class triggers an automatic restart and editing a template or
CSS file is picked up on refresh.

## Build and test

```bash
cd Lab7_SpringBoot/elibrary && ./mvnw clean package && cd ../eregistrar && ./mvnw clean package
```
