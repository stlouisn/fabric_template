'```plaintext' | Set-Content project-fileStructure.md

tree .\src /F /A |
    Select-String -NotMatch "^(Folder PATH|Volume serial|[A-Z]:\\)" |
    Select-String -NotMatch "\.cache" |
    Add-Content project-FileStructure.md

'```' | Add-Content project-fileStructure.md
