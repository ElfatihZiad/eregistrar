# Vision Document for **eRegistrar**

**Course:** CS425 — Software Engineering
**Team members:** Ziad El Fatih — 618971
**Document version:** 1.0
**Date:** August 3, 2026

---

## 1. Introduction

Course registration at a mid-sized university is still handled by a patchwork of
tools. The registrar's office builds each term's schedule in a spreadsheet,
faculty send their teaching preferences by email, and students register by
filling in a paper or PDF form that a staff member later keys into the student
records system. When the university offered two intakes a year and a handful of
electives per term this was workable. It no longer is.

The department now runs **four entries per year**, with 100–130 students per
entry, and in a busy block it offers 8–9 electives alongside the required core
courses. Faculty each have one or two areas of specialization (Web Applications,
Data Science, Software Design, Networking, Operating Systems, Compilers,
Parallel Programming, and so on), a set of courses they are qualified and
willing to teach, and preferences about which blocks they are available in. Some
500-level courses have 400-level prerequisites, so the 400-level offerings have
to appear in a student's earlier blocks and the 500-level ones later. Different
student categories carry different requirements: most students take 4 elective
blocks on campus, resident students may take 9, and OPT students take 5.

Holding all of these rules in a spreadsheet is slow and error-prone. A schedule
change late in the process — a faculty member becoming unavailable, a course
being cancelled for low enrolment — has to be propagated by hand into every
affected student's plan, and conflicts are typically discovered by the students
themselves, after registration has opened.

**eRegistrar** is a web-based enterprise application that builds the term
schedule with faculty assigned to each section, enforces the prerequisite and
capacity rules automatically, and lets students register for classes online and
see the result immediately. It gives the registrar one authoritative schedule,
gives faculty a way to maintain their own profile and see their teaching load,
and gives students a self-service registration process that refuses invalid
registrations at the point they are attempted rather than weeks later.

## 2. Positioning

### 2.1 Problem Statement

| | |
|---|---|
| **The problem of** | building and maintaining the term class schedule, assigning qualified faculty to sections, and registering students for classes |
| **Affects** | the registrar and academic administrators, faculty members, and students |
| **The impact of which is** | scheduling takes weeks of manual spreadsheet work each term, faculty preferences and qualifications are applied inconsistently, prerequisite and capacity violations are discovered late, and every schedule change forces a manual re-check of registrations |
| **A successful solution would be** | a single system that holds courses, faculty profiles, student programs and the schedule in one database, applies the scheduling and prerequisite rules automatically, and provides role-appropriate web access for administrators, faculty, and students |

### 2.2 Product Position Statement

| | |
|---|---|
| **For** | the registrar's office, faculty, and students of a mid-sized university department running multiple entries per year |
| **Who** | need to build a conflict-free term schedule and register students against it without manual spreadsheet reconciliation |
| **The eRegistrar** | is a web-based course scheduling and registration system |
| **That** | generates term schedules from faculty qualifications, availability and program requirements, and enforces prerequisites, capacity and time-conflict rules at the moment of registration |
| **Unlike** | maintaining spreadsheets alongside email-based faculty preference collection and manual data entry into the student records system |
| **Our product** | keeps one authoritative model of courses, sections, faculty and registrations, so that a change made once is reflected everywhere immediately and invalid registrations are impossible rather than merely discouraged |

## 3. Stakeholder Descriptions

### 3.1 Stakeholder Summary

| Name | Represents | Role |
|---|---|---|
| Registrar / Academic Administrator | The registrar's office | Owns the schedule. Maintains courses, programs, blocks and terms; generates and publishes the schedule; assigns faculty; overrides registrations when a documented exception applies; runs enrolment reports. |
| Faculty Member | Teaching staff | Maintains their own profile — specializations, courses they can teach, block availability. Views their assigned sections and class rosters. |
| Student | Enrolled students | Views the published schedule, registers for and drops courses within the registration window, views their own registration history and remaining program requirements. |
| Department Chair | Academic leadership | Approves the published schedule, monitors faculty teaching load and course demand. Sponsor of the project. |
| IT / System Administrator | University IT | Deploys and operates the system, manages accounts and roles, performs backups, integrates with the university identity provider. |

