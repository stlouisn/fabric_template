# Set mod_patches directory
$patchesDirectory = "mod_patches\mezz\jei\gui\overlay"

# Create the mod_patches directory if it doesn't exist
If (!(Test-Path -Path $patchesDirectory)) {
    New-Item -Path $patchesDirectory -ItemType Directory
}

# Copy the class files from the build directory to the mod_patches directory
Copy-Item 'D:\Git Projects\just_enough_items\gui\build\classes\java\main\mezz\jei\gui\overlay\ConfigButton.class' 'D:\Git Projects\just_enough_items\mod_patches\mezz\jei\gui\overlay\.' -Force
