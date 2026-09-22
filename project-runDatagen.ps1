# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

# Run Client
./gradlew --console=plain --warn --non-interactive runDatagen
