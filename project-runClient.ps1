# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host

# Run Client
./gradlew --console=plain --warn --non-interactive runClient

Write-Host
