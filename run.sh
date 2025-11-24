#!/bin/bash
# Script to compile and run Pixel & Bean application

echo "Compilando Pixel & Bean..."
mkdir -p bin
javac -d bin -sourcepath src/main/java src/main/java/com/pixelbean/ui/LoginFrame.java

if [ $? -eq 0 ]; then
    echo "Compilación exitosa!"
    echo "Iniciando aplicación..."
    java -cp bin com.pixelbean.ui.LoginFrame
else
    echo "Error en la compilación"
    exit 1
fi