### 3.2 User Environment

- **Who does the work today.** Two to three registrar staff build each term's
  schedule over roughly three weeks, working mainly in Excel and email. Faculty
  (about 25 active) submit preferences once per entry. Students (100–130 per
  entry, up to ~500 active on campus) interact with the process only at
  registration time.
- **Task cycle.** The scheduling cycle repeats every block (8 weeks), with a
  larger planning cycle each entry. The registration window is short — typically
  the last week of the preceding block — and load is heavily concentrated in the
  first hours after it opens.
- **Platforms in use.** Windows and macOS desktops in the offices; students use
  laptops and phones, so the student-facing screens must work on a small screen.
  Browsers are current versions of Chrome, Edge, Safari and Firefox.
- **Existing systems.** The university's student information system remains the
  system of record for admissions and grades, and identity is managed by the
  campus identity provider. eRegistrar has to fit alongside both rather than
  replace them, so account provisioning and a future export of registrations to
  the SIS are in scope as interfaces.
- **Constraints.** Staff are not technical users; the scheduling screens have to
  be usable without training beyond a short walkthrough. No client software may
  be required beyond a browser.

## 4. Product Overview

### 4.1 Product Perspective

eRegistrar is a self-contained web application that owns the scheduling and
registration domain, while interacting with two external systems:

- **University Identity Provider** — authentication and single sign-on; the
  system maps authenticated users onto its own roles (Admin, Faculty, Student).
- **Student Information System (SIS)** — the source of student and program
  records, and the eventual destination of confirmed registrations.

Internally the product is organised into four subsystems — Faculty Profile
Management, Course & Program Management, Schedule Generation, and Student
Registration — sharing a single relational database. The subsystem decomposition
and the deployment view are developed in the Lab 3 architecture document.

### 4.2 Assumptions and Dependencies

1. Course and program definitions change rarely and are maintained by registrar
   staff inside eRegistrar; the SIS is not authoritative for them.
2. Student identity and program enrolment originate in the SIS. Until that
   integration is built, administrators may create student records directly.
3. Every user has a university account with the campus identity provider; the
   system does not manage its own passwords in production.
4. Scheduling operates on a fixed academic calendar of blocks and entries
   defined by the university; the system does not invent calendar structures.
5. The deployment target is a standard Java EE / Spring Boot environment with a
   relational database, hosted on university infrastructure.
6. Registration volumes peak sharply, but the total data volume is small
   (thousands of registrations per term), so a single application server with a
   single database instance is sufficient.

### 4.3 Needs and Features — Problem/Need/Feature Table

