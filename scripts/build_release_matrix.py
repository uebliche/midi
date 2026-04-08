#!/usr/bin/env python3
from __future__ import annotations

import pathlib
import subprocess
import sys


def main() -> int:
    root = pathlib.Path(__file__).resolve().parents[1]
    script = root.parents[1] / "tools" / "mod-release" / "build_release_matrix.py"
    return subprocess.call([sys.executable, str(script), "--project-dir", str(root), *sys.argv[1:]])


if __name__ == "__main__":
    raise SystemExit(main())
