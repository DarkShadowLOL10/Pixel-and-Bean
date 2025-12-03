# Generador de Pantallazos - CLASE3 📸

Este documento explica cómo usar el script Python que genera capturas PNG con resaltado de sintaxis Java, un archivo de mapeo y un PDF consolidado.

---

## 📋 ¿Qué hace el script?

El script `tools/export_code_images.py` realiza las siguientes tareas automáticamente:

1. **Lee 13 archivos Java** desde `src/cl/enmanuelchirinos/pnb/`:
   - IUsuarioRepository.java
   - IProductoRepository.java
   - IVentaRepository.java
   - UsuarioRepositoryMock.java
   - ProductoRepositoryMock.java
   - VentaRepositoryMock.java
   - UsuarioService.java
   - ProductoService.java
   - VentaService.java
   - UsuarioController.java
   - ProductoController.java
   - VentaController.java
   - ApplicationContext.java

2. **Aplica resaltado de sintaxis Java** usando Pygments con:
   - Números de línea
   - Estilo amigable "friendly"
   - Fuente monoespaciada DejaVu Sans Mono

3. **Exporta cada archivo como imagen PNG** con nombres ordenados:
   - `01_IUsuarioRepository.png`
   - `02_IProductoRepository.png`
   - ... hasta `13_ApplicationContext.png`

4. **Genera un archivo `mapping.txt`** que relaciona:
   - Número de orden
   - Nombre del archivo fuente
   - Nombre de la captura PNG

5. **Crea un PDF `CLASE3_PANTALLAZOS.pdf`** con todas las imágenes en orden, cada una en una página A4.

---

## 🚀 Ejecución Rápida

### Opción 1: Script PowerShell (más fácil)

Ejecuta desde la raíz del proyecto:

```powershell
powershell -ExecutionPolicy Bypass -File "tools\generar_pantallazos.ps1"
```

O simplemente haz doble clic en `tools\generar_pantallazos.ps1` (si tienes permisos de ejecución).

### Opción 2: Script Python directo

Desde la raíz del proyecto:

```powershell
python tools/export_code_images.py
```

Con rutas personalizadas:

```powershell
python tools/export_code_images.py --src "ruta/a/archivos/java" --out "ruta/salida"
```

---

## 📦 Requisitos Previos

### 1. Python 3.8 o superior

Verifica tu versión:
```powershell
python --version
```

### 2. Librerías necesarias

Instala las dependencias (solo una vez):

```powershell
pip install Pillow reportlab pygments
```

O actualiza pip primero:

```powershell
python -m pip install --upgrade pip
pip install Pillow reportlab pygments
```

---

## 📁 Estructura de Archivos

### Archivos creados:

```
pixel-and-bean-master/
├── tools/
│   ├── export_code_images.py          # Script principal
│   └── generar_pantallazos.ps1        # Lanzador PowerShell
│
└── docs/entregas/CLASE3-EVIDENCIAS/
    ├── screens/                        # Carpeta con las 13 imágenes PNG
    │   ├── 01_IUsuarioRepository.png
    │   ├── 02_IProductoRepository.png
    │   ├── ...
    │   └── 13_ApplicationContext.png
    │
    ├── mapping.txt                     # Mapeo número-archivo-captura
    └── CLASE3_PANTALLAZOS.pdf          # PDF con todas las imágenes
```

---

## 🎯 Salidas Generadas

### 1. Imágenes PNG (`docs/entregas/CLASE3-EVIDENCIAS/screens/`)

Cada archivo Java se convierte en una imagen PNG con:
- Resaltado de sintaxis
- Números de línea
- Fondo claro con código legible

**Nombres**: `01_NombreArchivo.png` a `13_NombreArchivo.png`

### 2. Archivo `mapping.txt`

Formato:
```
01	IUsuarioRepository.java	01_IUsuarioRepository.png
02	IProductoRepository.java	02_IProductoRepository.png
...
13	ApplicationContext.java	13_ApplicationContext.png
```

### 3. PDF `CLASE3_PANTALLAZOS.pdf`

- Cada imagen en una página A4
- Imágenes centradas y escaladas proporcionalmente
- Orden según el checklist original

---

## ⚙️ Parámetros del Script

El script acepta argumentos opcionales:

