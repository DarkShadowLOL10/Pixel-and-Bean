# Guía de Uso - Pixel & Bean

## 🚀 Inicio Rápido

### 1. Clonar el Repositorio
```bash
git clone https://github.com/DarkShadowLOL10/Pixel-and-Bean.git
cd Pixel-and-Bean
```

### 2. Ejecutar la Aplicación

#### En Linux/macOS:
```bash
./run.sh
```

#### En Windows:
```cmd
run.bat
```

### 3. Iniciar Sesión
- **Usuario**: admin
- **Contraseña**: admin123

## 📋 Guía Paso a Paso

### Escenario 1: Administrador Completo

#### A. Gestión de Usuarios

1. **Iniciar sesión como admin**
   - Usuario: admin
   - Contraseña: admin123

2. **Acceder a gestión de usuarios**
   - Menú → Usuarios → Gestionar Usuarios

3. **Crear nuevo usuario**
   - Clic en "Nuevo Usuario"
   - Llenar formulario:
     - Usuario: jose
     - Contraseña: jose123
     - Nombre Completo: José García
     - Rol: OPERADOR
     - Estado: ACTIVE
   - Clic en "Guardar"

4. **Editar usuario**
   - Seleccionar usuario en la tabla
   - Clic en "Editar"
   - Modificar datos necesarios
   - Guardar cambios

5. **Desactivar usuario**
   - Seleccionar usuario activo
   - Clic en "Desactivar"
   - Confirmar acción

6. **Activar usuario**
   - Seleccionar usuario inactivo
   - Clic en "Activar"

#### B. Gestión de Productos

1. **Acceder a gestión de productos**
   - Menú → Productos → Gestionar Productos

2. **Agregar nuevo producto**
   - Clic en "Nuevo Producto"
   - Llenar formulario:
     - Nombre: Mocha
     - Descripción: Café con chocolate
     - Precio: 50.00
     - Estado: ACTIVE
   - Clic en "Guardar"

3. **Editar producto**
   - Seleccionar producto
   - Clic en "Editar"
   - Modificar precio o descripción
   - Guardar

4. **Desactivar producto**
   - Seleccionar producto activo
   - Clic en "Desactivar"
   - Producto no aparecerá en ventas

### Escenario 2: Operador de Ventas

#### A. Registrar Venta

1. **Iniciar sesión como operador**
   - Usuario: operador
   - Contraseña: oper123

2. **Acceder a nueva venta**
   - Menú → Ventas → Nueva Venta

3. **Agregar productos**
   - Seleccionar "Café Americano" del dropdown
   - Cantidad: 2
   - Clic en "Agregar"
   - Seleccionar "Croissant"
   - Cantidad: 1
   - Clic en "Agregar"
   - Observar el total calculado automáticamente

4. **Quitar un item** (si es necesario)
   - Seleccionar item en la tabla
   - Clic en "Quitar Item"

5. **Completar venta**
   - Verificar el total
   - Clic en "Completar Venta"
   - Confirmar
   - Se muestra folio de venta generado

6. **Nueva venta**
   - El sistema limpia automáticamente
   - Repetir proceso para otra venta

### Escenario 3: Consultar Reportes

#### A. Ver Ventas del Día

1. **Acceder a reportes**
   - Menú → Reportes → Reporte de Ventas

2. **Filtrar por período**
   - Seleccionar "Hoy" en dropdown
   - Clic en "Actualizar"

3. **Ver resumen**
   - Observar "Total de Ventas"
   - Observar "Monto Total"

4. **Ver detalle de venta**
   - Doble clic en una venta de la tabla
   - Se abre ventana con detalles completos
   - Ver productos, cantidades, precios
   - Cerrar ventana

#### B. Ver Reportes Históricos

1. **Ventas de ayer**
   - Seleccionar "Ayer" en dropdown
   - Ver ventas del día anterior

2. **Ventas de la semana**
   - Seleccionar "Esta Semana"
   - Ver todas las ventas de lunes a hoy

