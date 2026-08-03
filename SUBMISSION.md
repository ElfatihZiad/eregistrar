# What to submit for each lab

**Student:** Ziad El Fatih, 618971
**Project:** eRegistrar

Both repos are created and pushed. Nothing left to set up before submitting.

| Repo | URL |
|---|---|
| `eregistrar` (private, whole project) | <https://github.com/ElfatihZiad/eregistrar> |
| `elibrary` (public, Lab 7 subtree) | <https://github.com/ElfatihZiad/elibrary> |

---

## Lab 1: Vision Document

**Upload:** `Lab1_Vision/eRegistrar_Vision_Document.pdf` (4 pages)

Contains your name and ID, the problem statement, the problem–need–feature
table, stakeholders, product overview and other requirements, the sections the
sample Vision document uses. Tools setup is a "begin doing" item with no
graded artifact; `Lab1_Vision/TOOLS_SETUP.md` records it if asked.

## Lab 2: SRS with the Use-Case Model

**Upload:** `Lab2_SRS/eRegistrar_SRS.pdf` (5 pages)

Contains the use-case diagram, the 6 use cases, full descriptions of UC3 and
UC4 in the sample's format (flows of events, alternate flows, postconditions,
business rules), the non-functional requirements, and **the GitHub URL**. The
lab requires the repository URL inside the SRS document itself.

The presentation is prepared but uploaded later when the course asks for it:
`presentation/eRegistrar_Requirements_Design.pptx`.

## Lab 3: System Architecture

**Upload:** `Lab3_Architecture/eRegistrar_Architecture.pdf` (3 pages)

The required deliverable is the high-level architecture diagram. The PDF
wraps it with the constraints, the reasoning behind this architecture, and
the subsystem breakdown. If the instructor wants the diagram alone, submit
`Lab3_Architecture/diagrams/architecture_layers.png`.

## Lab 4: Sequence Diagrams

**Upload:** `Lab4_SequenceDiagrams/Lab4_SequenceDiagrams.pdf` (4 pages)

Two sequence diagrams (UC4 Register for Course and UC3 Generate Term
Schedule), with every lifeline marked «boundary», «control» or «entity»,
which the lab explicitly asks for. The PNGs are in
`Lab4_SequenceDiagrams/diagrams/` if you'd rather attach images.

## Lab 5: Collaboration and VOPC Diagrams

**Upload:** `Lab5_Collaboration_VOPC/Lab5_Collaboration_VOPC.pdf` (5 pages)

A collaboration diagram and a VOPC diagram for each of UC3 and UC4, four
diagrams in total, with numbered messages on the collaboration diagrams.

## Lab 6: Java Setup and Coding Exercises

**Upload:** the GitHub repository URL, as text, in the Sakai assignment:
`https://github.com/ElfatihZiad/eregistrar`

Everything the lab asks to see is already committed in the repo:

- `Lab6_Java_Setup_and_Coding/StudentRecordsMgmtApp/`: the `Student` class in
  `edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model`, and
  `MyStudentRecordsMgmtApp` with `printListOfStudents`,
  `getListOfPlatinumAlumniStudents`, `printHelloWorld`, `findSecondBiggest`.
- `Lab6_Java_Setup_and_Coding/screenshots/`: the result screenshots.

⚠️ The lab asks for evidence of **three JDK versions** (8, 11, 13). Only JDK
11 is installed. To cover that part, run `brew install openjdk@8 openjdk@17`,
then `python3 Lab6_Java_Setup_and_Coding/make_screenshot.py` to refresh the
evidence image, then commit.

## Lab 7: Spring Boot Applications

**Upload:** two things: the zip of the eLibrary project, and the repo URL as
text (`https://github.com/ElfatihZiad/elibrary`).

```bash
cd Lab7_SpringBoot && zip -r elibrary.zip elibrary -x '*/target/*'
```

Checklist the lab grades:

- ✅ Homepage banner reads "Ziad El Fatih's elibrary - a digital library for everyone"
- ✅ `screenshots/` folder inside the project, with the homepage screenshot
- ✅ DevTools + LiveReload configured
- ✅ Second app `eregistrar` built from scratch with its own content
- ✅ Repo named `elibrary` on GitHub, pushed

---

## One-command refresh

If you edit any document or diagram, regenerate everything before submitting:

```bash
java -jar tools/plantuml.jar -tpng Lab*/diagrams/*.puml && python3 tools/md_to_pdf.py
```
