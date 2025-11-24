#!/bin/bash
# Script to run basic tests for Pixel & Bean

echo "Compilando tests..."
mkdir -p bin
javac -d bin -sourcepath src/main/java src/main/java/com/pixelbean/ui/LoginFrame.java
javac -d bin -cp bin -sourcepath src/test/java src/test/java/com/pixelbean/test/BasicTest.java

if [ $? -eq 0 ]; then
    echo "Compilación exitosa!"
    echo ""
    echo "Ejecutando tests básicos..."
    java -cp bin com.pixelbean.test.BasicTest
else
    echo "Error en la compilación"
    exit 1
fi
