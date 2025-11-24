# Pixel & Bean - Sistema de Gestión Café-Arcade

Pixel & Bean es un sistema completo de gestión para café-arcade desarrollado en Java Swing. El sistema permite gestionar usuarios, productos, ventas y generar reportes, todo con almacenamiento en memoria mediante servicios stub.

## 🚀 Inicio Rápido

```bash
# Ejecutar aplicación (Linux/macOS)
./run.sh

# Ejecutar aplicación (Windows)
run.bat

# Ejecutar tests
./test.sh
```

**Login**: `admin` / `admin123`

## 📋 Características Principales

### ✅ Sistema de Autenticación
- Login con roles: **ADMIN** y **OPERADOR**
- Control de acceso basado en roles
- Usuarios de prueba incluidos

### ✅ Gestión de Usuarios (Solo ADMIN)
- Crear, editar usuarios
- Activar/desactivar usuarios
- Roles: Administrador y Operador
- Validación de nombres de usuario únicos

### ✅ Gestión de Productos (Solo ADMIN)
- Crear, editar productos
- Activar/desactivar productos
- Catálogo predefinido: cafés, alimentos y fichas arcade
- Control de precios

### ✅ Registro de Ventas
- Interfaz intuitiva para registrar ventas
- Selección de productos y cantidades
- Cálculo automático de totales
- Generación de folios únicos
- Disponible para todos los usuarios autenticados

### ✅ Reportes de Ventas
- Filtros por período:
  - **Hoy**: Ventas del día actual
  - **Ayer**: Ventas del día anterior
  - **Esta Semana**: Ventas de la semana actual
  - **Este Mes**: Ventas del mes actual
- Vista detallada de cada venta
- Resumen con totales de ventas y montos

## Estructura del Proyecto

```
src/main/java/com/pixelbean/
├── models/          # Modelos de dominio
│   ├── Role.java           # Enum de roles (ADMIN, OPERADOR)
│   ├── Status.java         # Enum de estados (ACTIVE, INACTIVE)
│   ├── User.java           # Modelo de usuario
│   ├── Product.java        # Modelo de producto
│   ├── Sale.java           # Modelo de venta
│   └── SaleItem.java       # Item de venta
├── services/        # Servicios stub (datos en memoria)
│   ├── UserService.java    # Gestión de usuarios
│   ├── ProductService.java # Gestión de productos
│   └── SaleService.java    # Gestión de ventas
└── ui/              # Interfaz gráfica Swing
    ├── LoginFrame.java              # Ventana de login
    ├── MainFrame.java               # Ventana principal
    ├── UserManagementPanel.java     # Panel gestión usuarios
    ├── UserDialog.java              # Diálogo usuario
    ├── ProductManagementPanel.java  # Panel gestión productos
    ├── ProductDialog.java           # Diálogo producto
    ├── SalePanel.java               # Panel registro ventas
    └── ReportPanel.java             # Panel de reportes
```

## Requisitos

- Java 17 o superior
- Sistema operativo: Windows, Linux o macOS

## Instalación y Ejecución

### Opción 1: Usar el script de ejecución (Linux/macOS)

```bash
./run.sh
```

### Opción 2: Compilar y ejecutar manualmente

```bash
# Compilar
mkdir -p bin
javac -d bin -sourcepath src/main/java src/main/java/com/pixelbean/ui/LoginFrame.java

# Ejecutar
java -cp bin com.pixelbean.ui.LoginFrame
```

## Usuarios de Prueba

El sistema incluye usuarios predefinidos para pruebas:

| Usuario    | Contraseña | Rol       | Permisos                                    |
|-----------|-----------|-----------|---------------------------------------------|
| admin     | admin123  | ADMIN     | Acceso completo (usuarios, productos, ventas, reportes) |
| operador  | oper123   | OPERADOR  | Ventas y reportes solamente                |

## Datos Iniciales

### Productos Predefinidos

**Cafés:**
- Café Americano - $35.00
- Café Latte - $45.00
- Cappuccino - $45.00
- Espresso - $30.00

**Alimentos:**
- Croissant - $25.00
- Sandwich - $55.00
- Muffin - $30.00

**Fichas Arcade:**
- 10 Fichas - $50.00
- 25 Fichas - $100.00
- 50 Fichas - $180.00

## Uso del Sistema

### 1. Login
- Inicie sesión con uno de los usuarios de prueba
- El sistema validará credenciales y roles

### 2. Gestión de Usuarios (Solo ADMIN)
- Menú: **Usuarios → Gestionar Usuarios**
- Crear nuevos usuarios con roles específicos
- Editar información de usuarios existentes
- Activar/desactivar usuarios

### 3. Gestión de Productos (Solo ADMIN)
- Menú: **Productos → Gestionar Productos**
- Agregar nuevos productos al catálogo
- Editar precios y descripciones
- Activar/desactivar productos

### 4. Registro de Ventas
- Menú: **Ventas → Nueva Venta**
- Seleccionar productos y cantidades
- El total se calcula automáticamente
- Completar venta para generar registro

### 5. Reportes
- Menú: **Reportes → Reporte de Ventas**
- Seleccionar período (Hoy, Ayer, Semana, Mes)
- Ver resumen de ventas y totales
- Doble clic en una venta para ver detalles

## Características Técnicas

- **Arquitectura**: Servicios stub con almacenamiento en-memoria
- **Patrón Singleton**: Servicios únicos por instancia
- **Gestión de Estado**: Control de sesión de usuario
- **Validaciones**: Campos obligatorios, precios positivos, usuarios únicos
- **UI Responsiva**: Interfaz Swing con componentes estándar
- **Cálculos Automáticos**: Totales de ventas calculados dinámicamente

## Próximos Pasos (Roadmap)

- [ ] Migrar a arquitectura MVC completa
- [ ] Implementar persistencia con JDBC
- [ ] Sistema de inventario con control de stock
- [ ] Módulo de promociones y descuentos
- [ ] Reportes avanzados con gráficos
- [ ] Sistema de eventos y reservaciones arcade
- [ ] Más roles y permisos granulares
- [ ] Interfaz web complementaria

## Almacenamiento de Datos

⚠️ **Importante**: Los datos se almacenan **solo en memoria**. Al cerrar la aplicación, todos los cambios se pierden y los datos vuelven a su estado inicial.

## Contribución

Este es un prototipo educativo. Para mejoras o sugerencias, contacte al equipo de desarrollo.

## Licencia

Proyecto educativo - Pixel & Bean Café-Arcade
