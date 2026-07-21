# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Generate sources
./gradlew --console=colored genSources
