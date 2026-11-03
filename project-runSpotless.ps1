# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host

# Run Spotless
./gradlew --console=colored --quiet spotlessApply

Write-Host
