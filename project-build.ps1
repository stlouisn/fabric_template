# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Run the Gradle build
Start-Process -FilePath "./gradlew" -ArgumentList "clean", "build", "--no-daemon" -NoNewWindow -Wait

Write-Host ""