# Vision Document: eRegistrar

**Course:** CS425, Software Engineering  
**Student:** Ziad El Fatih, 618971  

---

## 1. Introduction

Course registration in the Computer Science department is still run on
spreadsheets and email. The registrar builds each term's schedule by hand,
faculty send in their teaching preferences by email, and students register on
paper forms that staff later key into the system.

That worked fine when the department was small. It now runs **four entries a
year** with 100 to 130 students per entry, and offers 8 or 9 electives in a
busy block. Faculty each have one or two specializations, a set of courses
they can teach, and preferences about which blocks they're available in. Some
500-level courses have 400-level prerequisites, so the 400-level offerings
need to land in a student's earlier blocks. Keeping all of this straight in a
spreadsheet is slow, and prerequisite or capacity problems usually turn up
only after students discover them, once registration is already open.

**eRegistrar** is a web application that builds the term schedule with faculty
assigned to each section, and lets students register online with the
prerequisite and capacity rules enforced automatically.

## 2. Positioning

### 2.1 Problem Statement

| | |
|---|---|
| **The problem of** | building the term schedule, assigning qualified faculty, and registering students for classes |
| **Affects** | the registrar, faculty, and students |
| **The impact of which is** | scheduling takes weeks of manual work, prerequisite and capacity violations are found late, and every schedule change forces a manual re-check |
| **A successful solution would be** | one system holding courses, faculty profiles and the schedule in a single database, applying the rules automatically, with web access for administrators, faculty and students |

### 2.2 Product Position Statement

| | |
|---|---|
| **For** | the registrar, faculty and students of the Computer Science department |
| **Who** | need a conflict-free term schedule and online registration against it |
| **The eRegistrar** | is a web-based course scheduling and registration system |
| **That** | generates the schedule from faculty qualifications and availability, and enforces prerequisites and capacity at the moment of registration |
| **Unlike** | spreadsheets plus email plus manual data entry |
| **Our product** | keeps one authoritative schedule, so a change made once is reflected everywhere and invalid registrations are rejected immediately |

## 3. Stakeholders

| Stakeholder | Role in the system |
|---|---|
| **Registrar (Administrator)** | Maintains courses and programs, generates and publishes the schedule, assigns faculty. |
| **Faculty** | Maintains their own profile: specializations, teachable courses, block availability. Views their assigned sections. |
| **Student** | Views the published schedule, registers for and drops courses. |
| **Department Chair** | Approves the published schedule. Project sponsor. |
| **IT Administrator** | Deploys and operates the system, manages accounts. |

**User environment.** Two to three registrar staff, about 25 faculty, and up
to 500 students on campus. All access is through a browser on desktop or
phone, with no software installed on user machines. Registration load is
heaviest in the first hour after the registration window opens.

## 4. Product Overview

### 4.1 Needs and Features: Problem / Need / Feature Table

| # | Problem | Need | Priority | Feature |
|---|---|---|---|---|
| 1 | Faculty preferences arrive by email and are applied inconsistently | Faculty must own their qualifications and availability | High | **F1: Faculty Profile Management** |
| 2 | Course and prerequisite data live in several spreadsheets that disagree | One authoritative catalog | High | **F2: Course & Program Catalog** |
| 3 | Building a term schedule takes weeks of manual work | Generate the schedule automatically from the rules | High | **F3: Schedule Generation** |
| 4 | Manual assignment double-books faculty or assigns courses they can't teach | Detect assignment conflicts | High | **F4: Faculty Assignment & Conflict Detection** |
| 5 | Students register on paper forms that staff re-key | Students register themselves with an immediate answer | High | **F5: Online Student Registration** |
| 6 | Prerequisite and capacity violations are found after registration closes | Invalid registrations rejected as they are attempted | High | **F6: Registration Rule Enforcement** |
| 7 | Nobody can see live enrolment | Role-specific views of the schedule and enrolment | Medium | **F7: Schedule & Enrolment Views** |
| 8 | Anyone with the spreadsheet can change anything | Access must match the user's role | High | **F8: Authentication & Role-Based Access** |

### 4.2 Assumptions and Dependencies

1. The academic calendar of blocks and entries is fixed by the university.
2. Course and program definitions are maintained inside eRegistrar by the registrar.
3. Every user has a university account; authentication is delegated to the campus identity provider.
4. The deployment target is a Java/Spring Boot environment with a relational database.

### 4.3 Alternatives

- **Status quo (spreadsheets and email).** Free and familiar, but it has no
  rule enforcement, no concurrency control, and won't scale to four entries a
  year.
- **Commercial SIS registration modules.** Comprehensive, but priced and
  scoped for a whole institution, and they don't model this department's
  block/entry structure without heavy customization.
- **Build in-house.** This is what we chose. The scheduling rules are
  specific to this department and are the hard part of the problem, so a
  small focused application ends up cheaper than customizing a large product.

## 5. Other Product Requirements

| Category | Requirement |
|---|---|
| Platform | Java 11+, Spring Boot, relational database, browser client |
| Performance | Pages respond within 2 seconds; 150 concurrent users during registration |
| Concurrency | Section capacity holds under simultaneous registration |
| Security | Role-based authorization on every operation, TLS everywhere, no passwords stored by the application |
| Usability | Student pages usable on a phone; staff productive after one walkthrough |
| Compliance | Student records handled in accordance with FERPA |
