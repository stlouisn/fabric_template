# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host

# Generate sources
./gradlew --console=colored genSources

Write-Host
