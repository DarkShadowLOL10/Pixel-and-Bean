# Documento de Implementación - Clase 2 (Archivos Nuevos)

## Link GitHub al commit de la clase
**Repositorio:** https://github.com/DarkShadowLOL10/Pixel-and-Bean

**Commit principal (Clase 2):**
- Hash: `5acdb04a4717b43affd4042d14c81b04bc1404cf`
- Mensaje: "Clase 2: Alpha UI completa"
- Link directo: https://github.com/DarkShadowLOL10/Pixel-and-Bean/commit/5acdb04a4717b43affd4042d14c81b04bc1404cf

---

## Archivos Nuevos Creados en la Clase 2

### 1. LoginFrame.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/LoginFrame.java`

**Descripción:** Ventana de autenticación con validación mock. Incluye campos de usuario y contraseña, botón de login y manejo de credenciales de prueba.

**Captura de pantalla:**
![LoginFrame - Pantalla de inicio](../images/screenshots/inicio.png)

**Funcionalidades implementadas:**
- Validación de campos vacíos
- Autenticación mock (admin/1234, operador/op123)
- Determinación de rol
- Mensajes de error para credenciales incorrectas
- Enter en campo contraseña ejecuta login
- Limpieza de memoria de contraseña por seguridad

---

### 2. MainFrame.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/MainFrame.java`

**Descripción:** Ventana principal con menú completo, navegación CardLayout y barra de estado con información del usuario y hora actualizada.

**Captura de pantalla:**
![MainFrame - Bienvenida](../images/screenshots/menu.png)

**Funcionalidades implementadas:**
- CardLayout para navegación entre vistas
- Menú completo (Archivo, Gestión, Operación, Reportes, Eventos, Ayuda)
- Barra de estado con usuario, rol y fecha/hora en tiempo real
- Control de acceso por roles (Usuarios solo para ADMIN)
- Atajos de teclado (Ctrl+Q salir, Ctrl+V ventas, etc.)
- Diálogos de confirmación para cerrar sesión y salir
- Lazy initialization de paneles (se crean cuando se acceden)

---

### 3. UsuariosPanel.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/panels/UsuariosPanel.java`

**Descripción:** Panel CRUD completo para gestión de usuarios con tabla, formulario y búsqueda incremental.

**Captura de pantalla:**
![UsuariosPanel - Gestión](../images/screenshots/operadores.png)

**Funcionalidades implementadas:**
- Tabla con datos de usuarios (ID, Username, Nombre, Rol, Activo)
- Búsqueda incremental con DocumentListener
- Formulario: username, password, nombre, rol (combo), activo (checkbox)
- Botones: Nuevo, Guardar, Eliminar, Limpiar, Cancelar, Cambiar Estado
- Selección en tabla carga datos en formulario
- Doble clic en fila para editar
- Renderer personalizado para columna Activo (verde/rojo)
- AbstractTableModel personalizado (UsuariosTableModel interno)
- Validaciones: campos obligatorios

---

### 4. ProductosPanel.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/panels/ProductosPanel.java`

**Descripción:** Panel CRUD para productos con filtros por categoría y tipo dinámico según selección.

**Captura de pantalla:**
![ProductosPanel - Gestión](../images/screenshots/productos.png)

**Funcionalidades implementadas:**
- Tabla con productos (ID, Nombre, Categoría, Tipo, Precio, Estado)
- Búsqueda incremental en nombre
- Filtro por categoría (TODAS, BEBIDA, SNACK, TIEMPO)
- Tipo dinámico: al cambiar categoría se actualizan opciones de tipo
  - BEBIDA → CAFE, TE, OTRA
  - SNACK → POSTRE, SALADO
  - TIEMPO → ARCADE, VR
- Formulario: nombre, categoría, tipo, precio
- Botones: Nuevo, Guardar, Eliminar, Limpiar, Cambiar Estado
- Renderer para precios (formato $X,XXX) y estado (Activo/Inactivo)
- AbstractTableModel personalizado (ProductosTableModel)
- Validaciones: nombre obligatorio, precio > 0

---

### 5. VentasPanel.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/panels/VentasPanel.java`

**Descripción:** Panel para registro de ventas con detalle de productos, cantidades, cálculo de total y gestión de ventas (confirmar/anular).

