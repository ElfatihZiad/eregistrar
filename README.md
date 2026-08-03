# CS425 — Software Engineering: Labs 1–7

**Student:** Ziad El Fatih — 618971
**Course project:** **eRegistrar** — a course scheduling and registration system
for a mid-sized university department.

Labs 1–5 are the analysis and design of eRegistrar, carried through the RUP
workflow from vision to use-case analysis. Lab 6 is the Java environment and
coding exercises. Lab 7 implements two Spring Boot web applications, one of
which is the first implementation slice of eRegistrar itself.

**GitHub:**
[eregistrar](https://github.com/ElfatihZiad/eregistrar) (private, whole project) ·
[elibrary](https://github.com/ElfatihZiad/elibrary) (public, Lab 7 subtree)

## Deliverables by lab

**→ [SUBMISSION.md](SUBMISSION.md) tells you exactly what file to upload for each lab.**

| Lab | Deliverable (submit the PDF) | Folder |
|---|---|---|
| **1** | Vision Document — problem statement, problem–need–feature table, stakeholders | [Lab1_Vision/](Lab1_Vision/) |
| **2** | SRS — use-case diagram, 6 use cases, UC3 + UC4 described in full, NFRs, GitHub URL | [Lab2_SRS/](Lab2_SRS/) |
| **3** | High-level system architecture (layered) with constraints and subsystems | [Lab3_Architecture/](Lab3_Architecture/) |
| **4** | Sequence diagrams for UC3 and UC4, with boundary/control/entity classes | [Lab4_SequenceDiagrams/](Lab4_SequenceDiagrams/) |
| **5** | Collaboration + VOPC diagrams for UC3 and UC4 | [Lab5_Collaboration_VOPC/](Lab5_Collaboration_VOPC/) |
| **6** | Java setup evidence and the coding exercises (submit the repo URL) | [Lab6_Java_Setup_and_Coding/](Lab6_Java_Setup_and_Coding/) |
| **7** | eLibrary and eRegistrar Spring Boot apps (submit the zip + repo URL) | [Lab7_SpringBoot/](Lab7_SpringBoot/) |
| **later** | 10-minute requirements/design presentation | [presentation/](presentation/) |

Each Labs 1–5 folder holds the Markdown source, the PlantUML diagram sources,
the rendered PNGs, and the submission-ready PDF.

## Reading order

1. [Vision Document](Lab1_Vision/eRegistrar_Vision_Document.md) — the problem and the eight features
2. [SRS](Lab2_SRS/eRegistrar_SRS.md) — actors, six use cases, UC3 and UC4 in full, NFRs
3. [Architecture](Lab3_Architecture/eRegistrar_Architecture.md) — constraints, chosen layered architecture, subsystems
4. [Sequence diagrams](Lab4_SequenceDiagrams/README.md) — UC3 and UC4 realised over time
5. [Collaboration & VOPC](Lab5_Collaboration_VOPC/README.md) — the same two use cases by object links, plus participating classes
6. [Lab 6 coding exercises](Lab6_Java_Setup_and_Coding/README.md)
7. [Lab 7 Spring Boot apps](Lab7_SpringBoot/README.md)

## Traceability

The artifacts form one chain: each of the eight features in the Vision document
maps to use cases in the SRS (§6), the two significant use cases (UC3 Generate
Term Schedule, UC4 Register for Course) each have a sequence diagram (Lab 4) and
a collaboration + VOPC pair (Lab 5), and the analysis classes map onto the
subsystems of the Lab 3 architecture — control classes become business-layer
services, entity classes become the domain model, boundary classes become Spring
MVC controllers and their templates, as realised in the Lab 7 eRegistrar app.

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

### Regenerating diagrams and PDFs

```bash
java -jar tools/plantuml.jar -tpng Lab*/diagrams/*.puml && python3 tools/md_to_pdf.py
```

## Source material

The original assignment texts (`lab1.txt` … `lab7.txt`), the provided samples
(`Sample Vision Document.docx`, the use-case description PDF, the sample
architecture image) and the lecture slides in `lessons/` are kept alongside for
reference.
