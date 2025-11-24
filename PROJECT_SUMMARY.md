# Pixel & Bean - Resumen del Proyecto

## 📋 Descripción General

**Pixel & Bean** es un sistema completo de gestión para café-arcade desarrollado en Java Swing con almacenamiento en memoria. El sistema permite gestionar usuarios, productos, ventas y generar reportes con filtros de fecha.

## ✅ Características Implementadas

### 1. Sistema de Autenticación
- ✅ Login con validación de credenciales
- ✅ Control de acceso basado en roles (ADMIN, OPERADOR)
- ✅ Logout seguro con confirmación
- ✅ Sesión de usuario mantenida en memoria

### 2. Gestión de Usuarios (Solo ADMIN)
- ✅ Listar todos los usuarios
- ✅ Crear nuevos usuarios
- ✅ Editar usuarios existentes
- ✅ Activar usuarios inactivos
- ✅ Desactivar usuarios activos
- ✅ Validación de nombres de usuario únicos
- ✅ Dos usuarios predefinidos (admin, operador)

### 3. Gestión de Productos (Solo ADMIN)
- ✅ Listar todos los productos
- ✅ Crear nuevos productos
- ✅ Editar productos existentes
- ✅ Activar productos inactivos
- ✅ Desactivar productos activos
- ✅ Validación de precios positivos
- ✅ 10 productos predefinidos (cafés, alimentos, fichas arcade)

### 4. Registro de Ventas
- ✅ Interfaz intuitiva para registrar ventas
- ✅ Selector de productos activos
- ✅ Control de cantidad (1-100 unidades)
- ✅ Agregar múltiples productos
- ✅ Quitar productos antes de completar
- ✅ Cálculo automático de subtotales
- ✅ Cálculo automático de total
- ✅ Confirmación de venta
- ✅ Generación de folio único
- ✅ Timestamp automático
- ✅ Asociación con usuario actual

### 5. Reportes de Ventas
- ✅ Filtro por período: Hoy
- ✅ Filtro por período: Ayer
- ✅ Filtro por período: Esta Semana
- ✅ Filtro por período: Este Mes
- ✅ Tabla con todas las ventas del período
- ✅ Resumen: Total de ventas
- ✅ Resumen: Monto total
- ✅ Vista detallada de cada venta (doble click)
- ✅ Detalles incluyen: productos, cantidades, precios

### 6. Interfaz de Usuario
- ✅ Look & Feel del sistema operativo
- ✅ Diseño responsivo
- ✅ Navegación por menús
- ✅ Paneles intercambiables
- ✅ Diálogos modales
- ✅ Mensajes de confirmación
- ✅ Validación de formularios
- ✅ Indicadores visuales (colores, fuentes)

## 🏗️ Estructura del Código

```
src/
├── main/java/com/pixelbean/
│   ├── models/              (6 archivos)
│   │   ├── Role.java
│   │   ├── Status.java
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── SaleItem.java
│   │   └── Sale.java
│   │
│   ├── services/            (3 archivos)
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   └── SaleService.java
│   │
│   └── ui/                  (8 archivos)
│       ├── LoginFrame.java
│       ├── MainFrame.java
│       ├── UserManagementPanel.java
│       ├── UserDialog.java
│       ├── ProductManagementPanel.java
│       ├── ProductDialog.java
│       ├── SalePanel.java
│       └── ReportPanel.java
│
└── test/java/com/pixelbean/test/
    └── BasicTest.java       (1 archivo)

Total: 18 archivos Java
```

## 📊 Estadísticas del Código

- **Archivos Java**: 18
- **Clases compiladas**: 27 (incluyendo inner classes)
- **Líneas de código**: ~2,500
- **Modelos de dominio**: 6
- **Servicios**: 3 (Singleton)
- **Componentes UI**: 8
- **Tests básicos**: 8

## 🧪 Tests Implementados

1. ✅ UserService authentication
2. ✅ UserService find all users
3. ✅ ProductService find active products
4. ✅ SaleService create sale
5. ✅ Sale automatic total calculation
6. ✅ User activation/deactivation
7. ✅ Product creation and retrieval
8. ✅ SaleService find today sales

