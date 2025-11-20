# Smart Home System - Javadoc Generation Script (PowerShell)
# This script generates Javadoc for the Smart Home Automation System

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Smart Home System - Javadoc Generator" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan
Write-Host ""

# Create output directory if it doesn't exist
$javadocDir = "target/javadoc"
if (-not (Test-Path $javadocDir)) {
    Write-Host "Creating $javadocDir directory..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $javadocDir -Force | Out-Null
}

Write-Host "Generating Javadoc..." -ForegroundColor Yellow
Write-Host ""

# Generate Javadoc
javadoc -encoding UTF-8 -d target/javadoc -sourcepath src/main/java -subpackages com

Write-Host ""
Write-Host "============================================" -ForegroundColor Green
Write-Host "✅ Javadoc generation complete!" -ForegroundColor Green
Write-Host "============================================" -ForegroundColor Green
Write-Host ""
Write-Host "Documentation location: $(Get-Location)\target\javadoc\index.html" -ForegroundColor Cyan
Write-Host ""
Write-Host "To view the documentation:" -ForegroundColor Yellow
Write-Host "  1. Open the file in your browser" -ForegroundColor White
Write-Host "  2. Or use: Start-Process (Get-Item target/javadoc/index.html).FullName" -ForegroundColor White
Write-Host ""
