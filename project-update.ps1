# Filename of this script
$ScriptFilename = "project-update.ps1"

# Path to this script
$Self = Join-Path $PSScriptRoot $ScriptFilename

# Define repository details
$Owner  = "stlouisn"
$Repo   = "fabric_template"
$Branch = "fabric-26.2"

# Define gradle properties filenames
$GradleTemplate     = "gradle-template.properties"
$ProjectVersions    = "project-versions.properties"
$GradleProperties   = "gradle.properties"

# Suppress download progress messages
$ProgressPreference = 'SilentlyContinue'

function DownloadUpdateScript {

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
        return $true
    }
    return $false
}

function DownloadTemplateFiles {

    # Ensure copilot path exists
    $CopilotDirectory = "copilot"
    if (-not (Test-Path $CopilotDirectory)) {
        New-Item -ItemType Directory -Path $CopilotDirectory -Force | Out-Null
    }

    # Ensure inspectionProfiles path exists
    $InspectionProfilesDirectory = ".idea\inspectionProfiles"
    if (-not (Test-Path $InspectionProfilesDirectory)) {
        New-Item -ItemType Directory -Path $InspectionProfilesDirectory -Force | Out-Null
    }

    # Ensure dictionaries path exists
    $DictionariesDirectory = ".idea\dictionaries"
    if (-not (Test-Path $DictionariesDirectory)) {
        New-Item -ItemType Directory -Path $DictionariesDirectory -Force | Out-Null
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
        Invoke-WebRequest -Uri "$BaseRawUrl/$FileName" -OutFile $FileName -ErrorAction Stop
        if (-not (Test-Path -Path ".\$FileName")) {
            Write-Host
            Write-Host "Failed to download file: $FileName" -ForegroundColor Red
            throw "Script execution stopped due to missing download file."
        }
    }
    Write-Host
}

function DownloadAPIFiles {

    # Mod Exemptions
    $ExemptMods = @(
        "anvilrestoration"
        "armordamagescale"
        "flourish"
    )
    if ($ModId -in $ExemptMods) {
        return
    }

    # Ensure util path exists
    $RootDirectory = "src/main/java/$MavenDomain/$MavenArtifact/util"
    if (-not (Test-Path $RootDirectory)) {
        New-Item -ItemType Directory -Path $RootDirectory -Force | Out-Null
    }

    # List of mapped folders to download
    $FolderMappings = @(
        @{
            Source      = "src/main/java/dev/mod_id/util"
            Destination = "$RootDirectory"
        }
    )

    # List of downloaded files (from mapped folders)
    $DownloadedFiles = [System.Collections.Generic.List[string]]::new()

    # Download folder contents from repository
    $TreeUrl = "https://api.github.com/repos/$Owner/$Repo/git/trees/${TreeSha}?recursive=1"
    $Tree = Invoke-RestMethod -Uri $TreeUrl -Headers @{ "User-Agent" = "PowerShell" }
    foreach ($Mapping in $FolderMappings) {
        $SourcePrefix = "$($Mapping.Source)/"
        $Paths = $Tree.tree |Where-Object { $_.type -eq "blob" -and $_.path.StartsWith($SourcePrefix) } | ForEach-Object { $_.path }
        foreach ($SourcePath in $Paths) {
            $RelativePath = $SourcePath.Substring($SourcePrefix.Length)
            $DestinationPath = Join-Path $Mapping.Destination $RelativePath
            $DestinationDirectory = Split-Path -Parent $DestinationPath
            New-Item -ItemType Directory -Path $DestinationDirectory -Force | Out-Null
            $EncodedPath = ($SourcePath -split "/" | ForEach-Object { [Uri]::EscapeDataString($_) }) -join "/"
            Write-Host "Downloading: $DestinationPath" -ForegroundColor Gray
            Invoke-WebRequest -Uri "$BaseRawUrl/$EncodedPath" -OutFile $DestinationPath -ErrorAction Stop
            $DownloadedFiles.Add($DestinationPath)
        }
    }
    Write-Host

    # Substitute maven_group into placeholder
    foreach ($File in $DownloadedFiles) {
        $Content = [System.IO.File]::ReadAllText($File)
        if ($Content.Contains("dev.mod_id")) {
            $Content = $Content.Replace("dev.mod_id", $MavenGroup)
            [System.IO.File]::WriteAllText(
                $File,
                $Content,
                [System.Text.UTF8Encoding]::new($false)
            )
        }
        if ($Content.Contains('"mod_id"')) {
            $Content = $Content.Replace('"mod_id"', '"' + $ModId + '"')
            [System.IO.File]::WriteAllText(
                $File,
                $Content,
                [System.Text.UTF8Encoding]::new($false)
            )
        }
        if ($Content.Contains('"mod_name"')) {
            $Content = $Content.Replace('"mod_name"', '"' + $ModName + '"')
            [System.IO.File]::WriteAllText(
                $File,
                $Content,
                [System.Text.UTF8Encoding]::new($false)
            )
        }
    }
}