```powershell
python tools/export_code_images.py --src <carpeta_fuente> --out <carpeta_salida>
```

**Parámetros:**

- `--src`: Carpeta con los archivos `.java` 
  - Por defecto: `src/cl/enmanuelchirinos/pnb`
  
- `--out`: Carpeta base de salida
  - Por defecto: `docs/entregas/CLASE3-EVIDENCIAS`

**Ejemplo:**

```powershell
python tools/export_code_images.py --src "C:\MiProyecto\src\java" --out "C:\Salida\CLASE3"
```

---

## 🛠️ Manejo de Errores

### Si falta un archivo Java:

El script crea una imagen informativa gris con el mensaje:
```
Archivo no encontrado: ruta/archivo.java
Se esperaba para índice XX (NombreArchivo.java)
```

### Si falla el renderizado con Pygments:

Crea una imagen rosa con el mensaje de error específico.

### Archivos no afectados:

Si hay un error en un archivo, el script **continúa** con los demás archivos y genera el PDF con todas las capturas disponibles.

---

## 🎨 Personalización

### Cambiar estilo de resaltado:

Edita `tools/export_code_images.py`, línea ~90:

```python
formatter = ImageFormatter(
    style='friendly',  # Opciones: 'monokai', 'vim', 'default', etc.
    font_size=14,      # Tamaño de fuente
    ...
)
```

Estilos disponibles: https://pygments.org/styles/

### Cambiar tamaño de imagen:

Si las imágenes informativas son muy grandes/pequeñas, edita línea ~190:

```python
img = Image.new('RGB', (1280, 720), color=(245, 245, 245))
```

---

## 📞 Solución de Problemas

### Error: "No se encuentra python"

Asegúrate de tener Python instalado y en el PATH del sistema:
```powershell
python --version
```

Si no funciona, usa la ruta completa:
```powershell
C:\Users\TU_USUARIO\AppData\Local\Programs\Python\Python3XX\python.exe tools/export_code_images.py
```

### Error: "ModuleNotFoundError: No module named 'PIL'"

Instala las dependencias:
```powershell
pip install Pillow reportlab pygments
```

### Error: "can't open file"

Verifica que estés ejecutando el script desde la **raíz del proyecto** `pixel-and-bean-master`:

```powershell
cd "C:\Users\enman\Documents\POO_Duoc\2 semestre\pixel-and-bean-master"
python tools/export_code_images.py
```

### Las imágenes se ven cortadas en el PDF

El script escala automáticamente para A4. Si el código es muy largo, considera:
- Usar tamaño de fuente más pequeño (edita `font_size=14` a un valor menor)
- Dividir archivos largos en múltiples capturas

---

## 📝 Resumen de Comandos

```powershell
# 1. Instalar dependencias (solo primera vez)
pip install Pillow reportlab pygments

# 2. Generar pantallazos (opción PowerShell)
powershell -ExecutionPolicy Bypass -File "tools\generar_pantallazos.ps1"

# 3. Generar pantallazos (opción Python)
python tools/export_code_images.py

# 4. Ver resultados
# PNGs en: docs/entregas/CLASE3-EVIDENCIAS/screens/
# Mapping: docs/entregas/CLASE3-EVIDENCIAS/mapping.txt
# PDF:     docs/entregas/CLASE3-EVIDENCIAS/CLASE3_PANTALLAZOS.pdf
```

---

## ✅ Checklist de Verificación

Después de ejecutar el script, verifica:

- [ ] Se crearon 13 archivos PNG en `docs/entregas/CLASE3-EVIDENCIAS/screens/`
- [ ] Existe el archivo `mapping.txt` con 13 líneas
- [ ] Existe el PDF `CLASE3_PANTALLAZOS.pdf`
- [ ] El PDF tiene 13 páginas (una por archivo)
- [ ] Las imágenes tienen resaltado de sintaxis y números de línea
- [ ] El mapping relaciona correctamente número-archivo-captura

---

## 📚 Archivos del Proyecto

- **`tools/export_code_images.py`**: Script principal en Python
- **`tools/generar_pantallazos.ps1`**: Lanzador PowerShell para Windows
- **`README_CLASE3.md`**: Este archivo de instrucciones

---

¡Listo! 🎉 Ahora puedes generar los pantallazos con un solo comando.
