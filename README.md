# CS425 — Software Engineering: Labs 1–7

**Student:** Ziad El Fatih — 618971
**Course project:** **eRegistrar** — a course scheduling and registration system
for a mid-sized university department.

Labs 1–5 are the analysis and design of eRegistrar, carried through the RUP
workflow from vision to use-case analysis. Lab 6 is the Java environment and
coding exercises. Lab 7 implements two Spring Boot web applications, one of
which is the first implementation slice of eRegistrar itself.

## Deliverables by lab

| Lab | Deliverable | Folder |
|---|---|---|
| **1** | Vision Document (problem statement, problem–need–feature table, stakeholders, product overview) and development tools setup | [Lab1_Vision/](Lab1_Vision/) |
| **2** | System Requirements Specification: use-case diagram, use-case descriptions for the major use cases, supplementary requirements, Git/GitHub setup | [Lab2_SRS/](Lab2_SRS/) |
| **3** | Architectural analysis and initial high-level system architecture (layered) with a deployment view | [Lab3_Architecture/](Lab3_Architecture/) |
| **4** | Sequence diagrams for the significant use cases, with boundary/control/entity analysis classes | [Lab4_SequenceDiagrams/](Lab4_SequenceDiagrams/) |
| **5** | Collaboration (communication) diagrams and VOPC class diagrams | [Lab5_Collaboration_VOPC/](Lab5_Collaboration_VOPC/) |
| **6** | Java environment setup evidence and the coding practice exercises (Student records app, `printHelloWorld`, `findSecondBiggest`) | [Lab6_Java_Setup_and_Coding/](Lab6_Java_Setup_and_Coding/) |
| **7** | Spring Boot applications: **eLibrary** (renamed banner) and **eRegistrar**, both running with tests and screenshots | [Lab7_SpringBoot/](Lab7_SpringBoot/) |
| **2 (cont.)** | Requirements/Design presentation deck | [presentation/](presentation/) |

## Reading order

1. [Vision Document](Lab1_Vision/eRegistrar_Vision_Document.md) — the problem and the feature set
2. [SRS](Lab2_SRS/eRegistrar_SRS.md) — actors, use-case model, use-case descriptions, NFRs
3. [Architecture](Lab3_Architecture/eRegistrar_Architecture.md) — constraints, candidates considered, selected layered architecture
4. [Sequence diagrams](Lab4_SequenceDiagrams/README.md) — use-case realisations over time
5. [Collaboration & VOPC](Lab5_Collaboration_VOPC/README.md) — the same realisations by object links, plus participating classes
6. [Lab 6 coding exercises](Lab6_Java_Setup_and_Coding/README.md)
7. [Lab 7 Spring Boot apps](Lab7_SpringBoot/README.md)

## Traceability

The artifacts form one chain: each feature in the Vision document maps to use
cases in the SRS (§6 of the SRS), each significant use case has a sequence
diagram (Lab 4) and a collaboration + VOPC pair (Lab 5), and the analysis
classes from Lab 5 map onto the subsystems of the Lab 3 architecture — control
classes become business-layer services, entity classes become the domain model,
boundary classes become Spring MVC controllers and their templates, as realised
in the Lab 7 eRegistrar application.

## Tooling

| Tool | Version | Used for |
|---|---|---|
| OpenJDK | 11.0.30 | Labs 6 and 7 |
| Apache Maven | 3.9.9 (via each project's `mvnw` wrapper) | Lab 7 builds |
| Spring Boot | 2.7.18 | Lab 7 applications |
| PlantUML | 1.2025.4 | Every UML diagram in Labs 2–5 |
| Git | 2.39.5 | Version control for all of the above |

`tools/plantuml.jar` and `tools/apache-maven-3.9.9/` are local, gitignored
copies — see [Lab1_Vision/TOOLS_SETUP.md](Lab1_Vision/TOOLS_SETUP.md) for how to
fetch them.

### Regenerating every diagram

```bash
java -jar tools/plantuml.jar -tpng Lab*/diagrams/*.puml
```

## Source material

The original assignment texts (`lab1.txt` … `lab7.txt`), the provided samples
(`Sample Vision Document.docx`, the use-case description PDF, the sample
architecture image) and the lecture slides in `lessons/` are kept alongside for
reference.
