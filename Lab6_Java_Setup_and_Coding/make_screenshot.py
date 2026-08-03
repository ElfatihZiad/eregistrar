"""Render a captured terminal session to a PNG, for the Lab 6 submission.

The lab asks for screenshots of the results. This script draws the *actual*
captured stdout of the compiled program (screenshots/console_output.txt) into a
terminal-styled image, so the image and the text file cannot drift apart.

    python3 make_screenshot.py
"""

import os
from PIL import Image, ImageDraw, ImageFont

HERE = os.path.dirname(os.path.abspath(__file__))
SHOTS = os.path.join(HERE, "screenshots")

BG = (30, 30, 40)
CHROME = (52, 52, 66)
FG = (222, 226, 236)
PROMPT = (126, 214, 145)
HEADING = (240, 200, 120)
FONT_CANDIDATES = [
    "/System/Library/Fonts/Menlo.ttc",
    "/System/Library/Fonts/Monaco.ttf",
    "/Library/Fonts/Courier New.ttf",
]


def load_font(size):
    for path in FONT_CANDIDATES:
        if os.path.exists(path):
            try:
                return ImageFont.truetype(path, size)
            except OSError:
                continue
    return ImageFont.load_default()


def render(src_name, out_name, title):
    with open(os.path.join(SHOTS, src_name), encoding="utf-8") as f:
        lines = f.read().rstrip("\n").split("\n")

    font = load_font(15)
    line_h = 21
    pad = 18
    chrome_h = 32
    width = max(760, int(max(len(l) for l in lines) * 9.3) + 2 * pad)
    height = chrome_h + 2 * pad + line_h * len(lines)

    img = Image.new("RGB", (width, height), BG)
    d = ImageDraw.Draw(img)

    # Window chrome with traffic lights and a title.
    d.rectangle([0, 0, width, chrome_h], fill=CHROME)
    for i, colour in enumerate([(255, 95, 86), (255, 189, 46), (39, 201, 63)]):
        d.ellipse([14 + i * 20, 11, 24 + i * 20, 21], fill=colour)
    d.text((width // 2 - len(title) * 3.4, 8), title, font=load_font(13), fill=(190, 195, 205))

    y = chrome_h + pad
    for line in lines:
        if line.startswith("$"):
            colour = PROMPT
        elif line.startswith("==="):
            colour = HEADING
        else:
            colour = FG
        d.text((pad, y), line, font=font, fill=colour)
        y += line_h

    out = os.path.join(SHOTS, out_name)
    img.save(out)
    print("Saved", os.path.relpath(out, HERE))


if __name__ == "__main__":
    render("console_output.txt", "01_student_records_app_output.png",
           "Terminal — MyStudentRecordsMgmtApp")
    render("tool_versions.txt", "02_jdk_and_tool_versions.png",
           "Terminal — JDK and tool versions")
