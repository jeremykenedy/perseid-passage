#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/build/tests"
mkdir -p "$OUT"
javac -source 8 -target 8 -d "$OUT" \
  "$ROOT/src/com/jeremykenedy/perseidpassage/ShowerOptions.java" \
  "$ROOT/src/com/jeremykenedy/perseidpassage/SettingsValues.java" \
  "$ROOT/tests/ShowerOptionsTest.java"
java -ea -cp "$OUT" com.jeremykenedy.perseidpassage.ShowerOptionsTest
python3 -m unittest -v tests.test_installer
python3 "$ROOT/scripts/check-privacy.py"
python3 "$ROOT/scripts/check-docs.py"
bash "$ROOT/scripts/check-style.sh"
