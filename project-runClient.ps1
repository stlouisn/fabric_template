# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Run the Gradle build
Start-Process -FilePath "./gradlew" -ArgumentList "-Dorg.gradle.console=colored", "runClient" -NoNewWindow -Wait