function DownloadDatagenFiles {

    # Extract datagen boolean
    $DownloadDatagenLine = Get-Content $GradleTemplate | Where-Object { $_ -match '^\s*download_datagen\s*=' } | Select-Object -First 1
    if (-not $DownloadDatagenLine) {
        throw "Could not find 'download_datagen' in $GradleTemplate."
    }
    $DownloadDatagen = ($DownloadDatagenLine -replace '^\s*download_datagen\s*=\s*', '').Trim()

    # Return unless download_datagen is true
    if ($DownloadDatagen -ine "true") {
        Write-Host "Datagen is not enabled ... skipping" -ForegroundColor Gray
        Write-Host
        return
    }

    # Ensure datagen paths exist
    $DatagenDirectory = "src/main/java/$MavenDomain/$MavenArtifact/datagen"
    if (-not (Test-Path $DatagenDirectory)) {
        New-Item -ItemType Directory -Path $DatagenDirectory -Force | Out-Null
    }
    if (-not (Test-Path $DatagenDirectory/adapter)) {
        New-Item -ItemType Directory -Path $DatagenDirectory/adapter -Force | Out-Null
    }
    if (-not (Test-Path $DatagenDirectory/compat)) {
        New-Item -ItemType Directory -Path $DatagenDirectory/compat -Force | Out-Null
    }
    if (-not (Test-Path $DatagenDirectory/provider)) {
        New-Item -ItemType Directory -Path $DatagenDirectory/provider -Force | Out-Null
    }

    # List of mapped folders to download
    $FolderMappings = @(
        @{
            Source      = "src/main/java/dev/mod_id/datagen/builder"
            Destination = "$DatagenDirectory/builder"
        },
        @{
            Source      = "src/main/java/dev/mod_id/datagen/util"
            Destination = "$DatagenDirectory/util"
        }
    )

    # List of downloaded files (from mapped folders)
    $DownloadedFiles = [System.Collections.Generic.List[string]]::new()

    # Download folder contents from repository
    $TreeUrl = "https://api.github.com/repos/$Owner/$Repo/git/trees/${TreeSha}?recursive=1"
    $Tree = Invoke-RestMethod -Uri $TreeUrl -Headers @{ "User-Agent" = "PowerShell" }
    foreach ($Mapping in $FolderMappings) {
        $SourcePrefix = "$($Mapping.Source)/"
        $Paths = $Tree.tree |Where-Object { $_.type -eq "blob" -and $_.path.StartsWith($SourcePrefix) } | ForEach-Object { $_.path }
        foreach ($SourcePath in $Paths) {
            $RelativePath = $SourcePath.Substring($SourcePrefix.Length)
            $DestinationPath = Join-Path $Mapping.Destination $RelativePath
            $DestinationDirectory = Split-Path -Parent $DestinationPath
            New-Item -ItemType Directory -Path $DestinationDirectory -Force | Out-Null
            $EncodedPath = ($SourcePath -split "/" | ForEach-Object { [Uri]::EscapeDataString($_) }) -join "/"
            Write-Host "Downloading: $DestinationPath" -ForegroundColor Gray
            Invoke-WebRequest -Uri "$BaseRawUrl/$EncodedPath" -OutFile $DestinationPath -ErrorAction Stop
            $DownloadedFiles.Add($DestinationPath)
        }
    }
    Write-Host

    # Substitute maven_group into placeholder
    foreach ($File in $DownloadedFiles) {
        $Content = [System.IO.File]::ReadAllText($File)
        if ($Content.Contains("dev.mod_id")) {
            $Content = $Content.Replace("dev.mod_id", $MavenGroup)
            [System.IO.File]::WriteAllText(
                $File,
                $Content,
                [System.Text.UTF8Encoding]::new($false)
            )
        }
    }
}

