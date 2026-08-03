# Lab 2, Part 2 — Git and GitHub Setup

## Local repository

All CS425 project artifacts — documents, PlantUML sources, rendered diagrams and
source code for Labs 6 and 7 — are version-controlled in a single Git repository
rooted at this project folder (`CS425/`).

```bash
git -C ~/Projects/CS425 log --oneline
```

`tools/plantuml.jar` is excluded by `.gitignore` because of its size; the
download command is in [../Lab1_Vision/TOOLS_SETUP.md](../Lab1_Vision/TOOLS_SETUP.md).

## Publishing to GitHub

The repository has not been pushed to GitHub — that needs your account, so run
these three commands yourself after creating an empty repository named
`eregistrar` (no README, no .gitignore) at <https://github.com/new>:

```bash
git -C ~/Projects/CS425 remote add origin https://github.com/<your-github-username>/eregistrar.git
```

```bash
git -C ~/Projects/CS425 branch -M main
```

```bash
git -C ~/Projects/CS425 push -u origin main
```

Then replace `<your-github-username>` in the repository URL at the top of
[eRegistrar_SRS.md](eRegistrar_SRS.md) with your actual username, and commit that
change.

If you have the GitHub CLI installed and authenticated (`gh auth status`), the
first two steps collapse into one command:

```bash
gh repo create eregistrar --private --source ~/Projects/CS425 --remote origin --push
```

Lab 7 asks for a separate repository named `elibrary`. The eLibrary application
lives inside this same repository at `Lab7_SpringBoot/elibrary/`, so publish it
as its own repository by pushing that subtree — no nested Git repository, and
the history stays in one place:

```bash
git -C ~/Projects/CS425 remote add elibrary https://github.com/<your-github-username>/elibrary.git
```

```bash
git -C ~/Projects/CS425 subtree push --prefix=Lab7_SpringBoot/elibrary elibrary main
```

## Working practice for the rest of the course

Commit after each lab deliverable, and push regularly:

```bash
git -C ~/Projects/CS425 add -A && git -C ~/Projects/CS425 commit -m "Lab N: <what changed>" && git -C ~/Projects/CS425 push
```
