# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

# Filename of this script
$ScriptFilename = "project-update.ps1"

# Path to this script
$Self = Join-Path $PSScriptRoot $ScriptFilename

# Ensure idea paths exists
$CopilotDirectory = "copilot"
if (-not (Test-Path $CopilotDirectory)) {
    New-Item -ItemType Directory -Path $CopilotDirectory -Force | Out-Null
}

# Ensure copilot path exists
$InspectionProfilesDirectory = ".idea\inspectionProfiles"
if (-not (Test-Path $InspectionProfilesDirectory)) {
    New-Item -ItemType Directory -Path $InspectionProfilesDirectory -Force | Out-Null
}
$DictionariesDirectory = ".idea\dictionaries"
if (-not (Test-Path $DictionariesDirectory)) {
    New-Item -ItemType Directory -Path $DictionariesDirectory -Force | Out-Null
}

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
    $TreeSha = $CommitInfo.commit.tree.sha
    if (-not $TreeSha) {
        throw "GitHub's commit response did not contain a tree SHA."
    }
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

Write-Host
Write-Host "Downloading files..." -ForegroundColor Yellow

# Compute hash before update
$BeforeHash = (Get-FileHash -Path $Self -Algorithm SHA256).Hash

# Download update script
Write-Host
Write-Host "Downloading: $ScriptFilename ..." -ForegroundColor Gray
Invoke-WebRequest -Uri "$BaseRawUrl/$ScriptFilename" -OutFile ".\$ScriptFilename" -ErrorAction Stop
if (-not (Test-Path -Path ".\$ScriptFilename")) {
    Write-Host
    Write-Host "Failed to download file: $ScriptFilename" -ForegroundColor Red
    throw "Script execution stopped due to missing download file."
}

# Compute hash after update
$AfterHash = (Get-FileHash -Path $Self -Algorithm SHA256).Hash

# Exit if the script updated itself
if ($BeforeHash -ne $AfterHash) {
    Write-Host
    Write-Host "Script updated. You will need to restart it." -ForegroundColor Red
    Write-Host
    exit 1
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

# Download files from repository
foreach ($FileName in $FilesToDownload) {
    Write-Host "Downloading: $FileName ..." -ForegroundColor Gray
    Invoke-WebRequest -Uri "$BaseRawUrl/$FileName" -OutFile ".\$FileName" -ErrorAction Stop
    if (-not (Test-Path -Path ".\$FileName")) {
        Write-Host
        Write-Host "Failed to download file: $FileName" -ForegroundColor Red
        throw "Script execution stopped due to missing download file."
    }
}

# Extract maven group
$MavenGroupLine = Get-Content "gradle.properties" |Where-Object { $_ -match '^\s*maven_group\s*=' } |Select-Object -First 1
if (-not $MavenGroupLine) {
    throw "Could not find 'maven_group' in gradle.properties."
}
$MavenGroup = ($MavenGroupLine -replace '^\s*maven_group\s*=\s*', '').Trim()
$MavenParts = $MavenGroup.Split('.')
if ($MavenParts.Length -lt 2) {
    throw "Invalid maven_group value '$MavenGroup'; expected at least two dot-separated segments."
}
$MavenDomain = $MavenParts[0..($MavenParts.Length - 2)] -join [IO.Path]::DirectorySeparatorChar
$MavenArtifact = $MavenParts[-1]

$DatagenDirectory = "src/main/java/$MavenDomain/$MavenArtifact/datagen"
if (-not (Test-Path $DatagenDirectory)) {
    throw "Required datagen directory not found: $DatagenDirectory"
    #New-Item -ItemType Directory -Path $DatagenDirectory -Force | Out-Null
}

# List of mapped folders to download
$FolderMappings = @(
    @{
        Source      = "src/main/java/dev/mod_id/datagen/builder"
        Destination = "$DatagenDirectory/builder"
    },
    @{
        Source      = "src/main/java/dev/mod_id/datagen/utils"
        Destination = "$DatagenDirectory/utils"
    }
)

# Download folders from repository
$TreeUrl = "https://api.github.com/repos/$Owner/$Repo/git/trees/$TreeSha?recursive=1"
$Tree = Invoke-RestMethod -Uri $TreeUrl -Headers @{ "User-Agent" = "PowerShell" }
foreach ($Mapping in $FolderMappings) {
    $SourcePrefix = "$($Mapping.Source)/"
    $Paths = $Tree.tree |
        Where-Object {
            $_.type -eq "blob" -and $_.path.StartsWith($SourcePrefix)
        } |
        ForEach-Object { $_.path }
    foreach ($SourcePath in $Paths) {
        $RelativePath = $SourcePath.Substring($SourcePrefix.Length)
        $DestinationPath = Join-Path $Mapping.Destination $RelativePath
        $DestinationDirectory = Split-Path -Parent $DestinationPath
        New-Item -ItemType Directory -Path $DestinationDirectory -Force | Out-Null
        $EncodedPath = ($SourcePath -split "/" | ForEach-Object {
            [Uri]::EscapeDataString($_)
        }) -join "/"
        Write-Host "Downloading: $SourcePath -> $DestinationPath" -ForegroundColor Gray
        Invoke-WebRequest `
            -Uri "$BaseRawUrl/$EncodedPath" `
            -OutFile $DestinationPath `
            -ErrorAction Stop
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
Write-Host "Generated '$GradleProperties' successfully." -ForegroundColor Green

# Fetch Gradle versions
$gradleVersions = Invoke-RestMethod "https://services.gradle.org/versions/all"

# Find the latest stable release
$gradleLatest = ($gradleVersions | Where-Object { $_.current -eq $true }).version

# Update the Gradle Wrapper to the latest version
./gradlew --console=colored wrapper --gradle-version $gradleLatest --distribution-type bin
Write-Host
Write-Host "Gradle Wrapper: $gradleLatest" -ForegroundColor Green

Write-Host
