# 📸 Generador de Pantallazos - CLASE 3

Este script genera automáticamente capturas PNG de los archivos Java con resaltado de sintaxis y los consolida en un PDF para la entrega.

---

## ✅ Lo que hace el script

1. **Lee los 13 archivos Java nuevos** de la Clase 3:
   - 3 interfaces de repositorio
   - 3 implementaciones Mock
   - 3 servicios
   - 3 controladores
   - 1 ApplicationContext

2. **Genera capturas PNG** con:
   - Resaltado de sintaxis Java profesional
   - Números de línea
   - Nombres ordenados (01_ hasta 13_)

3. **Crea un archivo `mapping.txt`** que relaciona:
   - Número de orden
   - Archivo fuente
   - Nombre de la captura PNG

4. **Genera `CLASE3_PANTALLAZOS.pdf`** con:
   - Todas las capturas en un solo PDF
   - Cada archivo en una página A4
   - Números de página

---

## 🚀 Uso Rápido

### Windows (PowerShell)

```powershell
powershell -ExecutionPolicy Bypass -File "tools\generar_pantallazos.ps1"
```

El script:
- ✅ Verifica que Python esté instalado
- ✅ Instala dependencias automáticamente si faltan
- ✅ Ejecuta el generador
- ✅ Abre la carpeta de resultados al terminar

---

## 📦 Requisitos

### Python 3.7+
Descarga desde: https://www.python.org/downloads/

### Librerías (se instalan automáticamente):
- `Pygments` - Resaltado de sintaxis
- `Pillow` - Procesamiento de imágenes
- `reportlab` - Generación de PDF

---

## 📂 Archivos Generados

Todos los archivos se guardan en `docs/entregas/screenshots/`:

```
docs/entregas/screenshots/
├── 01_IUsuarioRepository.png
├── 02_IProductoRepository.png
├── 03_IVentaRepository.png
├── 04_UsuarioRepositoryMock.png
├── 05_ProductoRepositoryMock.png
├── 06_VentaRepositoryMock.png
├── 07_UsuarioService.png
├── 08_ProductoService.png
├── 09_VentaService.png
├── 10_UsuarioController.png
├── 11_ProductoController.png
├── 12_VentaController.png
├── 13_ApplicationContext.png
├── mapping.txt
└── CLASE3_PANTALLAZOS.pdf  ← Este es el que entregas
```

---

## 🎯 Lista de Archivos Capturados

| # | Archivo | Ruta |
|---|---------|------|
| 01 | IUsuarioRepository.java | repository/ |
| 02 | IProductoRepository.java | repository/ |
| 03 | IVentaRepository.java | repository/ |
| 04 | UsuarioRepositoryMock.java | repository/mock/ |
| 05 | ProductoRepositoryMock.java | repository/mock/ |
| 06 | VentaRepositoryMock.java | repository/mock/ |
| 07 | UsuarioService.java | service/ |
| 08 | ProductoService.java | service/ |
| 09 | VentaService.java | service/ |
| 10 | UsuarioController.java | controller/ |
| 11 | ProductoController.java | controller/ |
| 12 | VentaController.java | controller/ |
| 13 | ApplicationContext.java | app/ |

---

## 🔧 Solución de Problemas

### "Python no está instalado"
1. Descarga Python: https://www.python.org/downloads/
2. Durante la instalación, marca "Add Python to PATH"
3. Reinicia PowerShell

### "ModuleNotFoundError"
El script instala las dependencias automáticamente. Si falla:
```powershell
python -m pip install Pygments Pillow reportlab
```

### "Archivo no encontrado"
Asegúrate de ejecutar el script desde la **raíz del proyecto**:
```powershell
cd "C:\Users\enman\Documents\POO_Duoc\2 semestre\pixel-and-bean-master"
powershell -ExecutionPolicy Bypass -File "tools\generar_pantallazos.ps1"
```

### El PDF está en blanco o con errores
- Verifica que los archivos .java existan en las rutas correctas
- Revisa la consola para ver qué archivos no se encontraron
- El archivo `mapping.txt` te mostrará qué se generó correctamente

---

## 📝 Qué Entregar

Para la entrega de la Clase 3 necesitas:

1. ✅ **Link del commit en GitHub** (ya está hecho)
2. ✅ **`CLASE3_PANTALLAZOS.pdf`** - 1 pantallazo por archivo nuevo (13 páginas)
3. ⚠️ **Pantallazos de ejecución** (debes tomarlos manualmente):
   - Login exitoso
   - Panel Usuarios (CRUD)
   - Panel Productos (filtros)
   - Panel Ventas (registro)
   - Panel Reportes (períodos)

---

## ✨ Ventajas de Este Script

- ✅ **Rápido**: Genera 13 capturas + PDF en segundos
- ✅ **Profesional**: Resaltado de sintaxis consistente
- ✅ **Organizado**: Nombres ordenados y mapping claro
- ✅ **Automático**: No necesitas capturar manualmente cada archivo
- ✅ **Portable**: El PDF se puede imprimir o compartir fácilmente

---

## 📞 Soporte

Si tienes problemas:
1. Revisa que estés en la raíz del proyecto
2. Verifica que Python 3.7+ esté instalado
3. Revisa los mensajes de error en la consola
4. Comprueba que los archivos .java existan en las rutas esperadas

---

**Autor**: Enmanuel Chirinos  
**Fecha**: 3 de diciembre de 2025  
**Clase**: 3 - Arquitectura MVC  
**Institución**: DuocUC