**Resultado**: 8/8 tests passed (100%)

## 📦 Datos Iniciales

### Usuarios Predefinidos
1. **admin / admin123** (ADMIN)
2. **operador / oper123** (OPERADOR)

### Productos Predefinidos
**Cafés** (4):
- Café Americano - $35.00
- Café Latte - $45.00
- Cappuccino - $45.00
- Espresso - $30.00

**Alimentos** (3):
- Croissant - $25.00
- Sandwich - $55.00
- Muffin - $30.00

**Fichas Arcade** (3):
- 10 Fichas - $50.00
- 25 Fichas - $100.00
- 50 Fichas - $180.00

## 🚀 Cómo Ejecutar

### Compilar y Ejecutar
```bash
# Linux/macOS
./run.sh

# Windows
run.bat
```

### Ejecutar Tests
```bash
./test.sh
```

## 📚 Documentación

- **README.md**: Guía general y características
- **USER_GUIDE.md**: Guía detallada de uso con ejemplos
- **TECHNICAL_DOC.md**: Documentación técnica y arquitectura
- **PROJECT_SUMMARY.md**: Este archivo (resumen ejecutivo)

## 🎯 Requisitos Completados

Según el problem statement:

✅ **Login con roles (ADMIN, OPERADOR)**: Implementado completamente
✅ **Administración de usuarios**: CRUD completo con activación/desactivación
✅ **Administración de productos**: CRUD completo con activación/desactivación
✅ **Registro de ventas**: Con cálculo automático de totales
✅ **Reportes por rangos**: Hoy, ayer, semana, mes
✅ **Datos en memoria**: Servicios stub implementados

## 🔧 Patrones y Técnicas Utilizadas

1. **Singleton Pattern**: Para servicios (UserService, ProductService, SaleService)
2. **Layered Architecture**: Models → Services → UI
3. **Enums**: Para roles y estados
4. **Observer Pattern**: En componentes Swing (listeners)
5. **Validation**: En formularios y operaciones
6. **In-Memory Storage**: HashMap para almacenamiento
7. **Auto-increment IDs**: Simulación de base de datos

## 🔐 Seguridad

- Validación de credenciales
- Control de acceso por roles
- Validación de entrada en formularios
- Confirmación de operaciones destructivas
- Sesión de usuario controlada

⚠️ **Nota**: Prototipo educativo - contraseñas en texto plano

## 📈 Próximos Pasos Sugeridos

1. Migrar a arquitectura MVC completa
2. Implementar persistencia con JDBC
3. Hash de contraseñas (bcrypt)
4. Sistema de inventario con stock
5. Módulo de promociones
6. Reportes con gráficos (JFreeChart)
7. Exportación a PDF/Excel
8. Interfaz web (Spring Boot)
9. Testing automatizado (JUnit)
10. Logging (Log4j)

## 💻 Tecnologías

- **Lenguaje**: Java 17
- **Framework UI**: Java Swing
- **Almacenamiento**: In-memory (HashMap)
- **Patrón**: Singleton para servicios
- **Build**: javac (sin build system)

## 📝 Notas Importantes

1. **Datos volátiles**: Todo se pierde al cerrar la aplicación
2. **Sin persistencia**: No hay base de datos
3. **Thread-safe**: Services con synchronized getInstance()
4. **Validaciones**: En UI y servicios
5. **Pruebas**: Test manual y 8 tests automatizados

## 🏆 Logros

- ✅ Sistema completamente funcional
- ✅ Código limpio y organizado
- ✅ Documentación completa
- ✅ Tests pasando 100%
- ✅ Scripts de ejecución multiplataforma
- ✅ Interfaz intuitiva
- ✅ Datos de ejemplo incluidos

## 📞 Soporte

Para más información:
- Ver README.md para guía de inicio
- Ver USER_GUIDE.md para ejemplos de uso
- Ver TECHNICAL_DOC.md para detalles técnicos

---

**Pixel & Bean v1.0** - Sistema de Gestión para Café-Arcade
Prototipo educativo completo y funcional
