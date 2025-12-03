# Clase 3 - Archivos Nuevos Implementados

## 📋 Listado de Archivos Nuevos para Screenshots

A continuación se lista cada archivo nuevo creado en la implementación de la arquitectura MVC. Se debe tomar un pantallazo de cada uno mostrando el código completo.

---

## 1. Interfaces de Repositorios

### 1.1 IUsuarioRepository.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/repository/IUsuarioRepository.java`

**Descripción**: Interfaz que define el contrato para operaciones de acceso a datos de Usuario.

**Métodos**:
- `Usuario buscarPorId(int id)`
- `Usuario buscarPorUsername(String username)`
- `List<Usuario> listarTodos()`
- `List<Usuario> listarPorRol(String rol)`
- `int guardar(Usuario usuario)`
- `void actualizar(Usuario usuario)`
- `void eliminar(int id)`
- `boolean existeUsername(String username)`
- `int contarActivosPorRol(String rol)`

---

### 1.2 IProductoRepository.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/repository/IProductoRepository.java`

**Descripción**: Interfaz que define el contrato para operaciones de acceso a datos de Producto.

**Métodos**:
- `Producto buscarPorId(int id)`
- `List<Producto> listarTodos()`
- `List<Producto> listarActivos()`
- `List<Producto> listarPorCategoria(String categoria)`
- `List<Producto> buscarPorNombre(String nombre)`
- `int guardar(Producto producto)`
- `void actualizar(Producto producto)`
- `void eliminar(int id)`
- `void cambiarEstado(int id, boolean activo)`

---

### 1.3 IVentaRepository.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/repository/IVentaRepository.java`

**Descripción**: Interfaz que define el contrato para operaciones de acceso a datos de Venta.

**Métodos**:
- `Venta buscarPorId(int id)`
- `List<Venta> listarTodas()`
- `List<Venta> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta)`
- `List<Venta> listarDelDia()`
- `List<Venta> listarPorUsuario(int usuarioId)`
- `int guardar(Venta venta)`
- `void anular(int id)`
- `double calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta)`
- `double calcularTotalDelDia()`

---

## 2. Implementaciones Mock de Repositorios

### 2.1 UsuarioRepositoryMock.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/repository/mock/UsuarioRepositoryMock.java`

**Descripción**: Implementación en memoria del repositorio de Usuario. Contiene datos de prueba precargados.

**Datos iniciales**:
- admin / admin123 (ADMIN)
- operador1 / op123 (OPERADOR)
- operador2 / op456 (OPERADOR)
- cajero / caj123 (OPERADOR, inactivo)

---

### 2.2 ProductoRepositoryMock.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/repository/mock/ProductoRepositoryMock.java`

**Descripción**: Implementación en memoria del repositorio de Producto. Contiene catálogo de productos precargados.

**Categorías de productos**:
- BEBIDA: Espresso, Cappuccino, Latte, Americano, Coca-Cola, Sprite
- SNACK: Brownie, Cheesecake, Papas Fritas, Nachos
- TIEMPO: 15 minutos, 30 minutos, 1 hora, 2 horas

---

### 2.3 VentaRepositoryMock.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/repository/mock/VentaRepositoryMock.java`

**Descripción**: Implementación en memoria del repositorio de Venta. Contiene ventas de ejemplo del día actual.

**Datos iniciales**: 6 ventas de ejemplo con diferentes estados (ACTIVA/ANULADA)

---

## 3. Capa de Servicios

### 3.1 UsuarioService.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/service/UsuarioService.java`

**Descripción**: Servicio que contiene toda la lógica de negocio relacionada con Usuario.

**Funcionalidades**:
- Autenticación de usuarios
- Validación de datos (username, password, rol)
- Verificación de username único
- Protección del último administrador activo
- Gestión de estados (activo/inactivo)

**Reglas de negocio**:
- Username mínimo 4 caracteres
- Password mínimo 6 caracteres
- Rol debe ser ADMIN u OPERADOR
- No se puede eliminar el último admin activo

---

### 3.2 ProductoService.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/service/ProductoService.java`

**Descripción**: Servicio que contiene toda la lógica de negocio relacionada con Producto.

**Funcionalidades**:
- CRUD completo de productos
- Validación de datos (nombre, categoría, tipo, precio)
- Gestión de estado activo/inactivo
- Búsquedas y filtros

**Reglas de negocio**:
- Precio debe ser mayor a 0
- Categoría debe ser BEBIDA, SNACK o TIEMPO
- Nombre no puede estar vacío

