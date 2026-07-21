# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Define repository branch
$BranchRef  = "fabric-26.2"

# Base URL for GitHub raw content
$BaseRawUrl = "https://raw.githubusercontent.com/stlouisn/fabric_template/$BranchRef"

# List of files to download
$FilesToDownload = @(
    ".idea/inspectionProfiles/Project_Default.xml",
    ".idea/betterCommentsSettings.xml",
    "copilot/coding.txt",
    "copilot/efficiency_audit.txt",
    "copilot/generate_javadoc.txt",
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

# Create copilot path
$Directory = ".\copilot"
if (-not (Test-Path $Directory)) {
    New-Item -ItemType Directory -Path $Directory -Force | Out-Null
}

Write-Host

# Download and overwrite files
$ProgressPreference = 'SilentlyContinue'
$Headers = @{
    "Cache-Control" = "no-cache, no-store, must-revalidate"
    "Pragma"        = "no-cache"
}
foreach ($FileName in $FilesToDownload) {
    Write-Host "Downloading: $FileName..." -ForegroundColor Cyan
    $NoCacheUrl = "$BaseRawUrl/$FileName`?nocache=$(Get-Date -UFormat %s)"
    Invoke-WebRequest -Uri "$NoCacheUrl" -Headers $Headers -OutFile ".\$FileName" -ErrorAction Stop
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
Write-Host "Gradle Wrapper: $gradleLatest" -ForegroundColor Green

Write-Host
