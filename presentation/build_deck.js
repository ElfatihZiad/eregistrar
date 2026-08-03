const pptxgen = require("pptxgenjs");
const path = require("path");

const ROOT = "/Users/screwby/Projects/CS425";
const OUT = path.join(ROOT, "presentation", "eRegistrar_Requirements_Design.pptx");

// --- palette: teal, matching the eRegistrar application itself -------------
const TEAL = "028090";
const DEEP = "0B3C40";
const SEA = "00A896";
const MINT = "02C39A";
const INK = "1F2933";
const MUTED = "5B6B7C";
const LIGHT = "F4F8F8";
const WHITE = "FFFFFF";

const HEAD = "Cambria";
const BODY = "Calibri";

const pres = new pptxgen();
pres.layout = "LAYOUT_WIDE"; // 13.3 x 7.5
pres.author = "Ziad El Fatih";
pres.title = "eRegistrar — Requirements and Design";

const W = 13.3, H = 7.5;

function darkSlide() {
  const s = pres.addSlide();
  s.background = { color: DEEP };
  return s;
}

function lightSlide(title, kicker) {
  const s = pres.addSlide();
  s.background = { color: WHITE };
  s.addText(title, {
    x: 0.6, y: 0.42, w: W - 3.2, h: 0.75,
    fontFace: HEAD, fontSize: 34, bold: true, color: INK, margin: 0,
  });
  if (kicker) {
    s.addText(kicker, {
      x: W - 3.9, y: 0.58, w: 3.3, h: 0.4,
      fontFace: BODY, fontSize: 12, color: MUTED, align: "right", margin: 0,
    });
  }
  return s;
}

// Icon-ish numbered circle used as the recurring motif
function circleNum(slide, n, x, y, size = 0.42, fill = TEAL) {
  slide.addShape(pres.ShapeType.ellipse, {
    x, y, w: size, h: size, fill: { color: fill },
  });
  slide.addText(String(n), {
    x, y, w: size, h: size, align: "center", valign: "middle",
    fontFace: BODY, fontSize: 14, bold: true, color: WHITE, margin: 0,
  });
}

function card(slide, x, y, w, h, fill = LIGHT) {
  slide.addShape(pres.ShapeType.roundRect, {
    x, y, w, h, rectRadius: 0.08,
    fill: { color: fill },
    line: { color: "E1EAEA", width: 1 },
  });
}

// ---------------------------------------------------------------- slide 1
{
  const s = darkSlide();
  s.addShape(pres.ShapeType.ellipse, {
    x: 9.4, y: -1.6, w: 6.2, h: 6.2, fill: { color: TEAL, transparency: 72 },
  });
  s.addShape(pres.ShapeType.ellipse, {
    x: 11.2, y: 3.6, w: 3.4, h: 3.4, fill: { color: SEA, transparency: 82 },
  });
  s.addText("eRegistrar", {
    x: 0.9, y: 2.2, w: 8.6, h: 1.2,
    fontFace: HEAD, fontSize: 54, bold: true, color: WHITE, margin: 0,
  });
  s.addText("Course scheduling and registration for a university department", {
    x: 0.9, y: 3.35, w: 8.4, h: 0.6,
    fontFace: BODY, fontSize: 19, color: MINT, margin: 0,
  });
  s.addText("System Requirements and Design", {
    x: 0.9, y: 4.25, w: 8.4, h: 0.4,
    fontFace: BODY, fontSize: 15, color: "BFD8D6", margin: 0, italic: true,
  });
  s.addText("Ziad El Fatih  ·  618971  ·  CS425 Software Engineering  ·  August 2026", {
    x: 0.9, y: 5.9, w: 9.5, h: 0.4,
    fontFace: BODY, fontSize: 13, color: "9FBDBB", margin: 0,
  });
  s.addNotes("eRegistrar: course scheduling and registration. Covers the vision, the use-case model, the architecture, and the analysis of the significant use cases, plus what is already running.");
}

