# Filename
$File = "file-structure.md"

# Write header
'```plaintext' | Set-Content $File

# Write filtered tree output
tree .\src /F /A |
    Select-String -NotMatch "^(Folder PATH|Volume serial|[A-Z]:\\)" |
    Add-Content $File

# Write footer
'```' | Add-Content $File
