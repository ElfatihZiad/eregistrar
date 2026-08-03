# System Requirements Specification — **eRegistrar**

**Course:** CS425 — Software Engineering
**Author:** Ziad El Fatih — 618971
**Document version:** 1.0
**Date:** August 3, 2026
**Project repository:** `https://github.com/<your-github-username>/eregistrar`
*(create the repository and replace `<your-github-username>`; the local Git
repository containing all of this work is at the root of this folder — see
[GIT_SETUP.md](GIT_SETUP.md))*

---

## 1. Introduction

### 1.1 Purpose

This document specifies the requirements for **eRegistrar**, a web-based course
scheduling and registration system for a mid-sized university department. It
captures the requirements as a use-case model: the actors, the use-case
diagram, and detailed descriptions of the major use cases, together with the
supplementary (non-functional) requirements that apply across them.

It is derived from the problem statement and feature list in the
[Vision Document](../Lab1_Vision/eRegistrar_Vision_Document.md) (Lab 1), and it
is the input to the architectural analysis of Lab 3 and the use-case analysis of
Labs 4 and 5.

### 1.2 Scope

eRegistrar covers four areas of functionality:

1. **Faculty profile management** — specializations, teachable courses, block availability.
2. **Course and program catalog management** — courses, prerequisites, programs, entries, blocks.
3. **Schedule generation and publication** — building the term schedule, assigning faculty, publishing it.
4. **Student registration** — registering for and dropping courses, with rule enforcement.

Out of scope: admissions, grading and transcripts, tuition and payments, and
classroom/room allocation. These remain with the university's existing Student
Information System, with which eRegistrar integrates.

### 1.3 Definitions and Acronyms

| Term | Meaning |
|---|---|
| **Block** | An 8-week teaching period. Courses are taught within a single block. |
| **Entry** | An intake cohort of students; the department runs four entries per year. |
| **Section** | A specific offering of a course in a specific block, with an assigned faculty member and a capacity. |
| **Schedule** | The set of sections offered in a term, in draft or published state. |
| **Registration window** | The bounded period during which students may register or drop. |
| **Prerequisite** | A course that must be completed before another course may be taken. |
| **SIS** | Student Information System — external system of record for student and program data. |
| **IdP** | Identity Provider — external single sign-on service. |
| **VOPC** | View of Participating Classes. |

### 1.4 References

- Vision Document — `../Lab1_Vision/eRegistrar_Vision_Document.md`
- Architecture Document (Lab 3) — `../Lab3_Architecture/eRegistrar_Architecture.md`
- Sequence diagrams (Lab 4) — `../Lab4_SequenceDiagrams/`
- Collaboration and VOPC diagrams (Lab 5) — `../Lab5_Collaboration_VOPC/`

## 2. Actors

| Actor | Type | Description |
|---|---|---|
| **Student** | Primary, human | Registers for and drops courses within the registration window; views the published schedule and their own registrations. |
| **Faculty** | Primary, human | Maintains their own profile (specializations, teachable courses, block availability); views assigned sections and class rosters. |
| **Registrar (Administrator)** | Primary, human | Maintains the course catalog and programs, generates and publishes schedules, assigns faculty, registers students by exception, runs reports. |
| **Identity Provider (IdP)** | Secondary, external system | Authenticates users on behalf of eRegistrar via single sign-on. |
| **Student Information System (SIS)** | Secondary, external system | Supplies student and program records; receives published schedules and confirmed registrations. |

## 3. Use-Case Model

![eRegistrar use case diagram](diagrams/usecase_eregistrar.png)

*Source: [diagrams/usecase_eregistrar.puml](diagrams/usecase_eregistrar.puml)*

### 3.1 Use-Case Summary

| ID | Use Case | Primary Actor | Priority | Realises feature |
|---|---|---|---|---|
| UC1 | Manage Faculty Profile | Faculty | High | F1 |
| UC2 | Manage Course Catalog | Registrar | High | F2 |
| UC3 | Generate Term Schedule | Registrar | High | F3 |
| UC4 | Assign Faculty to Section | Registrar | High | F4 |
| UC5 | Publish Schedule | Registrar | Medium | F8 |
| UC6 | Register for Course | Student | High | F5, F6 |
| UC7 | Drop Course | Student | High | F5, F6 |
| UC8 | View Schedule | Student, Faculty | Medium | F7 |
| UC9 | View Class Roster | Faculty | Medium | F7 |
| UC10 | Login / Authenticate | All | High | F9 |
| UC11 | Generate Enrolment Report | Registrar | Low | F7 |