// ---------------------------------------------------------------- slide 2
{
  const s = lightSlide("The problem", "Lab 1 — Vision");
  const stats = [
    ["4", "entries per year", "up from 3, each needing its own schedule"],
    ["100–130", "students per entry", "registering by paper form, re-keyed by staff"],
    ["8–9", "electives per block", "plus FPP and MPP courses to place"],
    ["~3 weeks", "to build one schedule", "by hand, in a spreadsheet"],
  ];
  let x = 0.6;
  stats.forEach(([big, label, sub]) => {
    card(s, x, 1.55, 2.95, 2.0);
    s.addText(big, {
      x: x + 0.25, y: 1.72, w: 2.5, h: 0.75,
      fontFace: HEAD, fontSize: 34, bold: true, color: TEAL, margin: 0,
    });
    s.addText(label, {
      x: x + 0.25, y: 2.5, w: 2.5, h: 0.3,
      fontFace: BODY, fontSize: 13, bold: true, color: INK, margin: 0,
    });
    s.addText(sub, {
      x: x + 0.25, y: 2.82, w: 2.5, h: 0.6,
      fontFace: BODY, fontSize: 11, color: MUTED, margin: 0,
    });
    x += 3.1;
  });

  s.addText("What goes wrong today", {
    x: 0.6, y: 3.95, w: 6.0, h: 0.35,
    fontFace: BODY, fontSize: 16, bold: true, color: INK, margin: 0,
  });
  const pains = [
    "Faculty preferences arrive by email and are applied inconsistently",
    "Prerequisite and capacity violations surface after registration closes",
    "One schedule change forces a manual re-check of every registration",
    "Nobody can see live enrolment until the spreadsheet is reconciled",
  ];
  s.addText(pains.map((t, i) => ({
    text: t, options: { bullet: true, breakLine: i < pains.length - 1 },
  })), {
    x: 0.75, y: 4.4, w: 6.4, h: 1.9,
    fontFace: BODY, fontSize: 14, color: INK, paraSpaceAfter: 8, margin: 0,
  });

  card(s, 7.5, 3.95, 5.2, 2.45, DEEP);
  s.addText("A successful solution", {
    x: 7.8, y: 4.15, w: 4.6, h: 0.35,
    fontFace: BODY, fontSize: 14, bold: true, color: MINT, margin: 0,
  });
  s.addText("One system holding courses, faculty profiles, programs and the schedule in a single database — applying the scheduling and prerequisite rules automatically, with role-appropriate web access for administrators, faculty and students.", {
    x: 7.8, y: 4.6, w: 4.6, h: 1.6,
    fontFace: BODY, fontSize: 13, color: WHITE, margin: 0, lineSpacingMultiple: 1.15,
  });
  s.addNotes("The department outgrew its spreadsheet process. Four entries a year, up to 130 students each, and a three-week manual scheduling cycle. The rules exist but are enforced by people, late.");
}

// ---------------------------------------------------------------- slide 3
{
  const s = lightSlide("Features driving the design", "Lab 1 — Problem / Need / Feature");
  const feats = [
    ["F1", "Faculty Profile Management", "Faculty own their specializations, teachable courses and block availability"],
    ["F3", "Schedule Generation", "Draft schedule built from program requirements, qualification and availability"],
    ["F4", "Assignment Conflict Detection", "No double-booking, no unqualified assignment — checked, not trusted"],
    ["F5", "Online Student Registration", "Students register themselves and get an immediate answer"],
    ["F6", "Registration Rule Enforcement", "Prerequisites, capacity, time conflicts and course load enforced at submit"],
    ["F8", "Role-Based Access", "Single sign-on with Admin, Faculty and Student roles"],
  ];
  let y = 1.5;
  feats.forEach(([id, name, desc], i) => {
    const col = i % 2, row = Math.floor(i / 2);
    const cx = 0.6 + col * 6.35;
    const cy = 1.5 + row * 1.72;
    card(s, cx, cy, 6.05, 1.5);
    s.addShape(pres.ShapeType.ellipse, {
      x: cx + 0.25, y: cy + 0.3, w: 0.62, h: 0.62, fill: { color: TEAL },
    });
    s.addText(id, {
      x: cx + 0.25, y: cy + 0.3, w: 0.62, h: 0.62, align: "center", valign: "middle",
      fontFace: BODY, fontSize: 13, bold: true, color: WHITE, margin: 0,
    });
    s.addText(name, {
      x: cx + 1.05, y: cy + 0.24, w: 4.75, h: 0.35,
      fontFace: BODY, fontSize: 15, bold: true, color: INK, margin: 0,
    });
    s.addText(desc, {
      x: cx + 1.05, y: cy + 0.62, w: 4.75, h: 0.7,
      fontFace: BODY, fontSize: 12, color: MUTED, margin: 0, lineSpacingMultiple: 1.1,
    });
  });
  s.addText("Eight features in total; these six are the ones the architecture has to satisfy.", {
    x: 0.6, y: 6.75, w: 9.0, h: 0.3,
    fontFace: BODY, fontSize: 12, color: MUTED, italic: true, margin: 0,
  });
  s.addNotes("The problem-need-feature table produced ten features. F3 to F6 are the heart of it: generate the schedule, and enforce the registration rules at the point of attempt.");
}

