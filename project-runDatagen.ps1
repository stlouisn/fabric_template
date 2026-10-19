# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host

# Run Datagen
./gradlew --console=plain --warn --non-interactive runDatagen

Write-Host
