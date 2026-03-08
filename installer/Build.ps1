param(
    # Version sent by GitHub Actions workflow.
    [string]$Version = "0.0.0"
)

# Removes the prefix 'v' from the tag name (eg: v1.2.3 -> 1.2.3)
$ProductVersion = $Version.TrimStart('v')

# Building MSI Package
dotnet build /p:ProductVersion=$ProductVersion