function ExtractMavenGroup {

    # Extract maven group
    $MavenGroupLine = Get-Content $GradleTemplate |Where-Object { $_ -match '^\s*maven_group\s*=' } |Select-Object -First 1
    if (-not $MavenGroupLine) {
        throw "Could not find 'maven_group' in $GradleTemplate."
    }

    # Return maven group
    return ($MavenGroupLine -replace '^\s*maven_group\s*=\s*', '').Trim()
}

function ExtractMavenDomain {

    # Required param
    param([string]$MavenGroup)

    # Extract maven domain
    $MavenParts = $MavenGroup.Split('.')
    if ($MavenParts.Length -lt 2) {
        throw "Invalid maven_group value '$MavenGroup'; expected at least two dot-separated segments."
    }

    # Return maven domain
    return $MavenParts[0..($MavenParts.Length - 2)] -join [IO.Path]::DirectorySeparatorChar
}

function ExtractMavenArtifact {

    # Required param
    param([string]$MavenGroup)

    # Extract maven artifact
    $MavenParts = $MavenGroup.Split('.')
    if ($MavenParts.Length -lt 2) {
        throw "Invalid maven_group value '$MavenGroup'; expected at least two dot-separated segments."
    }

    # Return maven artifact
    return $MavenParts[-1]
}

function ExtractModId {

    # Extract mod id
    $ModIdLine = Get-Content $GradleTemplate |Where-Object { $_ -match '^\s*mod_id\s*=' } |Select-Object -First 1
    if (-not $ModIdLine) {
        throw "Could not find 'mod_id' in $GradleTemplate."
    }

    # Return mod id
    return ($ModIdLine -replace '^\s*mod_id\s*=\s*', '').Trim()
}

function ExtractModName {

    # Extract mod name
    $ModNameLine = Get-Content $GradleTemplate |Where-Object { $_ -match '^\s*mod_name\s*=' } |Select-Object -First 1
    if (-not $ModNameLine) {
        throw "Could not find 'mod_name' in $GradleTemplate."
    }

    # Return mod name
    return ($ModNameLine -replace '^\s*mod_name\s*=\s*', '').Trim()
}

function UpdateGradleProperties {

    # Build gradle properties
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
}

function UpdateGradleWrapper {

    # Fetch Gradle versions
    $gradleVersions = Invoke-RestMethod "https://services.gradle.org/versions/all"

    # Find the latest stable release
    $gradleLatest = ($gradleVersions | Where-Object { $_.current -eq $true }).version

    # Update the Gradle Wrapper to the latest version
    ./gradlew --console=colored wrapper --gradle-version $gradleLatest --distribution-type bin
    Write-Host
    Write-Host "Gradle Wrapper: $gradleLatest" -ForegroundColor Green
    Write-Host
}

# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

# Extract properties
$MavenGroup = ExtractMavenGroup
$MavenDomain = ExtractMavenDomain($MavenGroup)
$MavenArtifact = ExtractMavenArtifact($MavenGroup)
$ModId = ExtractModId
$ModName = ExtractModName

Write-Host
Write-Host "Updating '$ModName' project..." -ForegroundColor Cyan

# Fetch the latest Commit SHA
Write-Host
Write-Host "Fetching latest Commit SHA for '$Branch'..." -ForegroundColor Yellow
try {
    $ApiUrl = "https://api.github.com/repos/$Owner/$Repo/commits/$Branch"
    $CommitInfo = Invoke-RestMethod -Uri $ApiUrl -Headers @{ "User-Agent" = "PowerShell" }
    $CommitSha = $CommitInfo.sha
    $TreeSha = $CommitInfo.commit.tree.sha
    Write-Host
    Write-Host "Latest Commit: $CommitSha" -ForegroundColor Green
} catch {
    Write-Host
    Write-Host "Failed to fetch commit SHA, falling back to branch name..." -ForegroundColor Red
    $CommitSha = $Branch
}

# Base URL for GitHub raw content
$BaseRawUrl = "https://raw.githubusercontent.com/$Owner/$Repo/$CommitSha"

Write-Host
Write-Host "Downloading files..." -ForegroundColor Yellow

# Download this script and exit if it changed
if (DownloadUpdateScript) {
    exit 1
}

# Download template files
DownloadTemplateFiles

# Download API files
DownloadAPIFiles

# Download datagen files
DownloadDatagenFiles

Write-Host "Downloads complete." -ForegroundColor Green
Write-Host

# Update gradle properties
UpdateGradleProperties

# Update gradle wrapper
UpdateGradleWrapper
