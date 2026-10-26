# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

# Extract datagen boolean
$GradleTemplate     = "gradle-template.properties"
$DownloadDatagenLine = Get-Content $GradleTemplate | Where-Object { $_ -match '^\s*download_datagen\s*=' } | Select-Object -First 1
if (-not $DownloadDatagenLine) {
    throw "Could not find 'download_datagen' in $GradleTemplate."
}
$DownloadDatagen = ($DownloadDatagenLine -replace '^\s*download_datagen\s*=\s*', '').Trim()

# Return unless download_datagen is true
if ($DownloadDatagen -ine "true") {
    Write-Host "Datagen is not enabled ... skipping" -ForegroundColor Gray
    Write-Host
    exit
}

Write-Host

# Run Datagen
./gradlew --console=plain --warn --non-interactive runDatagen

Write-Host