The four use cases described in full below (UC1, UC3, UC4, UC6) are the
architecturally significant ones; they are the use cases carried forward into
the sequence, collaboration and VOPC diagrams of Labs 4 and 5.

---

## 4. Use-Case Descriptions

### UC1 — Manage Faculty Profile

| | |
|---|---|
| **Use Case Number** | 1 |
| **Name** | Manage Faculty Profile |
| **Brief description** | Allows a faculty member to create and maintain their own profile — specializations, the courses they are qualified to teach, and the blocks in which they are available. The registrar may perform the same operations on behalf of any faculty member. |
| **Actors** | Faculty (primary), Registrar (alternate) |
| **Preconditions** | The user is authenticated (UC10) and holds the Faculty or Admin role. The course catalog (UC2) contains the courses to be selected. |

**Flows of Events — 1. Basic Flows**

*1.1.0 Create Faculty Profile*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The faculty member selects "Create my profile". | The system displays the faculty profile form with fields for first name, last name, email, specializations, teachable courses, and block availability. The course and specialization lists are populated from the catalog. |
| 2 | The faculty member completes the form and submits it. | The system validates that all mandatory fields are present, that at least one specialization and one teachable course are selected, and that no other profile exists with the same email address. It saves the profile and displays a confirmation. |

*1.1.1 View Faculty Profile*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The faculty member opens "My profile". (The registrar instead selects a faculty member from the faculty list.) | The system retrieves and displays the profile: names, email, specializations, teachable courses and block availability. |

*1.1.2 Update Faculty Profile*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The faculty member selects "Edit profile". | The system displays the profile form pre-filled with the current values. |
| 2 | The faculty member changes one or more values and submits. | The system validates the changes, saves them, and displays a confirmation. If the change removes availability or a teachable course that an already-published section depends on, the system warns the user and notifies the registrar. |

*1.1.3 Delete Faculty Profile*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The registrar selects a faculty profile and requests deletion. | The system checks whether the faculty member is assigned to any section in a current or future term. If so, deletion is refused with an explanatory message. Otherwise the profile is deactivated and retained for audit purposes. |

**2. Alternate Flows**

- *A1 — Duplicate email.* At step 2 of 1.1.0, if a profile with the same email address already exists, the system rejects the submission and reports the duplicate. The user may correct the address or abandon the operation.
- *A2 — Validation failure.* If mandatory fields are missing or malformed, the system redisplays the form with the offending fields marked and no data saved.
- *A3 — Insufficient privilege.* If a faculty member attempts to open another faculty member's profile for editing, the system refuses the operation and logs the attempt.

| | |
|---|---|
| **Postconditions** | The faculty profile is persisted, and is available to schedule generation (UC3) and faculty assignment (UC4). |
| **Business Rules** | BR1: A faculty profile is uniquely identified by email address. BR2: A faculty member may only edit their own profile; the registrar may edit any. BR3: A profile must declare at least one specialization and one teachable course before it can be used in scheduling. BR4: A profile assigned to a current or future section cannot be deleted. |

---

### UC3 — Generate Term Schedule

| | |
|---|---|
| **Use Case Number** | 3 |
| **Name** | Generate Term Schedule |
| **Brief description** | The registrar generates a draft schedule of sections for a term. The system creates sections for the courses required by each entry in that term, and assigns qualified, available faculty to them, reporting any course it could not staff. |
| **Actors** | Registrar (primary) |
| **Preconditions** | The user is authenticated with the Admin role. The term, its blocks and entries are defined. The course catalog and program requirements are populated (UC2). At least one faculty profile exists (UC1). |

**Flows of Events — 1. Basic Flows**

