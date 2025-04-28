# Set source directory
$copyFromPath = "D:\Git Projects\just_enough_items\gui\build\classes\java\main\mezz\jei\gui\overlay"

# Set destination directory
$copyToPath = "D:\Git Projects\just_enough_items\mod_patches\mezz\jei\gui\overlay"

# Set source filename
$copyFileName = "ConfigButton.class"

# Create destination directory if it doesn't exist
If (!(Test-Path -Path $copyToPath)) {
    New-Item -Path $copyToPath -ItemType Directory
}

# Fix the destination directory
$copyToPath = $copyToPath + "\."

# combine the source directory and filename
$copyFileName = $copyFromPath + "\" + $copyFileName

# Copy the class files from the build directory to the mod_patches directory
Copy-Item $copyFileName $copyToPath -Force