// ---------------------------------------------------------------- slide 4
{
  const s = lightSlide("Use-case model", "Lab 2 — SRS");
  s.addImage({
    path: path.join(ROOT, "Lab2_SRS/diagrams/usecase_eregistrar.png"),
    x: 0.6, y: 1.35, w: 5.75, h: 5.2,
  });
  const rows = [
    ["Student", "Register / drop courses, view schedule"],
    ["Faculty", "Maintain profile, view sections and rosters"],
    ["Registrar", "Catalog, schedule generation, assignment, reports"],
    ["Identity Provider", "Single sign-on (external)"],
    ["SIS", "Student records, published schedule (external)"],
  ];
  s.addText("Actors", {
    x: 6.85, y: 1.35, w: 5.9, h: 0.35,
    fontFace: BODY, fontSize: 16, bold: true, color: INK, margin: 0,
  });
  let y = 1.8;
  rows.forEach(([a, b]) => {
    card(s, 6.85, y, 5.85, 0.72);
    s.addText(a, {
      x: 7.05, y: y + 0.08, w: 2.1, h: 0.28,
      fontFace: BODY, fontSize: 13, bold: true, color: TEAL, margin: 0,
    });
    s.addText(b, {
      x: 7.05, y: y + 0.36, w: 5.45, h: 0.3,
      fontFace: BODY, fontSize: 11.5, color: MUTED, margin: 0,
    });
    y += 0.82;
  });
  s.addText("6 use cases identified. UC3 and UC4 are described in full and carried into analysis.", {
    x: 6.85, y: 6.05, w: 5.85, h: 0.6,
    fontFace: BODY, fontSize: 12, color: INK, italic: true, margin: 0,
  });
  s.addNotes("Three human actors and two external systems. Eleven use cases; the four architecturally significant ones get full descriptions with flows and business rules.");
}

// ---------------------------------------------------------------- slide 5
{
  const s = lightSlide("UC4 — Register for Course", "Lab 2 — Use-case description");
  s.addText("Basic flow", {
    x: 0.6, y: 1.35, w: 5.9, h: 0.35,
    fontFace: BODY, fontSize: 16, bold: true, color: INK, margin: 0,
  });
  const steps = [
    "Student opens registration for the term",
    "System shows the published schedule with remaining seats",
    "Student selects a section and confirms",
    "System validates every rule before accepting",
    "Registration is created and a seat consumed — in one transaction",
  ];
  let y = 1.85;
  steps.forEach((t, i) => {
    circleNum(s, i + 1, 0.62, y, 0.4);
    s.addText(t, {
      x: 1.2, y: y - 0.02, w: 5.3, h: 0.45,
      fontFace: BODY, fontSize: 13.5, color: INK, margin: 0, valign: "middle",
    });
    y += 0.72;
  });

  card(s, 7.1, 1.35, 5.6, 4.05, LIGHT);
  s.addText("Rules enforced (BR6–BR9)", {
    x: 7.4, y: 1.55, w: 5.0, h: 0.35,
    fontFace: BODY, fontSize: 15, bold: true, color: TEAL, margin: 0,
  });
  const rules = [
    "All prerequisites completed",
    "Section capacity never exceeded",
    "No overlapping registration in the block",
    "Course load within the student's category limit",
    "No duplicate registration for the course",
    ];
  s.addText(rules.map((t, i) => ({
    text: t, options: { bullet: true, breakLine: i < rules.length - 1 },
  })), {
    x: 7.55, y: 2.0, w: 4.9, h: 3.2,
    fontFace: BODY, fontSize: 13, color: INK, paraSpaceAfter: 7, margin: 0,
  });

  card(s, 0.6, 5.6, 12.1, 1.15, DEEP);
  s.addText("Every violation returns a typed failure and rolls the transaction back: no registration, no seat consumed, and the student is told exactly which rule stopped them.", {
    x: 0.95, y: 5.82, w: 11.4, h: 0.75,
    fontFace: BODY, fontSize: 13.5, color: WHITE, margin: 0, valign: "middle",
  });
  s.addNotes("UC6 is the highest-risk use case: it is the one with contention, and the one where a wrong answer is visible to students.");
}

