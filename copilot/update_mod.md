# Minecraft Mod Migration

Update this mod in carefully verified stages. Before making changes, inspect the project structure, build files, source sets, and dependency declarations. Do not assume that “Minecraft Fabric 26.3” is a valid or supported target version: check the project’s existing configuration and available version settings, and ask me to clarify if the target is ambiguous or unsupported.

## Stage 1: Remove Forge and NeoForge

1. Identify all Forge- and NeoForge-specific modules, source sets, build plugins, configuration, code, resources, and dependencies.
2. Before changing or removing any dependency, show me each dependency and offer a few practical choices for handling it, such as:
   - Keep it if it is Fabric-compatible.
   - Replace it with a Fabric-compatible alternative.
   - Remove the feature that requires it.
   - Pause while I decide.
3. **Do not make dependency changes until I approve how to handle each affected dependency.**
4. After I approve, remove the Forge and NeoForge configurations and platform-specific code. Preserve shared functionality wherever possible.
5. Build the project and run the available checks. Report exactly what you ran and whether it passed.
6. **Stop here and confirm that Forge and NeoForge have been removed and the project still works. Do not proceed until I confirm.**

## Stage 2: Remove Publishing and Repository Extras

After I confirm Stage 1:

1. Remove publishing to Modrinth and CurseForge, including related plugins, credentials/configuration references, and publishing tasks.
2. Remove the `.github` folder.
3. Remove the project’s license file and any license-specific configuration or references.
4. Remove macOS-specific support, including platform-specific code, scripts, build settings, or documentation. Preserve cross-platform functionality and avoid removing general functionality just because it also works on macOS.
5. Before removing or changing any dependency, ask me what to do and offer a few choices for that dependency.
6. Run the available checks and report the results. Stop and confirm this stage is complete before proceeding.

## Stage 3: Consolidate as a Standard Fabric Project

After I confirm Stage 2, combine the `common` and `fabric` code into a standard Fabric configuration under `/src`.

- Preserve behavior, assets, metadata, and tests where applicable.
- Ask me before changing any dependency, and offer a few practical options for handling it.
- Run the available checks and report the results. Stop and confirm this stage is complete before proceeding.

## Stage 4: Update the Minecraft/Fabric Target

Only after I confirm Stage 3, update the project to the confirmed Minecraft 26.3 target and compatible Fabric tooling and dependencies.

- Ask me before making any dependency changes that I have not already approved.
- Run the available checks and report the results.

## Instructions for Every Stage

- Make changes in small, reviewable steps.
- Do not silently remove features or dependencies.
- Do not claim that a stage works unless its checks actually pass.
- At each stage, summarize the files changed, decisions needed, checks run, and any failures.
- If a check cannot be run, say so and explain why.
