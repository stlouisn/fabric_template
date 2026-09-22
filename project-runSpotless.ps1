# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

# Run Spotless
./gradlew --console=colored spotlessApply
