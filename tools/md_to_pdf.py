"""Convert the Lab 1-5 Markdown documents to submission-ready PDFs.

Markdown -> styled HTML -> PDF via headless Chrome. Images referenced relatively
in the Markdown are inlined as data URIs, so the intermediate HTML is fully
self-contained and Chrome renders every diagram.

    python3 tools/md_to_pdf.py                 # convert all documents
    python3 tools/md_to_pdf.py Lab2_SRS/eRegistrar_SRS.md
"""

import base64
import os
import re
import subprocess
import sys

import markdown

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CHROME = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"

DOCUMENTS = [
    "Lab1_Vision/eRegistrar_Vision_Document.md",
    "Lab2_SRS/eRegistrar_SRS.md",
    "Lab3_Architecture/eRegistrar_Architecture.md",
    "Lab4_SequenceDiagrams/README.md",
    "Lab5_Collaboration_VOPC/README.md",
]

CSS = """
@page { size: A4; margin: 18mm 16mm; }
body { font-family: -apple-system, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
       font-size: 10.5pt; line-height: 1.5; color: #1f2933; }
h1 { font-size: 20pt; color: #0b3c40; border-bottom: 2px solid #028090;
     padding-bottom: 6px; margin: 0 0 14px; }
h2 { font-size: 14pt; color: #0b3c40; margin: 22px 0 8px; }
h3 { font-size: 11.5pt; color: #028090; margin: 16px 0 6px; }
p, li { margin: 6px 0; }
table { border-collapse: collapse; width: 100%; margin: 10px 0 14px; font-size: 9.5pt; }
th, td { border: 1px solid #cfd9de; padding: 5px 8px; text-align: left; vertical-align: top; }
th { background: #e6f3f2; color: #0b3c40; }
tr:nth-child(even) td { background: #fafcfc; }
code { background: #eef2f5; padding: 1px 4px; border-radius: 3px; font-size: 9pt; }
pre { background: #f5f7f9; border: 1px solid #dde4e9; border-radius: 4px;
      padding: 8px 10px; overflow-x: auto; font-size: 9pt; }
img { max-width: 100%; display: block; margin: 12px auto; page-break-inside: avoid; }
hr { border: none; border-top: 1px solid #dde4e9; margin: 18px 0; }
blockquote { margin: 8px 0; padding-left: 12px; border-left: 3px solid #028090; color: #5b6b7c; }
h2, h3 { page-break-after: avoid; }
table, pre { page-break-inside: avoid; }
"""


def inline_images(html, base_dir):
    """Replace <img src="relative.png"> with a base64 data URI."""
    def repl(match):
        src = match.group(1)
        if src.startswith(("http:", "https:", "data:")):
            return match.group(0)
        path = os.path.normpath(os.path.join(base_dir, src))
        if not os.path.exists(path):
            print("  ! missing image:", src)
            return match.group(0)
        with open(path, "rb") as f:
            data = base64.b64encode(f.read()).decode("ascii")
        return 'src="data:image/png;base64,{}"'.format(data)

    return re.sub(r'src="([^"]+)"', repl, html)


def convert(rel_path):
    md_path = os.path.join(ROOT, rel_path)
    base_dir = os.path.dirname(md_path)
    pdf_path = os.path.splitext(md_path)[0] + ".pdf"

    # A README.md is the lab's document; name the PDF after the lab folder.
    if os.path.basename(md_path).lower() == "readme.md":
        pdf_path = os.path.join(base_dir, os.path.basename(base_dir) + ".pdf")

    with open(md_path, encoding="utf-8") as f:
        body = markdown.markdown(
            f.read(), extensions=["tables", "fenced_code", "sane_lists", "attr_list"]
        )

    html = "<!doctype html><html><head><meta charset='utf-8'><style>{}</style>" \
           "</head><body>{}</body></html>".format(CSS, inline_images(body, base_dir))

    html_path = os.path.join(base_dir, ".tmp_" + os.path.basename(md_path) + ".html")
    with open(html_path, "w", encoding="utf-8") as f:
        f.write(html)

    subprocess.run(
        [CHROME, "--headless=new", "--disable-gpu", "--no-pdf-header-footer",
         "--virtual-time-budget=15000", "--print-to-pdf=" + pdf_path,
         "file://" + html_path],
        check=True, capture_output=True,
    )
    os.remove(html_path)
    print("  ->", os.path.relpath(pdf_path, ROOT))


if __name__ == "__main__":
    targets = sys.argv[1:] or DOCUMENTS
    for target in targets:
        print("Converting", target)
        convert(target)
