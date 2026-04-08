#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
exec python3 "$ROOT/../../tools/mod-release/build_release_matrix.py" --project-dir "$ROOT" --clean "$@"