**Captura de pantalla:**
![VentasPanel - Registro](../images/screenshots/ventas.png)

**Funcionalidades implementadas:**
- Tabla superior: lista de ventas existentes (ID, Fecha, Usuario, Total, Estado)
- Tabla izquierda inferior: catálogo de productos disponibles
- Tabla derecha inferior: detalle de la venta actual (items con cantidad)
- Spinner para cantidad de producto
- Botones: Agregar Producto, Confirmar Venta, Anular Venta, Quitar Seleccionado
- Cálculo dinámico del total
- ItemVenta para manejar cantidad y subtotal por producto
- Actualización automática del total al agregar/quitar items
- Validaciones: cantidad >= 1, al menos 1 item para confirmar
- Anulación de ventas existentes
- Limpieza de detalle tras confirmar

---

### 6. ReportesPanel.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/panels/ReportesPanel.java`

**Descripción:** Panel de reportes con filtros por periodo y visualización de top productos.

**Captura de pantalla:**
![ReportesPanel - Ventas](../images/screenshots/ventas_dia.png)

**Funcionalidades implementadas:**
- Combo de periodos: Hoy, Ayer, Última Semana, Último Mes
- Botón Refrescar para actualizar datos
- Tabla de ventas del periodo (ID, Fecha, Usuario, Total)
- Tabla de top productos por precio (Nombre, Precio)
- Cálculo automático de total del periodo
- Label inferior mostrando total: "Total periodo: $X,XXX"
- Filtrado por rango de fechas según periodo seleccionado
- Ordenamiento de productos por precio descendente

---

### 7. EventosPanel.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/gui/panels/EventosPanel.java`

**Descripción:** Panel placeholder para futura gestión de eventos/torneos.

**Captura de pantalla:**
![EventosPanel - Placeholder](../images/screenshots/torneo.png)

**Funcionalidades implementadas:**
- Mensaje informativo: "Módulo de Torneos es un placeholder"
- Diálogo JOptionPane al acceder desde menú
- Preparado para implementación futura como trabajo autónomo

---

### 8. Usuario.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/model/Usuario.java`

**Descripción:** Clase de modelo para entidad Usuario.

**Atributos:**
- int id
- String username
- String password
- String nombreCompleto
- String rol (ADMIN, OPERADOR)
- boolean activo

**Métodos:** Constructor, getters, setters, toString

---

### 9. Producto.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/model/Producto.java`

**Descripción:** Clase de modelo para entidad Producto.

**Atributos:**
- int id
- String nombre
- String categoria (BEBIDA, SNACK, TIEMPO)
- String tipo (CAFE, POSTRE, ARCADE, etc.)
- double precio
- boolean activo

**Métodos:** Constructor, getters, setters, toString

---

### 10. Venta.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/model/Venta.java`

**Descripción:** Clase de modelo para entidad Venta.

**Atributos:**
- int id
- LocalDateTime fecha
- String usuario
- List<ItemVenta> items
- double total
- String estado (ACTIVA, ANULADA)

**Métodos:** Constructor, getters, setters, calcularTotal(), toString

---

### 11. ItemVenta.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/model/ItemVenta.java`

**Descripción:** Clase para representar un item dentro del detalle de una venta.

**Atributos:**
- Producto producto
- int cantidad
- double subtotal

**Métodos:** Constructor, getters, setters, calcularSubtotal(), toString

---

### 12. UsuarioService.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/service/UsuarioService.java`

**Descripción:** Interfaz de servicio para operaciones con usuarios.

**Métodos:**
- List<Usuario> listAll()
- Usuario findById(int id)
- List<Usuario> searchByUsername(String username)
- void add(Usuario usuario)
- void update(Usuario usuario)
- void deleteById(int id)
- void toggleActive(int id, boolean activo)
- Usuario authenticate(String username, String password)

---

### 13. ProductoService.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/service/ProductoService.java`

**Descripción:** Interfaz de servicio para operaciones con productos.

**Métodos:**
- List<Producto> listAll()
- List<Producto> listActive()
- Producto findById(int id)
- List<Producto> searchByName(String name)
- List<Producto> filterByCategory(String categoria)
- void add(Producto producto)
- void update(Producto producto)
- void deleteById(int id)
- void toggleActive(int id, boolean activo)

---

