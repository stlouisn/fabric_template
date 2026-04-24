# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Run Spotless first
Start-Process -FilePath "./gradlew" -ArgumentList "spotlessApply" -NoNewWindow -Wait