*1.1.0 Generate Draft Schedule*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The registrar selects "Generate schedule" and chooses a term. | The system displays the generation form: the term, its blocks, the entries active in that term, and the option to include or exclude electives. |
| 2 | The registrar confirms the parameters and requests generation. | The system determines the courses required by each active entry in each block, applying the rule that 400-level prerequisite courses are placed in earlier blocks and 500-level courses in later blocks. |
| 3 | — | For each required course the system creates a section with a default capacity and selects a faculty member who (a) lists the course as teachable, (b) is available in that block, and (c) is not already assigned to another section in that block. Assignment conflicts are detected as described in *Detect Assignment Conflict*. |
| 4 | — | The system saves the result as a **draft** schedule and displays it, together with a list of any courses left unstaffed and any faculty left over capacity. |
| 5 | The registrar reviews the draft, adjusting individual assignments where needed (UC4). | The system applies each adjustment and re-checks for conflicts. |

**2. Alternate Flows**

- *A1 — No qualified faculty available.* At step 3, if no faculty member satisfies all three conditions for a course, the system creates the section unassigned, adds the course to the unstaffed list, and continues. Generation is not aborted.
- *A2 — Schedule already exists for the term.* At step 2, if a draft or published schedule already exists, the system requires the registrar to choose explicitly between discarding the existing draft and cancelling the operation. A published schedule is never overwritten by generation.
- *A3 — Incomplete catalog.* If a program's requirements reference a course that does not exist in the catalog, the system reports the inconsistency and does not generate until it is corrected.

| | |
|---|---|
| **Postconditions** | A draft schedule for the term exists, containing sections with faculty assigned where possible. Nothing is visible to students until it is published (UC5). |
| **Business Rules** | BR5: A faculty member may not be assigned to two sections in the same block. BR6: A faculty member may only be assigned to a course listed as teachable in their profile. BR7: 400-level courses that are prerequisites are scheduled in an entry's earlier blocks; the dependent 500-level courses in later blocks. BR8: Generation never modifies a published schedule. |

---

### UC4 — Assign Faculty to Section

| | |
|---|---|
| **Use Case Number** | 4 |
| **Name** | Assign Faculty to Section |
| **Brief description** | The registrar assigns, changes, or removes the faculty member teaching a particular section, with the system rejecting assignments that would breach qualification or availability rules. |
| **Actors** | Registrar (primary) |
| **Preconditions** | The user is authenticated with the Admin role. The section exists in a draft or published schedule. |

**Flows of Events — 1. Basic Flows**

*1.1.0 Assign Faculty*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The registrar selects a section from the schedule and chooses "Assign faculty". | The system displays the section details and a list of candidate faculty, showing for each whether they are qualified for the course and available in the block. |
| 2 | The registrar selects a faculty member and confirms. | The system re-checks qualification, block availability, and existing assignments. If all checks pass, the assignment is saved and confirmed. |
| 3 | — | If the section belongs to a published schedule, the system notifies the previously assigned faculty member (if any) and the newly assigned one. |

**2. Alternate Flows**

- *A1 — Unqualified faculty.* At step 2, if the course is not in the faculty member's teachable list, the system refuses the assignment and states why. The registrar may override with a recorded justification, which is written to the audit trail.
- *A2 — Double booking.* If the faculty member already teaches another section in the same block, the system refuses the assignment and identifies the conflicting section.
- *A3 — Unassign.* The registrar may remove an assignment; the section returns to the unstaffed list and, if published, the registrar is warned that students are already registered.

| | |
|---|---|
| **Postconditions** | The section's faculty assignment is updated and consistent with the qualification and availability rules, or the operation was refused and nothing changed. |
| **Business Rules** | BR5, BR6 (as above). BR9: An override of the qualification rule is permitted to the registrar only, and must be recorded with a justification. BR10: Changing the faculty of a published section triggers notification of affected faculty and students. |

---

### UC6 — Register for Course

| | |
|---|---|
| **Use Case Number** | 6 |
| **Name** | Register for Course |
| **Brief description** | A student registers for a section of a course in a published schedule. The system accepts the registration only if the prerequisite, capacity, time-conflict and course-load rules are all satisfied. |
| **Actors** | Student (primary), Registrar (on behalf of a student) |
| **Preconditions** | The user is authenticated with the Student role (UC10). A schedule is published for the term, and the registration window for that term is open. The student is active in an entry. |