---

### 3.3 VentaService.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/service/VentaService.java`

**Descripción**: Servicio que contiene toda la lógica de negocio relacionada con Venta.

**Funcionalidades**:
- Registro de nuevas ventas
- Anulación de ventas
- Consultas por período
- Cálculos de totales

**Reglas de negocio**:
- Total debe ser mayor a 0
- No se puede anular una venta ya anulada
- Validación de rangos de fechas

---

## 4. Capa de Controladores

### 4.1 UsuarioController.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/controller/UsuarioController.java`

**Descripción**: Controlador que coordina las operaciones de Usuario entre la vista y el servicio.

**Métodos**:
- `autenticar(String username, String password)`
- `crearUsuario(String username, String password, String nombreCompleto, String rol)`
- `actualizarUsuario(int id, ...)`
- `eliminarUsuario(int id)`
- `listarTodos()`
- `listarActivos()`
- `buscar(String texto)`

---

### 4.2 ProductoController.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/controller/ProductoController.java`

**Descripción**: Controlador que coordina las operaciones de Producto entre la vista y el servicio.

**Métodos**:
- `crearProducto(String nombre, String categoria, String tipo, double precio)`
- `actualizarProducto(int id, ...)`
- `eliminarProducto(int id)`
- `cambiarEstadoProducto(int id)`
- `listarTodos()`
- `listarActivos()`
- `listarPorCategoria(String categoria)`
- `buscarPorNombre(String nombre)`

---

### 4.3 VentaController.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/controller/VentaController.java`

**Descripción**: Controlador que coordina las operaciones de Venta entre la vista y el servicio.

**Métodos**:
- `registrarVenta(int usuarioId, String usuarioNombre, double total)`
- `anularVenta(int id)`
- `listarTodas()`
- `listarDelDia()`
- `listarPorRango(LocalDateTime desde, LocalDateTime hasta)`
- `listarPorUsuario(int usuarioId)`
- `calcularTotalDelDia()`
- `calcularTotalPorRango(LocalDateTime desde, LocalDateTime hasta)`

---

## 5. Contenedor de Inversión de Control

### 5.1 ApplicationContext.java
**Ubicación**: `src/cl/enmanuelchirinos/pnb/app/ApplicationContext.java`

**Descripción**: Contenedor IoC que gestiona la creación e inyección de dependencias de toda la aplicación. Implementa el patrón Singleton.

**Responsabilidades**:
- Inicializar repositorios (Mock por ahora, JDBC en Clase 4)
- Crear servicios con sus dependencias inyectadas
- Crear controladores con sus dependencias inyectadas
- Proveer acceso centralizado a los controladores

**Métodos públicos**:
- `getInstance()` - Obtiene la instancia única (Singleton)
- `getUsuarioController()`
- `getProductoController()`
- `getVentaController()`
- `reset()` - Para testing

---

## 📸 Instrucciones para Screenshots

Para cada archivo listado arriba, tomar un pantallazo que muestre:

1. **Ruta completa del archivo** en el explorador de proyecto
2. **Código completo** visible en el editor
3. **Resaltado de sintaxis** activado
4. **Nombre del archivo** en la pestaña del editor

### Ejemplo de captura:
```
[Explorador de Proyecto]  |  [Editor de Código]
src/                      |  
  cl/                     |  package cl.enmanuelchirinos.pnb.repository;
    enmanuelchirinos/     |  
      pnb/                |  public interface IUsuarioRepository {
        repository/       |      Usuario buscarPorId(int id);
          ► IUsuarioRepository.java  ← (seleccionado)
```

---

## 📊 Total de Archivos Nuevos

- **3** Interfaces de Repositorio
- **3** Implementaciones Mock
- **3** Servicios
- **3** Controladores
- **1** ApplicationContext

**Total: 13 archivos nuevos**

---

## ✅ Checklist de Screenshots

- [ ] IUsuarioRepository.java
- [ ] IProductoRepository.java
- [ ] IVentaRepository.java
- [ ] UsuarioRepositoryMock.java
- [ ] ProductoRepositoryMock.java
- [ ] VentaRepositoryMock.java
- [ ] UsuarioService.java
- [ ] ProductoService.java
- [ ] VentaService.java
- [ ] UsuarioController.java
- [ ] ProductoController.java
- [ ] VentaController.java
- [ ] ApplicationContext.java

---

**Nota**: Estos pantallazos demuestran la implementación completa de la arquitectura MVC con Inyección de Dependencias según los patrones vistos en la Clase 3.

