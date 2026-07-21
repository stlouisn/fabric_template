# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Define repository branch
$BranchRef  = "fabric-26.2"

# Base URL for GitHub raw content
$BaseRawUrl = "https://raw.githubusercontent.com/stlouisn/fabric_template/refs/heads/$BranchRef"

# List of files to download
$FilesToDownload = @(
    ".editorconfig",
    ".gitattributes",
    ".gitignore",
    ".java-style.xml",
    "build.gradle",
    "build-spotless.gradle",
    "gradlew",
    "gradlew.bat",
    "project-build.ps1",
    "project-fileStructure.ps1",
    "project-genSources.ps1",
    "project-runClient.ps1",
    "project-runSpotless.ps1",
    "project-update.ps1",
    "settings.gradle"
)

# Download and overwrite files
$ProgressPreference = 'SilentlyContinue'
foreach ($FileName in $FilesToDownload) {
    Write-Host "Downloading: $FileName..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri "$BaseRawUrl/$FileName" -OutFile ".\$FileName" -ErrorAction Stop
}

Write-Host
Write-Host "Downloads complete.`n" -ForegroundColor Green

# Fetch Gradle versions
$gradleVersions = Invoke-RestMethod "https://services.gradle.org/versions/all"

# Find the latest stable release
$gradleLatest = ($gradleVersions | Where-Object { $_.current -eq $true }).version

# Update the Gradle Wrapper to the latest version
./gradlew --console=colored wrapper --gradle-version $gradleLatest --distribution-type bin
Write-Host
Write-Host "Gradle Wrapper: $gradleLatest"
