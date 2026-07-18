# Forcefully terminate all java.exe processes
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host ""

# Run the Gradle build
$process = Start-Process -FilePath "./gradlew" -ArgumentList "clean", "build", "--no-daemon" -NoNewWindow -Wait -PassThru
$exitCode = $process.ExitCode

Write-Host ""

# Check if the build was successful (exit code 0)
if ($exitCode -eq 0) {

    # Ensure the destination folder exists
    if (-not (Test-Path ".\files")) {
        New-Item -ItemType Directory -Path ".\files" | Out-Null
    }

    # Copy matching JAR files to the 'files' directory
    Copy-Item -Path ".\build\libs\_custom_*.jar" -Destination ".\files" -Force
    Write-Host "JAR files copied to 'files' directory."
}
else {
    Write-Host "Gradle build failed with exit code $exitCode. Skipping file copy."
}

Write-Host ""