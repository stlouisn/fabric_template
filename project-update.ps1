# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Clear-Host

# Define repository details
$Owner  = "stlouisn"
$Repo   = "fabric_template"
$Branch = "fabric-26.2"

# Fetch the latest Commit SHA
Write-Host "Fetching latest commit SHA for '$Branch'..." -ForegroundColor Yellow
try {
    $ApiUrl = "https://api.github.com/repos/$Owner/$Repo/commits/$Branch"
    $CommitInfo = Invoke-RestMethod -Uri $ApiUrl -Headers @{ "User-Agent" = "PowerShell" }
    $CommitSha = $CommitInfo.sha
    Write-Host "Latest Commit: $CommitSha" -ForegroundColor Green
} catch {
    Write-Host "Failed to fetch commit SHA, falling back to branch name..." -ForegroundColor Red
    $CommitSha = $Branch
}

# Base URL for GitHub raw content
$BaseRawUrl = "https://raw.githubusercontent.com/$Owner/$Repo/$CommitSha"

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
    "project-versions.properties",
    "settings.gradle"
)

# Ensure copilot folder exists
$Directory = ".\copilot"
if (-not (Test-Path $Directory)) {
    New-Item -ItemType Directory -Path $Directory -Force | Out-Null
}

Write-Host

# Download directly using the commit SHA
$ProgressPreference = 'SilentlyContinue'
foreach ($FileName in $FilesToDownload) {
    Write-Host "Downloading: $FileName..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri "$BaseRawUrl/$FileName" -OutFile ".\$FileName" -ErrorAction Stop
}

#todo: add try/catch to check for downloaded files
Write-Host
Write-Host "Downloads complete.`n" -ForegroundColor Green

# Build gradle.properties
$GradleTemplate = "gradle-template.properties"
$ProjectVersions    = "project-versions.properties"
$GradleProperties   = "gradle.properties"
try {
    Write-Host "Combining '$ProjectProps' and '$ProjectVersions' into '$GradleProps'..." -ForegroundColor Yellow
    Get-Content -Path $ProjectProps -ErrorAction Stop | Set-Content -Path $GradleProps -Force -ErrorAction Stop
    Add-Content -Path $GradleProps -Value "`n" -ErrorAction Stop
    Get-Content -Path $ProjectVersions -ErrorAction Stop | Add-Content -Path $GradleProps -ErrorAction Stop
} catch {
    Write-Host "Failed to generate '$GradleProps': $_" -ForegroundColor Red
    throw "Script execution stopped because required property files could not be processed."
}

Write-Host
Write-Host "Generated '$GradleProps' successfully.`n" -ForegroundColor Green

# Fetch Gradle versions
$gradleVersions = Invoke-RestMethod "https://services.gradle.org/versions/all"

# Find the latest stable release
$gradleLatest = ($gradleVersions | Where-Object { $_.current -eq $true }).version

# Update the Gradle Wrapper to the latest version
./gradlew --console=colored wrapper --gradle-version $gradleLatest --distribution-type bin
Write-Host
Write-Host "Gradle Wrapper: $gradleLatest" -ForegroundColor Green

Write-Host