### 14. VentaService.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/service/VentaService.java`

**Descripción:** Interfaz de servicio para operaciones con ventas.

**Métodos:**
- List<Venta> listAll()
- List<Venta> listByDateRange(LocalDateTime inicio, LocalDateTime fin)
- List<Venta> listToday()
- Venta findById(int id)
- void add(Venta venta)
- void update(Venta venta)
- void cancelById(int id)
- double totalByDateRange(LocalDateTime inicio, LocalDateTime fin)

---

### 15. UsuarioServiceStub.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/service/impl/UsuarioServiceStub.java`

**Descripción:** Implementación stub del servicio de usuarios con datos en memoria.

**Datos iniciales:**
- admin (id=1, ADMIN, activo)
- operador (id=2, OPERADOR, activo)

**Funcionalidades:**
- Operaciones CRUD completas en lista local
- Autenticación por username y password
- Búsqueda por username (contiene texto)
- Toggle de estado activo/inactivo

---

### 16. ProductoServiceStub.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/service/impl/ProductoServiceStub.java`

**Descripción:** Implementación stub del servicio de productos con datos en memoria.

**Datos iniciales:**
- Espresso (BEBIDA, CAFE, $2,500)
- Cappuccino (BEBIDA, CAFE, $3,000)
- Brownie (SNACK, POSTRE, $2,000)
- 15 minutos (TIEMPO, ARCADE, $1,500)
- 30 minutos (TIEMPO, ARCADE, $2,500)

**Funcionalidades:**
- CRUD completo en lista local
- Filtro por categoría
- Búsqueda por nombre (contiene texto)
- Toggle de estado activo/inactivo
- Listado de solo productos activos

---

### 17. VentaServiceStub.java
**Ubicación:** `src/cl/enmanuelchirinos/pnb/service/impl/VentaServiceStub.java`

**Descripción:** Implementación stub del servicio de ventas con datos en memoria.

**Datos iniciales:**
- 3 ventas de ejemplo con diferentes usuarios y estados

**Funcionalidades:**
- CRUD completo en lista local
- Filtro por rango de fechas
- Listado de ventas del día actual
- Cálculo de total por periodo
- Anulación de ventas (cambio de estado)

---

### 18. .gitignore
**Ubicación:** `.gitignore`

**Descripción:** Archivo para excluir archivos temporales y compilados del control de versiones.

**Contenido:**
- Archivos .class compilados
- Directorios out/, dist/, build/, target/
- Configuración IDE (.idea/, *.iml)
- Logs y temporales
- Archivos del sistema operativo

---

### 19. README.md (actualizado)
**Ubicación:** `README.md`

**Descripción:** Documentación completa del proyecto con toda la información de la Clase 2.

**Secciones agregadas:**
- Descripción general breve
- Funcionalidad futura
- Prompt Jumpstart para Copilot
- Alcance de Clase 2
- Entregables
- Cómo ejecutar
- Flujo básico de uso
- Capturas de pantalla
- Arquitectura actual
- Estructura de carpetas
- Qué es un servicio stub
- Validaciones incluidas
- Checklist completo
- Próximos pasos
- Glosario rápido
- Instrucciones Git/GitHub
- Hash del commit

---

### 20. CLASE2-EVIDENCIAS.md
**Ubicación:** `docs/entregas/CLASE2-EVIDENCIAS.md`

**Descripción:** Documento con referencias a todas las capturas de pantalla organizadas por módulo.

**Capturas referenciadas:** Login, MainFrame, Usuarios, Productos, Ventas, Reportes, Eventos, Diálogos

---

## Resumen de Archivos Nuevos
- **GUI:** 6 clases (LoginFrame, MainFrame, 5 paneles)
- **Modelo:** 4 clases (Usuario, Producto, Venta, ItemVenta)
- **Servicios:** 3 interfaces + 3 implementaciones stub
- **Configuración:** .gitignore
- **Documentación:** README.md actualizado, CLASE2-EVIDENCIAS.md

**Total:** 17 archivos Java nuevos + 3 archivos de configuración/documentación

---

## Verificación en GitHub
Todos los archivos están disponibles en:
https://github.com/DarkShadowLOL10/Pixel-and-Bean

Commit principal: 5acdb04a4717b43affd4042d14c81b04bc1404cf

