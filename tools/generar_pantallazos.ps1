# Script PowerShell para generar pantallazos de la Clase 3
# Ejecuta el script Python que genera las capturas PNG y el PDF

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  GENERADOR DE PANTALLAZOS - CLASE 3" -ForegroundColor Cyan
Write-Host "  Pixel & Bean - Arquitectura MVC" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar que Python está instalado
Write-Host "Verificando Python..." -ForegroundColor Yellow
$pythonCmd = Get-Command python -ErrorAction SilentlyContinue

if (-not $pythonCmd) {
    Write-Host "ERROR: Python no está instalado o no está en el PATH" -ForegroundColor Red
    Write-Host "Por favor instala Python desde: https://www.python.org/downloads/" -ForegroundColor Red
    Write-Host ""
    pause
    exit 1
}

$pythonVersion = python --version
Write-Host "  $pythonVersion encontrado" -ForegroundColor Green
Write-Host ""

# Verificar/Instalar dependencias
Write-Host "Verificando dependencias de Python..." -ForegroundColor Yellow

$dependencies = @("Pygments", "Pillow", "reportlab")
$needsInstall = $false

foreach ($dep in $dependencies) {
    $installed = python -c "import $($dep.ToLower())" 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "  $dep no está instalado" -ForegroundColor Red
        $needsInstall = $true
    } else {
        Write-Host "  $dep instalado" -ForegroundColor Green
    }
}

if ($needsInstall) {
    Write-Host ""
    Write-Host "Instalando dependencias necesarias..." -ForegroundColor Yellow
    python -m pip install --upgrade pip
    python -m pip install Pygments Pillow reportlab
    Write-Host ""
}

# Ejecutar el script Python
Write-Host "Ejecutando generador de capturas..." -ForegroundColor Yellow
Write-Host ""

python "tools\export_code_images.py"

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  CAPTURAS GENERADAS EXITOSAMENTE" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Los archivos se encuentran en:" -ForegroundColor Cyan
    Write-Host "  docs\entregas\screenshots\" -ForegroundColor White
    Write-Host ""
    Write-Host "Archivos generados:" -ForegroundColor Cyan
    Write-Host "  - 13 capturas PNG con codigo Java" -ForegroundColor White
    Write-Host "  - mapping.txt (relacion de archivos)" -ForegroundColor White
    Write-Host "  - CLASE3_PANTALLAZOS.pdf (PDF consolidado)" -ForegroundColor White
    Write-Host ""

    # Abrir el directorio de salida
    $openFolder = Read-Host "Deseas abrir la carpeta de screenshots? (S/N)"
    if ($openFolder -eq "S" -or $openFolder -eq "s") {
        Start-Process "docs\entregas\screenshots"
    }

} else {
    Write-Host ""
    Write-Host "ERROR: Hubo un problema al generar las capturas" -ForegroundColor Red
    Write-Host "Revisa los mensajes de error arriba" -ForegroundColor Red
    Write-Host ""
}

Write-Host ""
Write-Host "Presiona cualquier tecla para salir..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")

