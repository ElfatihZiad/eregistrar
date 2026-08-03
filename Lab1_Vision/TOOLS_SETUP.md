# Lab 1, Part 2: Development Tools Setup

Technology stack chosen for the eRegistrar project: **Java + Spring Boot**,
with PlantUML for UML modelling and Git/GitHub for version control.

## Verified on this machine

The raw terminal output is in [tool_versions.txt](tool_versions.txt), captured
on August 3, 2026. Summary:

| Tool | Purpose | Version installed |
|---|---|---|
| macOS | Development OS | 26.5.2 (build 25F84) |
| OpenJDK (JDK) | Java SE development kit, compiles and runs all Java work in Labs 6 and 7 | 11.0.30 (Homebrew) |
| javac | Java compiler | 11.0.30 |
| Git | Version control | 2.39.5 |
| PlantUML | UML diagramming: use case, sequence, collaboration, VOPC, class and architecture diagrams | 1.2025.4 (`tools/plantuml.jar`) |
| Apache Maven | Java build tool | Supplied per-project by the Maven Wrapper (`mvnw`) generated with each Spring Boot project in Lab 7, so no global install is required |

## UML tool

PlantUML was chosen over a GUI tool (StarUML, Visual Paradigm, draw.io)
because the diagrams are plain text, which renders identically on any
machine. Class-style diagrams use PlantUML's built-in `smetana` layout
engine, so no Graphviz installation is required.

## Still to install

| Tool | Needed for | Command |
|---|---|---|
| JDK 8 and JDK 17/21 | Lab 6 asks for evidence of multiple JDK versions side by side; only JDK 11 is currently installed | `brew install openjdk@8 openjdk@17` |
| Eclipse IDE for Enterprise Java Developers (or IntelliJ IDEA) | Lab 6/7 IDE screenshots | Download from <https://www.eclipse.org/downloads/packages/> |

These need an installer or administrator action, so they're listed here
rather than performed automatically. Everything in Labs 6 and 7 in this
repository builds and runs fine on the JDK 11 that's already installed.
