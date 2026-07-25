# Filename
$File = "file-structure.md"

# Delete cache folder if it exists
$CachePath = "src/generated/resources/.cache"
if (Test-Path $CachePath) {
    Remove-Item $CachePath -Recurse -Force
}

# Write header
'```plaintext' | Set-Content $File

# Write filtered tree output
tree ./src /F /A |
    Select-String -NotMatch "^(Folder PATH|Volume serial|[A-Z]:\\)" |
    Add-Content $File

# Write footer
'```' | Add-Content $File
