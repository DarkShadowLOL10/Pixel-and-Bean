#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script para generar capturas PNG de archivos Java con resaltado de sintaxis
y consolidarlas en un PDF para la entrega de la Clase 3.

Autor: Enmanuel Chirinos
Fecha: 3 de diciembre de 2025
"""

import os
from pathlib import Path
from pygments import highlight
from pygments.lexers import JavaLexer
from pygments.formatters import ImageFormatter
from PIL import Image
from reportlab.lib.pagesizes import A4
from reportlab.pdfgen import canvas
from reportlab.lib.utils import ImageReader

# Configuración
BASE_PATH = Path("src/cl/enmanuelchirinos/pnb")
OUTPUT_DIR = Path("docs/entregas/screenshots")
PDF_OUTPUT = OUTPUT_DIR / "CLASE3_PANTALLAZOS.pdf"
MAPPING_FILE = OUTPUT_DIR / "mapping.txt"

# Lista de archivos a exportar con sus rutas relativas
FILES_TO_EXPORT = [
    ("01", "repository/IUsuarioRepository.java", "IUsuarioRepository"),
    ("02", "repository/IProductoRepository.java", "IProductoRepository"),
    ("03", "repository/IVentaRepository.java", "IVentaRepository"),
    ("04", "repository/mock/UsuarioRepositoryMock.java", "UsuarioRepositoryMock"),
    ("05", "repository/mock/ProductoRepositoryMock.java", "ProductoRepositoryMock"),
    ("06", "repository/mock/VentaRepositoryMock.java", "VentaRepositoryMock"),
    ("07", "service/UsuarioService.java", "UsuarioService"),
    ("08", "service/ProductoService.java", "ProductoService"),
    ("09", "service/VentaService.java", "VentaService"),
    ("10", "controller/UsuarioController.java", "UsuarioController"),
    ("11", "controller/ProductoController.java", "ProductoController"),
    ("12", "controller/VentaController.java", "VentaController"),
    ("13", "app/ApplicationContext.java", "ApplicationContext"),
]


def ensure_output_dir():
    """Crea el directorio de salida si no existe"""
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    print(f"✓ Directorio de salida: {OUTPUT_DIR}")


def generate_code_image(source_path, output_path, title):
    """
    Genera una imagen PNG con resaltado de sintaxis Java

    Args:
        source_path: Ruta del archivo Java fuente
        output_path: Ruta donde guardar la imagen PNG
        title: Título descriptivo del archivo
    """
    try:
        # Leer el código fuente
        with open(source_path, 'r', encoding='utf-8') as f:
            code = f.read()

        # Configurar el formatter de Pygments
        # Usar Consolas (Windows) o Courier New como fallback
        formatter = ImageFormatter(
            font_name='Consolas',
            font_size=11,
            line_numbers=True,
            style='friendly',
            line_pad=4,
            image_pad=15,
        )

        # Generar la imagen con resaltado de sintaxis
        result = highlight(code, JavaLexer(), formatter)

        # Guardar la imagen
        with open(output_path, 'wb') as f:
            f.write(result)

        print(f"  ✓ {output_path.name}")
        return True

    except FileNotFoundError:
        print(f"  ✗ ARCHIVO NO ENCONTRADO: {source_path}")
        return False
    except Exception as e:
        print(f"  ✗ ERROR al procesar {source_path}: {str(e)}")
        return False


def generate_mapping_file(mapping_data):
    """Genera el archivo mapping.txt con la relación número-archivo-PNG"""
    try:
        with open(MAPPING_FILE, 'w', encoding='utf-8') as f:
            f.write("# Mapeo de Archivos - Clase 3\n")
            f.write("# Formato: Número | Archivo Fuente | Captura PNG\n")
            f.write("-" * 80 + "\n\n")

            for num, source, png_name in mapping_data:
                f.write(f"{num} | {source} | {png_name}\n")

        print(f"\n✓ Archivo de mapeo generado: {MAPPING_FILE}")
        return True
    except Exception as e:
        print(f"\n✗ ERROR al generar mapping.txt: {str(e)}")
        return False


def generate_pdf(image_files):
    """
    Genera un PDF consolidado con todas las capturas

    Args:
        image_files: Lista de rutas de archivos PNG
    """
    try:
        # Crear el canvas PDF
        c = canvas.Canvas(str(PDF_OUTPUT), pagesize=A4)
        page_width, page_height = A4

        print(f"\n📄 Generando PDF: {PDF_OUTPUT}")

        for idx, img_path in enumerate(image_files, 1):
            if not img_path.exists():
                print(f"  ⚠ Imagen no encontrada: {img_path.name}")
                continue

            try:
                # Abrir la imagen
                img = Image.open(img_path)
                img_width, img_height = img.size

                # Calcular escala para ajustar a A4 (con márgenes)
                max_width = page_width - 40  # 20px margen a cada lado
                max_height = page_height - 60  # 30px margen arriba y abajo

                scale = min(max_width / img_width, max_height / img_height, 1.0)
                new_width = img_width * scale
                new_height = img_height * scale

                # Centrar la imagen en la página
                x = (page_width - new_width) / 2
                y = (page_height - new_height) / 2

                # Dibujar la imagen en el PDF
                c.drawImage(
                    str(img_path),
                    x, y,
                    width=new_width,
                    height=new_height,
                    preserveAspectRatio=True
                )

                # Agregar número de página
                c.setFont("Helvetica", 9)
                c.drawString(page_width - 50, 20, f"Página {idx}")

                print(f"  ✓ Página {idx}: {img_path.name}")

                # Nueva página para la siguiente imagen
                c.showPage()

            except Exception as e:
                print(f"  ✗ ERROR al procesar imagen {img_path.name}: {str(e)}")
                continue

        # Guardar el PDF
        c.save()
        print(f"\n✓ PDF generado exitosamente: {PDF_OUTPUT}")
        print(f"  Total de páginas: {len(image_files)}")
        return True

    except Exception as e:
        print(f"\n✗ ERROR al generar PDF: {str(e)}")
        return False


def main():
    """Función principal del script"""
    print("=" * 80)
    print("  GENERADOR DE PANTALLAZOS - CLASE 3")
    print("  Pixel & Bean - Arquitectura MVC")
    print("=" * 80)
    print()

    # Verificar que estamos en el directorio correcto
    if not BASE_PATH.exists():
        print(f"✗ ERROR: No se encuentra el directorio base: {BASE_PATH}")
        print(f"  Asegúrate de ejecutar el script desde la raíz del proyecto.")
        return 1

    # Crear directorio de salida
    ensure_output_dir()
    print()

    # Generar imágenes
    print("📸 Generando capturas de código...")
    print()

    mapping_data = []
    generated_images = []

    for num, relative_path, display_name in FILES_TO_EXPORT:
        source_path = BASE_PATH / relative_path
        png_name = f"{num}_{display_name}.png"
        output_path = OUTPUT_DIR / png_name

        print(f"[{num}/13] {display_name}")

        if generate_code_image(source_path, output_path, display_name):
            mapping_data.append((num, relative_path, png_name))
            generated_images.append(output_path)

    print()
    print(f"✓ Capturas generadas: {len(generated_images)}/13")

    # Generar archivo de mapeo
    if mapping_data:
        generate_mapping_file(mapping_data)

    # Generar PDF
    if generated_images:
        generate_pdf(generated_images)
    else:
        print("\n✗ No se generaron imágenes, no se puede crear el PDF")
        return 1

    # Resumen final
    print()
    print("=" * 80)
    print("✅ PROCESO COMPLETADO")
    print("=" * 80)
    print()
    print("📂 Archivos generados:")
    print(f"   • {len(generated_images)} capturas PNG en: {OUTPUT_DIR}")
    print(f"   • Mapeo: {MAPPING_FILE}")
    print(f"   • PDF consolidado: {PDF_OUTPUT}")
    print()
    print("🎓 Listo para entregar!")
    print()

    return 0


if __name__ == "__main__":
    exit(main())

