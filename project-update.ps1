# Path to this script
$Self = $MyInvocation.MyCommand.Path

# Ensure copilot path exists
$CopilotDirectory = ".\copilot"
if (-not (Test-Path $CopilotDirectory)) {
    New-Item -ItemType Directory -Path $CopilotDirectory -Force | Out-Null
}

# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

# Compute hash before update
$BeforeHash = (Get-FileHash -Path $Self -Algorithm SHA256).Hash

# Define repository details
$Owner  = "stlouisn"
$Repo   = "fabric_template"
$Branch = "fabric-26.2"

# Fetch the latest Commit SHA
Write-Host
Write-Host "Fetching latest Commit SHA for '$Branch'..." -ForegroundColor Yellow
try {
    $ApiUrl = "https://api.github.com/repos/$Owner/$Repo/commits/$Branch"
    $CommitInfo = Invoke-RestMethod -Uri $ApiUrl -Headers @{ "User-Agent" = "PowerShell" }
    $CommitSha = $CommitInfo.sha
    Write-Host
    Write-Host "Latest Commit: $CommitSha" -ForegroundColor Green
} catch {
    Write-Host
    Write-Host "Failed to fetch commit SHA, falling back to branch name..." -ForegroundColor Red
    $CommitSha = $Branch
}

# Base URL for GitHub raw content
$BaseRawUrl = "https://raw.githubusercontent.com/$Owner/$Repo/$CommitSha"

# Download directly using the commit SHA
$ProgressPreference = 'SilentlyContinue'

# Filename of the update script
$ScriptFilename = "project-update.ps1"

# Download update script
Write-Host "Downloading: $ScriptFilename ..." -ForegroundColor Cyan
Invoke-WebRequest -Uri "$BaseRawUrl/$ScriptFilename" -OutFile ".\$ScriptFilename" -ErrorAction Stop
if (-not (Test-Path -Path ".\$ScriptFilename")) {
    Write-Host
    Write-Host "Failed to download file: $ScriptFilename" -ForegroundColor Red
    throw "Script execution stopped due to missing download file."
}

# Compute hash after update
$AfterHash = (Get-FileHash -Path ".\$ScriptFilename" -Algorithm SHA256).Hash

# If the script updated itself, restart it
if ($BeforeHash -ne $AfterHash) {
    Write-Host "`nScript updated. Restarting..." -ForegroundColor Yellow
    & powershell -ExecutionPolicy Bypass -File $Self
    exit
}

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
    "project-runDatagen.ps1",
    "project-runSpotless.ps1",
    "project-versions.properties",
    "settings.gradle"
)

Write-Host

# Download files from repository
foreach ($FileName in $FilesToDownload) {
    Write-Host "Downloading: $FileName ..." -ForegroundColor Cyan
    Invoke-WebRequest -Uri "$BaseRawUrl/$FileName" -OutFile ".\$FileName" -ErrorAction Stop
    if (-not (Test-Path -Path ".\$FileName")) {
        Write-Host
        Write-Host "Failed to download file: $FileName" -ForegroundColor Red
        throw "Script execution stopped due to missing download file."
    }
}

Write-Host
Write-Host "Downloads complete." -ForegroundColor Green

Write-Host

# Build gradle.properties
$GradleTemplate     = "gradle-template.properties"
$ProjectVersions    = "project-versions.properties"
$GradleProperties   = "gradle.properties"
try {
    Write-Host "Combining '$GradleTemplate' and '$ProjectVersions' into '$GradleProperties'" -ForegroundColor Yellow
    Get-Content -Path $GradleTemplate -ErrorAction Stop | Set-Content -Path $GradleProperties -Force -ErrorAction Stop
    Add-Content -Path $GradleProperties -Value "" -ErrorAction Stop
    Get-Content -Path $ProjectVersions -ErrorAction Stop | Add-Content -Path $GradleProperties -ErrorAction Stop
} catch {
    Write-Host
    Write-Host "Failed to generate '$GradleProperties': $_" -ForegroundColor Red
    throw "Script execution stopped because required property files could not be processed."
}

Write-Host
Write-Host "Generated '$GradleProperties' successfully.`n" -ForegroundColor Green

# Fetch Gradle versions
$gradleVersions = Invoke-RestMethod "https://services.gradle.org/versions/all"

# Find the latest stable release
$gradleLatest = ($gradleVersions | Where-Object { $_.current -eq $true }).version

# Update the Gradle Wrapper to the latest version
./gradlew --console=colored wrapper --gradle-version $gradleLatest --distribution-type bin
Write-Host
Write-Host "Gradle Wrapper: $gradleLatest" -ForegroundColor Green

Write-Host
