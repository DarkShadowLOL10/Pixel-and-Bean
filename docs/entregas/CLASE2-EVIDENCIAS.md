# Evidencias Clase 2 - Pixel & Bean (Ejecución)

## Link GitHub al commit
**Repositorio:** https://github.com/DarkShadowLOL10/Pixel-and-Bean

**Commit:** `5acdb04a4717b43affd4042d14c81b04bc1404cf`

**Link directo:** https://github.com/DarkShadowLOL10/Pixel-and-Bean/commit/5acdb04a4717b43affd4042d14c81b04bc1404cf

---

## Capturas de Ejecución (Una por ventana y funcionalidad)

## 1. Login
**Funcionalidad:** Autenticación con credenciales válidas

![Login - Pantalla de inicio](images/screenshots/inicio.png)

**Descripción:** Ventana de login mostrando campos de usuario y contraseña con credenciales de prueba visibles. Al ingresar admin/1234 o operador/op123 se accede al sistema.

---

## 2. MainFrame - Bienvenida
**Funcionalidad:** Pantalla principal tras login exitoso

![MainFrame - Bienvenida](images/screenshots/menu.png)

**Descripción:** Pantalla de bienvenida mostrando mensaje, información del usuario logueado (nombre y rol), menú completo en la parte superior y barra de estado inferior con fecha/hora actualizada en tiempo real.

---

## 3. Gestión de Usuarios
**Funcionalidad:** CRUD completo de usuarios con búsqueda

![Usuarios - Panel de gestión](images/screenshots/operadores.png)

**Descripción:** Panel con tabla de usuarios (admin y operador visibles), formulario a la derecha con campos Username, Password, Nombre, Rol (combo) y Activo (checkbox). Botones: Nuevo, Guardar, Eliminar, Limpiar, Cancelar, Cambiar Estado. Campo de búsqueda en la parte superior con filtrado incremental.

---

## 4. Gestión de Productos
**Funcionalidad:** CRUD de productos con filtros y tipo dinámico

![Productos - Panel de gestión](images/screenshots/productos.png)

**Descripción:** Panel mostrando tabla de productos (Espresso, Cappuccino, Brownie, 15 minutos, 30 minutos). Filtro por categoría (combo TODAS/BEBIDA/SNACK/TIEMPO). Formulario con Nombre, Categoría (combo), Tipo (combo que cambia según categoría seleccionada) y Precio. Producto "Brownie" seleccionado mostrando categoría SNACK y tipo POSTRE.

---

## 5. Registro de Ventas
**Funcionalidad:** Crear ventas agregando productos con cantidades

![Ventas - Panel de registro](images/screenshots/ventas.png)

**Descripción:** Panel dividido en 3 secciones:
- Superior: Tabla de ventas existentes (3 ventas: 2 activas, 1 anulada) con ID, Fecha, Usuario, Total y Estado
- Inferior izquierda: Catálogo de productos disponibles con precios
- Inferior derecha: Detalle de venta actual (vacío en captura)
- Controles: spinner de cantidad, botones Agregar Producto, Confirmar Venta, Anular Venta, Quitar Seleccionado
- Label de total dinámico en la parte inferior

---

## 6. Reportes de Ventas
**Funcionalidad:** Consulta de ventas por periodo con top productos

![Reportes - Ventas del día](images/screenshots/ventas_dia.png)

**Descripción:** Panel con combo de periodos (Hoy seleccionado), botón Refrescar. Tabla superior mostrando 2 ventas del día con totales $7,500 y $4,500. Tabla derecha "Top Productos (Precio)" mostrando los 5 productos ordenados por precio descendente (Cappuccino $3,000, Espresso $2,500, 30 minutos $2,500, Brownie $2,000, 15 minutos $1,500). Total del periodo: $12,000 mostrado en la parte inferior.

---

## 7. Eventos (Placeholder)
**Funcionalidad:** Módulo placeholder para futura implementación

![Eventos - Diálogo informativo](images/screenshots/torneo.png)