// ---------------------------------------------------------------- slide 6
{
  const s = lightSlide("Architectural decision", "Lab 3 — Architecture");
  const cands = [
    ["Monolithic single layer", "Rejected", "Volatile scheduling rules scattered through the page code", "C43D3D"],
    ["Microservices", "Rejected", "Capacity check becomes a distributed transaction; too costly for the team", "C43D3D"],
    ["Layered web architecture", "Selected", "Isolates the rules, keeps registration in one local transaction, one server", TEAL],
  ];
  let y = 1.75;
  cands.forEach(([name, verdict, why, colour]) => {
    card(s, 0.6, y, 12.1, 1.12, name.startsWith("Layered") ? "E6F5F3" : LIGHT);
    s.addText(name, {
      x: 0.9, y: y + 0.16, w: 3.7, h: 0.35,
      fontFace: BODY, fontSize: 15, bold: true, color: INK, margin: 0,
    });
    s.addText(verdict, {
      x: 0.9, y: y + 0.56, w: 3.7, h: 0.32,
      fontFace: BODY, fontSize: 12.5, bold: true, color: colour, margin: 0,
    });
    s.addText(why, {
      x: 4.9, y: y + 0.3, w: 7.5, h: 0.6,
      fontFace: BODY, fontSize: 13, color: MUTED, margin: 0, valign: "middle",
    });
    y += 1.24;
  });
  s.addText("Driven by: capacity must hold under concurrent registration · 150 concurrent users at peak · rules are the volatile part · one server, one database", {
    x: 0.6, y: 6.5, w: 12.1, h: 0.5,
    fontFace: BODY, fontSize: 12, color: MUTED, italic: true, margin: 0,
  });
  s.addNotes("Microservices were considered and deferred rather than dismissed: the subsystem boundaries are drawn so services could be extracted later.");
}

// ---------------------------------------------------------------- slide 7
{
  const s = lightSlide("Layered architecture", "Lab 3 — Selected solution");
  s.addImage({
    path: path.join(ROOT, "Lab3_Architecture/diagrams/architecture_layers.png"),
    x: 0.6, y: 1.3, w: 7.35, h: 5.55,
  });
  const notes = [
    ["Presentation", "Spring MVC + Thymeleaf, security at the boundary"],
    ["Business", "Five subsystems; acyclic dependencies; transaction boundary"],
    ["Domain model", "Framework-independent — the invariants live here"],
    ["Data access", "Spring Data JPA; optimistic locking on Section"],
    ["External", "IdP, SIS and SMTP behind adapters, stubbed in development"],
  ];
  let y = 1.5;
  notes.forEach(([h, d]) => {
    s.addText(h, {
      x: 8.4, y: y, w: 4.3, h: 0.3,
      fontFace: BODY, fontSize: 14, bold: true, color: TEAL, margin: 0,
    });
    s.addText(d, {
      x: 8.4, y: y + 0.3, w: 4.4, h: 0.62,
      fontFace: BODY, fontSize: 12, color: MUTED, margin: 0, lineSpacingMultiple: 1.1,
    });
    y += 1.03;
  });
  s.addNotes("Dependencies point downwards only. The registration transaction stays inside one process, which is what makes the capacity guarantee affordable.");
}

