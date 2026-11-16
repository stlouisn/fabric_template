# Forcefully terminate all java.exe processes silently
Stop-Process -Name "java" -Force -ErrorAction SilentlyContinue

Write-Host

# Build JAR
./gradlew --console=colored clean build -x spotlessCheck -x spotlessApply

# Copy JARs to project root folder
$NewJars = @(Get-ChildItem "build/libs/*.jar" -ErrorAction SilentlyContinue)
if ($NewJars.Count -gt 0) {
    Remove-Item "jars/*.jar" -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Force -Path "jars" | Out-Null
    $NewJars | Copy-Item -Destination "jars/"
}

Write-Host