**Descripción:** Diálogo JOptionPane con ícono de información mostrando mensaje: "El módulo de Torneos es un placeholder. Esta funcionalidad se implementará como trabajo autónomo." con botón OK.

---

## 8. Acerca de
**Funcionalidad:** Información de la aplicación

![Acerca de - Información](images/screenshots/ayuda.png)

**Descripción:** Diálogo mostrando:
- Título: "Pixel & Bean - Sistema de Gestión v1.0"
- Desarrollador: Enmanuel Chirinos
- Proyecto: Café-Arcade Management System
- Tecnologías: Java 17, Swing GUI, MySQL + JDBC, Patrón MVC
- Copyright: © 2024 - Todos los derechos reservados

---

## 9. Proceso Git y GitHub
**Funcionalidad:** Commit y push al repositorio

![Git - Commit realizado](images/screenshots/commint.png)

**Descripción:** Captura mostrando terminal con comandos git exitosos: inicialización del repositorio, configuración de usuario, add de archivos, commit con mensaje "Clase 2: Alpha UI completa" y obtención del hash del commit.

![GitHub - Repositorio](images/screenshots/git_hub.png)

**Descripción:** Captura del repositorio en GitHub mostrando todos los archivos subidos correctamente en la rama main.

---

## Resumen de Funcionalidades Demostradas

### Login y Seguridad
- ✓ Validación de credenciales
- ✓ Mensajes de error claros
- ✓ Determinación de roles (ADMIN/OPERADOR)
- ✓ Limpieza de contraseña en memoria

### Navegación
- ✓ Menú completo funcional
- ✓ CardLayout para cambio de vistas
- ✓ Atajos de teclado
- ✓ Barra de estado actualizada en tiempo real

### Gestión de Usuarios
- ✓ Listado en tabla
- ✓ Búsqueda incremental
- ✓ Crear, editar, eliminar
- ✓ Activar/desactivar usuarios
- ✓ Selección en tabla carga formulario

### Gestión de Productos
- ✓ Listado con categorías y tipos
- ✓ Filtro por categoría
- ✓ Tipo dinámico según categoría
- ✓ CRUD completo
- ✓ Formato de precios
- ✓ Activar/desactivar productos

### Ventas
- ✓ Lista de ventas existentes
- ✓ Catálogo de productos
- ✓ Detalle con cantidades
- ✓ Cálculo automático de total
- ✓ Confirmar y anular ventas
- ✓ Manejo de ItemVenta

### Reportes
- ✓ Filtros por periodo (Hoy, Ayer, Semana, Mes)
- ✓ Tabla de ventas del periodo
- ✓ Top productos por precio
- ✓ Total del periodo calculado
- ✓ Botón refrescar

### Diálogos
- ✓ Confirmación de salida
- ✓ Acerca de con información completa
- ✓ Placeholder de eventos

### Control de Versiones
- ✓ Git inicializado
- ✓ Commit creado con hash
- ✓ Push exitoso a GitHub
- ✓ Todos los archivos en repositorio remoto

---

## Notas Técnicas Implementadas

1. **Servicios Stub:** Datos en memoria sin persistencia real
2. **AbstractTableModel:** Modelos personalizados para cada tabla
3. **DocumentListener:** Búsqueda incremental mientras se escribe
4. **Renderers:** Formato de precios, estados (activo/inactivo), fechas
5. **CardLayout:** Sistema de navegación central
6. **Timer:** Actualización automática de hora en barra de estado
7. **Validaciones:** Campos obligatorios, precios > 0, cantidades >= 1
8. **Control de acceso:** Usuarios restringido a rol ADMIN
9. **Lazy initialization:** Paneles se crean al accederlos por primera vez
10. **Atajos de teclado:** Ctrl+Q, Ctrl+V, Ctrl+R, Ctrl+Shift+L

---

## Verificación en GitHub
✓ Repositorio: https://github.com/DarkShadowLOL10/Pixel-and-Bean
✓ Commit: 5acdb04a4717b43affd4042d14c81b04bc1404cf
✓ 35 archivos subidos
✓ Documentación completa
✓ Capturas incluidas

---

**Entrega Clase 2 completada exitosamente**
