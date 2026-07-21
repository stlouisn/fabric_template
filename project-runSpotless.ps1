# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Run Spotless
./gradlew --Dorg.gradle.console=colored spotlessApply
