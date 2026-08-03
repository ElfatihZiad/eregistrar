# Requirements / Design Presentation

**Deck:** [eRegistrar_Requirements_Design.pptx](eRegistrar_Requirements_Design.pptx)
**Author:** Ziad El Fatih, 618971

Lab 2 asks for a short (10-minute) System Requirements/Design presentation,
extended as further analysis and design artifacts are produced. This deck
covers the artifacts from Labs 1 through 5 plus the Lab 7 implementation
slice.

## Structure (11 slides, about 10 minutes)

| # | Slide | Artifact shown | Time |
|---|---|---|---|
| 1 | Title | N/A | 0:20 |
| 2 | The problem | Vision §1, §2.1 | 1:15 |
| 3 | Features driving the design | Problem–Need–Feature table | 1:00 |
| 4 | Use-case model | Use-case diagram + actors | 1:15 |
| 5 | UC4: Register for Course | Use-case description, business rules | 1:15 |
| 6 | Architectural decision | Candidates considered and why | 1:10 |
| 7 | Layered architecture | Architecture diagram | 1:10 |
| 8 | Use-case realisation | UC4 sequence diagram | 1:15 |
| 9 | Participating classes | VOPC + collaboration diagrams | 1:00 |
| 10 | Already running | eRegistrar app screenshot, roadmap | 0:50 |
| 11 | From vision to running code | Traceability chain, close | 0:30 |

Every slide carries speaker notes. Open the notes pane in PowerPoint or
Keynote to see them.

## Regenerating

The deck is generated from a script rather than hand-built, so re-running it
picks up any updated diagram automatically:

```bash
node build_deck.js
```

`build_deck.js` embeds the PNGs directly from `Lab2_SRS/diagrams/`,
`Lab3_Architecture/diagrams/`, `Lab4_SequenceDiagrams/diagrams/`,
`Lab5_Collaboration_VOPC/diagrams/` and `Lab7_SpringBoot/eregistrar/screenshots/`.
It needs `pptxgenjs` (`npm install pptxgenjs`).

## Before presenting

The deck was validated structurally (OOXML schema, relationships, content
types, all passing) and checked geometrically for off-slide shapes and text
overflow. It was **not** visually rendered here, because LibreOffice isn't
installed on this machine, so open it once in PowerPoint or Keynote to
confirm the fonts and image placement look right on your projector.