**Flows of Events — 1. Basic Flows**

*1.1.0 Register for a Section*

| Step | User Actions | System Actions |
|---|---|---|
| 1 | The student opens "Register" for the term. | The system displays the published schedule for the student's entry: courses, blocks, assigned faculty, capacity and remaining seats, marking those the student is not yet eligible for. |
| 2 | The student selects a section and confirms registration. | The system validates the registration: the student has completed the course's prerequisites; the section has a free seat; the student has no other registration in the same block that conflicts; and the student's course load for the block is within the limit for their category. |
| 3 | — | If every rule passes, the system creates the registration, decrements the available seats atomically, and displays a confirmation showing the updated personal schedule. |
| 4 | — | The system records the transaction in the audit trail. |

**2. Alternate Flows**

- *A1 — Prerequisite not met.* At step 2 the system refuses the registration, names the missing prerequisite, and leaves the schedule unchanged.
- *A2 — Section full.* If no seat remains — including the case where the last seat is taken by a concurrent request between display and confirmation — the system refuses the registration and reports that the section is full. Capacity is never exceeded.
- *A3 — Time conflict.* If the student is already registered for another section in the same block at an overlapping time, the system refuses and identifies the conflicting section.
- *A4 — Course load exceeded.* If the registration would exceed the number of courses permitted for the student's category in that block, the system refuses and states the limit.
- *A5 — Already registered.* If the student is already registered for the same course in the term, the system refuses the duplicate.
- *A6 — Registration window closed.* If the window is not open, the system refuses the operation. The registrar may still register the student by exception, which is recorded.

| | |
|---|---|
| **Postconditions** | Either the registration exists, the section's remaining seats have decreased by one, and the student's schedule reflects it; or nothing has changed and the student has been told why. |
| **Business Rules** | BR11: All prerequisites of a course must be completed before registering for it. BR12: Registrations for a section may never exceed its capacity. BR13: A student may not hold two registrations at overlapping times in the same block. BR14: Course load per block is limited by student category (most students 4 elective blocks, resident students up to 9, OPT students 5). BR15: Registration is permitted only while the term's registration window is open, except for a recorded registrar exception. |

---

## 5. Supplementary (Non-Functional) Requirements

| ID | Category | Requirement |
|---|---|---|
| NFR1 | Performance | Schedule and registration pages respond within 2 seconds under normal load. |
| NFR2 | Scalability | 150 concurrent users are supported during a registration window without failed registrations. |
| NFR3 | Concurrency | Section capacity is enforced correctly under concurrent registration; the last seat is granted to exactly one student. |
| NFR4 | Availability | 99% availability during published registration windows. |
| NFR5 | Security | Every operation is authorized by role; a student may access only their own registrations; all traffic uses TLS. |
| NFR6 | Authentication | Authentication is delegated to the campus IdP; the application stores no production passwords. |
| NFR7 | Usability | Registrar staff can generate and publish a schedule after one walkthrough; student pages are usable at 375 px width. |
| NFR8 | Accessibility | WCAG 2.1 level AA for student- and faculty-facing pages. |
| NFR9 | Auditability | Schedule publications, faculty assignment overrides and registration transactions are retained for at least one academic year. |
| NFR10 | Compliance | Student data is handled in accordance with FERPA. |
| NFR11 | Portability | Runs on any platform with a Java 11+ runtime and a supported relational database. |
| NFR12 | Maintainability | The domain model is independent of the web and persistence frameworks; scheduling and registration rules are covered by automated tests. |

## 6. Traceability — Features to Use Cases

| Vision feature | Use cases |
|---|---|
| F1 Faculty Profile Management | UC1 |
| F2 Course & Program Catalog | UC2 |
| F3 Schedule Generation | UC3 |
| F4 Faculty Assignment & Conflict Detection | UC3, UC4 |
| F5 Online Student Registration | UC6, UC7 |
| F6 Registration Rule Enforcement | UC6, UC7 |
| F7 Schedule & Enrolment Views | UC8, UC9, UC11 |
| F8 Schedule Change Propagation | UC5 |
| F9 Authentication & Role-Based Access | UC10 |
| F10 Audit Trail | UC4, UC5, UC6 |
