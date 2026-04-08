$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $MyInvocation.MyCommand.Path
if (Get-Command py -ErrorAction SilentlyContinue) {
    & py -3 "$Root/../../tools/mod-release/build_release_matrix.py" --project-dir "$Root" --clean @args
} else {
    & python "$Root/../../tools/mod-release/build_release_matrix.py" --project-dir "$Root" --clean @args
}
exit $LASTEXITCODE
