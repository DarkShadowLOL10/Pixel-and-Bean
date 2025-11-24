@echo off
REM Script to compile and run Pixel & Bean application on Windows

echo Compilando Pixel ^& Bean...
if not exist bin mkdir bin
javac -d bin -sourcepath src\main\java src\main\java\com\pixelbean\ui\LoginFrame.java

if %ERRORLEVEL% EQU 0 (
    echo Compilacion exitosa!
    echo Iniciando aplicacion...
    java -cp bin com.pixelbean.ui.LoginFrame
) else (
    echo Error en la compilacion
    pause
    exit /b 1
)
