# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Build JAR
./gradlew --Dorg.gradle.console=colored clean build
