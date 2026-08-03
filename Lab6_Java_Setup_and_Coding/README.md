# Lab 6 — Java Development Environment and Coding Practice Exercises

**Author:** Ziad El Fatih — 618971
**Date:** August 3, 2026

## 1. Environment setup

| Requirement | Status |
|---|---|
| Java SE JDK | **Installed** — OpenJDK 11.0.30 (Homebrew). Evidence: [screenshots/02_jdk_and_tool_versions.png](screenshots/02_jdk_and_tool_versions.png), raw output in [screenshots/tool_versions.txt](screenshots/tool_versions.txt) |
| JDK 8 and JDK 13 (the lab asks for three side-by-side versions) | **Not installed** — these need an installer/administrator step, so they are listed as an action in [../Lab1_Vision/TOOLS_SETUP.md](../Lab1_Vision/TOOLS_SETUP.md). Install with `brew install openjdk@8 openjdk@17`, then re-run `python3 make_screenshot.py` to refresh the evidence image. |
| IDE | Any of Eclipse for Enterprise Java / IntelliJ IDEA / VS Code. The code here is plain `javac`-compatible, with no IDE-specific project files, so it imports into any of them. |
| Git | Installed — 2.39.5, repository at the CS425 project root. |

The screenshots in [screenshots/](screenshots/) are rendered from the real
captured terminal output by [make_screenshot.py](make_screenshot.py), so the
image and the text always show the same run.

## 2. Student Records Management application

Source: [StudentRecordsMgmtApp/src/](StudentRecordsMgmtApp/src/)

```
edu.mum.cs.cs425.demos.studentrecordsmgmtapp
├── MyStudentRecordsMgmtApp.java     — executable class (main)
└── model
    └── Student.java                 — the Student class
```

### `Student` (package `...studentrecordsmgmtapp.model`)

- Fields: `studentId`, `name`, `dateOfAdmission`.
- Three constructors: default, `(studentId, name)`, and the full
  `(studentId, name, dateOfAdmission)`.
- Getters and setters for all three fields, plus a `toString()` that formats a
  row of the report.

### `MyStudentRecordsMgmtApp`

| Method | Behaviour |
|---|---|
| `main` | Builds the array of the five sample students and calls each of the methods below. |
| `printListOfStudents(Student[])` | Prints every student in **ascending order of name**. Sorts a copy, so the caller's array is left untouched. |
| `getListOfPlatinumAlumniStudents(Student[])` | Returns the students admitted at least 30 years ago. `main` prints them in **descending order of admission date**. |
| `printHelloWorld(int[])` | Prints `Hello` for multiples of 5, `World` for multiples of 7, `HelloWorld` for multiples of both. |
| `findSecondBiggest(int[])` | Returns the second biggest integer in a single pass, **without sorting**. Duplicates of the maximum are skipped, so `{5, 5, 4}` returns 4. Boxed `Integer` accumulators are used so that `Integer.MIN_VALUE` is a legal input value rather than a "not seen yet" sentinel. |

### Building and running

```bash
cd Lab6_Java_Setup_and_Coding/StudentRecordsMgmtApp && javac -d out $(find src -name '*.java') && java -cp out edu.mum.cs.cs425.demos.studentrecordsmgmtapp.MyStudentRecordsMgmtApp
```

### Results

![Program output](screenshots/01_student_records_app_output.png)

Verified against the exercise's own examples:

| Call | Expected | Actual |
|---|---|---|
| `findSecondBiggest([1,2,3,4,5])` | 4 | 4 |
| `findSecondBiggest([19,9,11,0,12])` | 12 | 12 |
| `printHelloWorld([… 35 …])` | `HelloWorld` for 35 and 70 | `35 -> HelloWorld`, `70 -> HelloWorld` |
| Platinum alumni | Dave (1951), Erica (1974), Anna (1990), Bob (1990) — all admitted 30+ years before 2026; Carlos (2009) excluded | Same four, listed newest-admission-first |

Raw output: [screenshots/console_output.txt](screenshots/console_output.txt).

## 3. Git submission

All of this is committed in the repository at the CS425 project root. Pushing to
GitHub needs your account — the commands are in
[../Lab2_SRS/GIT_SETUP.md](../Lab2_SRS/GIT_SETUP.md). Submit the repository URL
in Sakai for this lab.
