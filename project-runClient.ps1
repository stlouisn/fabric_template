# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Run Client
./gradlew --console=colored runClient