// ---------------------------------------------------------------- slide 8
{
  const s = lightSlide("Use-case realisation — UC4", "Lab 4 — Sequence diagram");
  s.addImage({
    path: path.join(ROOT, "Lab4_SequenceDiagrams/diagrams/sd_uc4_register_for_course.png"),
    x: 0.75, y: 1.25, w: 6.05, h: 5.6,
  });
  card(s, 7.3, 1.4, 5.4, 2.35, LIGHT);
  s.addText("Analysis classes", {
    x: 7.6, y: 1.58, w: 4.8, h: 0.3,
    fontFace: BODY, fontSize: 14, bold: true, color: TEAL, margin: 0,
  });
  const cls = [
    "«boundary» RegistrationPage",
    "«control» RegistrationController",
    "«control» RegistrationValidator",
    "«entity» Student · Section · Course · Registration",
  ];
  s.addText(cls.map((t, i) => ({
    text: t, options: { bullet: true, breakLine: i < cls.length - 1 },
  })), {
    x: 7.75, y: 1.98, w: 4.75, h: 1.6,
    fontFace: BODY, fontSize: 12.5, color: INK, paraSpaceAfter: 6, margin: 0,
  });

  card(s, 7.3, 3.95, 5.4, 2.9, DEEP);
  s.addText("The critical detail", {
    x: 7.6, y: 4.15, w: 4.8, h: 0.3,
    fontFace: BODY, fontSize: 14, bold: true, color: MINT, margin: 0,
  });
  s.addText("The seat check and the seat decrement sit in the same transaction, with optimistic locking on Section.\n\nA student who loses the race for the last seat gets \"section full\" — never an over-filled section.", {
    x: 7.6, y: 4.55, w: 4.85, h: 2.1,
    fontFace: BODY, fontSize: 12.5, color: WHITE, margin: 0, lineSpacingMultiple: 1.15,
  });
  s.addNotes("Messages flow actor to boundary to control to entity, never back up. The validator owns the rules so they cannot be bypassed by another entry point.");
}

// ---------------------------------------------------------------- slide 9
{
  const s = lightSlide("Participating classes — VOPC", "Lab 5 — Collaboration & VOPC");
  s.addImage({
    path: path.join(ROOT, "Lab5_Collaboration_VOPC/diagrams/vopc_uc4_register_for_course.png"),
    x: 0.7, y: 1.25, w: 4.0, h: 5.6,
  });
  s.addImage({
    path: path.join(ROOT, "Lab5_Collaboration_VOPC/diagrams/collab_uc4_register_for_course.png"),
    x: 5.15, y: 1.6, w: 7.55, h: 3.9,
  });
  s.addText("VOPC — structure", {
    x: 0.7, y: 6.9, w: 4.0, h: 0.3,
    fontFace: BODY, fontSize: 12, bold: true, color: MUTED, align: "center", margin: 0,
  });
  s.addText("Collaboration — the same interaction by object links, numbered 1 … 3.5 … 7", {
    x: 5.15, y: 5.6, w: 7.55, h: 0.35,
    fontFace: BODY, fontSize: 12, color: MUTED, align: "center", margin: 0,
  });
  card(s, 5.15, 6.05, 7.55, 1.0, LIGHT);
  s.addText("Control classes become business-layer services · entity classes become the domain model and JPA entities · boundary classes become Spring MVC controllers and templates.", {
    x: 5.4, y: 6.2, w: 7.1, h: 0.72,
    fontFace: BODY, fontSize: 12.5, color: INK, margin: 0, valign: "middle",
  });
  s.addNotes("The VOPC is the bridge to class design: these classes map directly onto the subsystems in the architecture.");
}

