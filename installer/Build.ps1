param(
    # Version sent by GitHub Actions workflow.
    [string]$Version = "0.0.0"
)

# Removes the prefix 'v' from the tag name (eg: v1.2.3 -> 1.2.3)
$ProductVersion = $Version.TrimStart('v')

# Downloading WinSw
$winSwVersion = "v2.12.0"
$downloadDir = [System.IO.Path]::Combine([System.IO.Path]::GetDirectoryName($MyInvocation.MyCommand.Definition), "winsw-download")
$downloads = @{
    "WinSW-x64.exe" = "https://github.com/winsw/winsw/releases/download/$winSwVersion/WinSW-x64.exe"
    "WinSW-x86.exe" = "https://github.com/winsw/winsw/releases/download/$winSwVersion/WinSW-x86.exe"
}
New-Item -Path $downloadDir -ItemType Directory -Force
foreach($download in $downloads.GetEnumerator()) {
    $fileName = $download.Name
    $urlString = $download.Value

    $targetFile = [System.IO.Path]::Combine($downloadDir, $fileName)
    if (-not ([System.IO.File]::Exists($targetFile))){
        Write-Output -InputObject "Downloading $fileName from $urlString..."
        Invoke-WebRequest -Uri $urlString -OutFile $targetFile
    } else {
        Write-Output -InputObject "$fileName already exists. Skipping download."
    }
}

# Building MSI Package
dotnet build /p:ProductVersion=$ProductVersion /p:DefineConstants="ProductVersion=$ProductVersion"