3. **Ventas del mes**
   - Seleccionar "Este Mes"
   - Ver ventas desde el día 1 del mes

### Escenario 4: Cerrar Sesión

1. **Logout**
   - Menú → Sistema → Cerrar Sesión
   - Confirmar
   - Regresa a pantalla de login

## 💡 Consejos y Trucos

### Para Administradores

1. **Crea múltiples operadores**
   - Uno por turno de trabajo
   - Permite identificar quién registró cada venta

2. **Desactiva productos sin eliminarlos**
   - Productos de temporada
   - Productos agotados temporalmente
   - Mantiene historial en ventas anteriores

3. **Revisa reportes regularmente**
   - Identifica productos más vendidos
   - Analiza ventas por período

### Para Operadores

1. **Verifica el total antes de completar**
   - Usa la función "Quitar Item" si es necesario
   - El botón "Limpiar" reinicia toda la venta

2. **Doble check de cantidades**
   - El spinner permite 1-100 unidades
   - Cantidad incorrecta = total incorrecto

3. **Usa reportes para turnos**
   - Revisa tus ventas del día
   - Verifica montos antes de cerrar caja

## ❓ Preguntas Frecuentes

### ¿Los datos se guardan al cerrar?
No, los datos están en memoria volátil. Al cerrar la aplicación, todo vuelve al estado inicial.

### ¿Puedo cambiar mi contraseña?
Actualmente no. Necesitarías que un admin edite tu usuario.

### ¿Puedo eliminar usuarios o productos?
Actualmente solo se pueden desactivar, no eliminar. Esto mantiene integridad de datos.

### ¿Hay límite de ventas?
Solo el límite de memoria RAM disponible.

### ¿Puedo ver ventas de otros usuarios?
Sí, los reportes muestran todas las ventas, con el nombre del usuario que las registró.

### ¿Qué pasa si agrego mal un producto?
Usa "Quitar Item" para eliminarlo de la venta actual antes de completarla.

### ¿Puedo vender productos inactivos?
No, solo aparecen productos con estado ACTIVE en el selector de ventas.

### ¿Cómo sé el folio de mi venta?
Se muestra en el mensaje de confirmación después de completar la venta.

## 🎯 Casos de Uso Típicos

### Caso 1: Cliente pide café y muffin
1. Nueva Venta
2. Agregar "Café Latte" x1
3. Agregar "Muffin" x1
4. Total: $75.00
5. Completar Venta

### Caso 2: Cliente compra fichas arcade
1. Nueva Venta
2. Agregar "25 Fichas Arcade" x1
3. Total: $100.00
4. Completar Venta

### Caso 3: Pedido grupal
1. Nueva Venta
2. Agregar "Café Americano" x4
3. Agregar "Croissant" x4
4. Agregar "10 Fichas Arcade" x2
5. Total: $340.00
6. Completar Venta

### Caso 4: Error en pedido
1. Nueva Venta
2. Agregar "Espresso" x1
3. Cliente cambia de opinión
4. Seleccionar item en tabla
5. Quitar Item
6. Agregar "Cappuccino" x1
7. Completar Venta

### Caso 5: Reporte de cierre de caja
1. Al final del turno
2. Ir a Reportes → Reporte de Ventas
3. Seleccionar "Hoy"
4. Verificar "Monto Total"
5. Contar efectivo en caja
6. Debe coincidir con el monto total

## 📱 Próximas Funcionalidades

Estas funcionalidades están planificadas para futuras versiones:

- ✨ Descuentos y promociones
- 💳 Múltiples formas de pago
- 📊 Gráficas de ventas
- 📧 Envío de reportes por email
- 🏢 Gestión de inventario
- 👥 Gestión de clientes
- 🎮 Reservaciones de juegos arcade
- 🌐 Interfaz web

## 🆘 Soporte

Para reportar problemas o sugerir mejoras:
1. Ir al repositorio en GitHub
2. Crear un nuevo Issue
3. Describir el problema o sugerencia

---

**Pixel & Bean** - Sistema de gestión para café-arcade
Versión 1.0 - Prototipo educativo