// ---------------------------------------------------------------- slide 10
{
  const s = lightSlide("Already running", "Lab 7 — Implementation slice");
  s.addImage({
    path: path.join(ROOT, "Lab7_SpringBoot/eregistrar/screenshots/homepage.png"),
    x: 0.6, y: 1.35, w: 6.9, h: 5.39,
  });
  s.addText("Spring Boot 2.7 · Java 11 · Thymeleaf", {
    x: 7.85, y: 1.4, w: 4.9, h: 0.32,
    fontFace: BODY, fontSize: 13, bold: true, color: TEAL, margin: 0,
  });
  const done = [
    "Published-schedule view — the read half of UC6",
    "Section capacity shown; full sections marked",
    "MVC controller, Thymeleaf views, static assets",
    "Automated tests green on every build",
    "DevTools + LiveReload development workflow",
  ];
  s.addText(done.map((t, i) => ({
    text: t, options: { bullet: true, breakLine: i < done.length - 1 },
  })), {
    x: 8.0, y: 1.9, w: 4.75, h: 2.3,
    fontFace: BODY, fontSize: 13, color: INK, paraSpaceAfter: 7, margin: 0,
  });

  s.addText("Next iteration", {
    x: 7.85, y: 4.35, w: 4.9, h: 0.32,
    fontFace: BODY, fontSize: 13, bold: true, color: TEAL, margin: 0,
  });
  const next = [
    "JPA persistence and the registration transaction",
    "Schedule generation with conflict detection",
    "Single sign-on and role-based authorization",
    "Load test the registration path against 150 users",
  ];
  s.addText(next.map((t, i) => ({
    text: t, options: { bullet: true, breakLine: i < next.length - 1 },
  })), {
    x: 8.0, y: 4.8, w: 4.75, h: 2.0,
    fontFace: BODY, fontSize: 13, color: MUTED, paraSpaceAfter: 7, margin: 0,
  });
  s.addNotes("The homepage is the read-only half of UC6 rendered from the analysis model, so the design and the code already line up.");
}

// ---------------------------------------------------------------- slide 11
{
  const s = darkSlide();
  s.addShape(pres.ShapeType.ellipse, {
    x: -1.8, y: 4.4, w: 5.6, h: 5.6, fill: { color: TEAL, transparency: 78 },
  });
  s.addText("From vision to running code", {
    x: 0.9, y: 1.0, w: 11.5, h: 0.8,
    fontFace: HEAD, fontSize: 36, bold: true, color: WHITE, margin: 0,
  });
  const chain = [
    ["Vision", "10 features from the problem–need–feature table"],
    ["SRS", "6 use cases, 2 described in full, 6 NFRs"],
    ["Architecture", "Layered, 5 subsystems, acyclic dependencies"],
    ["Analysis", "Sequence, collaboration and VOPC per use case"],
    ["Code", "Two Spring Boot apps, tests green"],
  ];
  let x = 0.9;
  chain.forEach(([h, d], i) => {
    s.addShape(pres.ShapeType.roundRect, {
      x, y: 2.35, w: 2.15, h: 2.15, rectRadius: 0.1,
      fill: { color: "12494E" }, line: { color: "1D6B6E", width: 1 },
    });
    circleNum(s, i + 1, x + 0.85, 2.6, 0.45, SEA);
    s.addText(h, {
      x: x + 0.15, y: 3.2, w: 1.85, h: 0.32,
      fontFace: BODY, fontSize: 15, bold: true, color: MINT, align: "center", margin: 0,
    });
    s.addText(d, {
      x: x + 0.15, y: 3.55, w: 1.85, h: 0.85,
      fontFace: BODY, fontSize: 10.5, color: "CFE3E2", align: "center", margin: 0,
      lineSpacingMultiple: 1.05,
    });
    if (i < chain.length - 1) {
      s.addText("→", {
        x: x + 2.15, y: 3.15, w: 0.35, h: 0.4,
        fontFace: BODY, fontSize: 18, color: SEA, align: "center", margin: 0,
      });
    }
    x += 2.5;
  });
  s.addText("Every feature traces to a use case, every significant use case to a realisation, every analysis class to a subsystem.", {
    x: 0.9, y: 5.1, w: 11.5, h: 0.4,
    fontFace: BODY, fontSize: 14, color: "BFD8D6", italic: true, margin: 0,
  });
  s.addText("Ziad El Fatih  ·  618971  ·  Questions?", {
    x: 0.9, y: 6.3, w: 11.5, h: 0.4,
    fontFace: BODY, fontSize: 14, color: WHITE, margin: 0,
  });
  s.addNotes("Close on traceability: the chain from vision to code is unbroken, which is what makes the next iteration cheap to plan.");
}

pres.writeFile({ fileName: OUT }).then(() => console.log("wrote", OUT));
