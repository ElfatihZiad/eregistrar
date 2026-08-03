# Lab 2, Part 2 — Git and GitHub Setup

## Repositories

| Repo | Visibility | URL | Contents |
|---|---|---|---|
| `eregistrar` | Private | <https://github.com/ElfatihZiad/eregistrar> | The whole project — Labs 1–7 |
| `elibrary` | Public | <https://github.com/ElfatihZiad/elibrary> | Lab 7's eLibrary app only (its own repo, as the lab asks) |

Both are pushed and current as of the last commit.

## How they're kept in sync

The local repository at the CS425 project root is the single source of truth.
`elibrary` is a **subtree** of it — `Lab7_SpringBoot/elibrary/` pushed on its
own, not a separate checkout — so there's one history to maintain, not two.

```bash
git -C ~/Projects/CS425 log --oneline
```

`tools/plantuml.jar` and `tools/apache-maven-3.9.9/` are excluded by
`.gitignore` because of their size; see
[../Lab1_Vision/TOOLS_SETUP.md](../Lab1_Vision/TOOLS_SETUP.md) for the download
commands. Instructor-provided reference material (`lessons/`, the sample
documents) is also excluded — it's not your work, so it isn't pushed, but it
stays on disk for you to consult.

## Working practice for the rest of the course

After each lab, commit and push both remotes:

```bash
git -C ~/Projects/CS425 add -A && git -C ~/Projects/CS425 commit -m "Lab N: <what changed>" && git -C ~/Projects/CS425 push origin main
```

If a change touches `Lab7_SpringBoot/elibrary/`, also push the subtree so the
public repo stays current:

```bash
git -C ~/Projects/CS425 subtree push --prefix=Lab7_SpringBoot/elibrary elibrary main
```
