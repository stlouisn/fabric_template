# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host

# Build JAR
./gradlew --console=colored clean build

Write-Host