| # | Problem | Need | Priority | Feature |
|---|---|---|---|---|
| 1 | Faculty preferences arrive by email and are transcribed by hand, so they are applied inconsistently | Faculty must own and maintain their own qualifications and availability | High | **F1 — Faculty Profile Management.** Faculty create and update a profile holding specializations, courses they can teach, and per-block availability. |
| 2 | Course, prerequisite and program data live in several spreadsheets and disagree with each other | One authoritative catalog of courses, prerequisites and program requirements | High | **F2 — Course & Program Catalog.** CRUD for courses, prerequisites, programs, entries and blocks, maintained by the registrar. |
| 3 | Building a term schedule takes weeks of manual spreadsheet work | Generate a candidate schedule automatically from the rules | High | **F3 — Schedule Generation.** Produces a term schedule of sections with faculty assigned, honouring qualification, availability, and course-level (400/500) placement rules. |
| 4 | Manual assignment double-books faculty or assigns courses they cannot teach | Detect and prevent assignment conflicts | High | **F4 — Faculty Assignment & Conflict Detection.** Assign or reassign faculty to sections, with automatic rejection of double-booking and unqualified assignments. |
| 5 | Students register on paper forms that staff re-key, days after the fact | Students must register themselves and get an immediate answer | High | **F5 — Online Student Registration.** Students browse the published schedule and register or drop within the registration window, with immediate confirmation. |
| 6 | Prerequisite and capacity violations are discovered after registration closes | Invalid registrations must be rejected at the point of attempt | High | **F6 — Registration Rule Enforcement.** Prerequisite, section capacity, time-conflict and per-block course-load rules are checked before a registration is accepted. |
| 7 | Nobody can see current enrolment until the spreadsheet is reconciled | Live visibility of enrolment and load | Medium | **F7 — Schedule & Enrolment Views.** Role-specific views: student's own schedule, faculty's assigned sections and rosters, administrator's enrolment and load reports. |
| 8 | A cancelled or moved section forces a manual re-check of every affected student | Changes must propagate and notify those affected | Medium | **F8 — Schedule Change Propagation.** Publishing a change flags affected registrations and notifies the affected students and faculty. |
| 9 | Anyone with the spreadsheet can change anything | Access must match the user's role | High | **F9 — Authentication & Role-Based Access.** Single sign-on against the campus identity provider, with Admin, Faculty and Student roles. |
| 10 | Disputes about who registered for what cannot be settled | An auditable record of schedule and registration changes | Low | **F10 — Audit Trail.** Timestamped, attributed history of schedule publications and registration transactions. |

### 4.4 Alternatives and Competition

- **Status quo (Excel + email + manual entry).** Free and familiar, and it is
  what staff know. It does not scale to four entries a year, provides no rule
  enforcement, no concurrency control during registration, and no audit trail.
- **Commercial SIS registration modules** (Banner, PeopleSoft Campus Solutions,
  Workday Student). Comprehensive and supported, but priced and scoped for a
  whole institution, and their scheduling engines do not model this department's
  block/entry structure and faculty-preference rules without heavy
  customization.
- **Open-source student information systems** (e.g. OpenSIS, Fedena). Lower
  cost, but oriented to K–12 or conventional semester structures; the block and
  entry model would again have to be forced in.
- **Build in-house — the chosen alternative.** The scheduling rules are specific
  to this department and are the hard part of the problem; owning them in a
  small, focused application is cheaper than customizing a large product, and
  the system can integrate with the existing SIS rather than replacing it.

## 5. Other Product Requirements

| Category | Requirement |
|---|---|
| **Platform** | Java 11+ with Spring Boot; deployed as an executable JAR on Linux. Relational database (MySQL or PostgreSQL in production, H2 in development). |
| **Standards** | HTTP/HTTPS, HTML5/CSS3, REST/JSON for service interfaces, JPA for persistence, SAML or OAuth2/OIDC for single sign-on. |
| **Performance** | Schedule and registration pages respond in under 2 seconds under normal load; the system supports 150 concurrent users during the registration window without a registration failing due to load. |
| **Concurrency** | Section capacity is enforced correctly under simultaneous registration attempts — no section may exceed its capacity even when the last seat is contested. |
| **Availability** | 99% during published registration windows; scheduled maintenance only outside them. |
| **Usability** | Registrar staff can build and publish a schedule after a single walkthrough; students can complete registration without instruction. Student-facing pages usable at 375 px width. |
| **Accessibility** | Conformance with WCAG 2.1 level AA for student- and faculty-facing pages. |
| **Security** | Role-based authorization on every operation; no student may view or alter another student's registrations; all traffic over TLS; passwords never stored by the application in production. |
| **Privacy / compliance** | Student records handled in accordance with FERPA; personal data visible only to the student and authorised staff. |
| **Auditability** | Registration and schedule-change transactions retained for at least one academic year. |
| **Maintainability** | Layered architecture with the domain model independent of the web and persistence frameworks; automated unit and integration tests for the scheduling and registration rules. |

## 6. Project Repository

Version control for all project artifacts (documents, diagrams, and source
code): see `../README.md` for the repository layout. The GitHub URL is recorded
in the SRS document (Lab 2).
