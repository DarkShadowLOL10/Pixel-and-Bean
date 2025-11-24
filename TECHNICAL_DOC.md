# Documentación Técnica - Pixel & Bean

## Arquitectura del Sistema

### Patrón de Capas

El sistema está organizado en tres capas principales:

1. **Capa de Modelos (models)**: Entidades de dominio
2. **Capa de Servicios (services)**: Lógica de negocio y gestión de datos
3. **Capa de Presentación (ui)**: Interfaz gráfica Swing

### Diagramas de Clases

#### Capa de Modelos

```
Role (enum)
├── ADMIN
└── OPERADOR

Status (enum)
├── ACTIVE
└── INACTIVE

User
├── id: Long
├── username: String
├── password: String
├── fullName: String
├── role: Role
└── status: Status

Product
├── id: Long
├── name: String
├── description: String
├── price: double
└── status: Status

SaleItem
├── product: Product
├── quantity: int
├── unitPrice: double
└── getSubtotal(): double

Sale
├── id: Long
├── date: LocalDateTime
├── user: User
├── items: List<SaleItem>
├── total: double
├── addItem(SaleItem)
├── removeItem(SaleItem)
└── calculateTotal()
```

#### Capa de Servicios (Patrón Singleton)

```
UserService (Singleton)
├── authenticate(username, password): User
├── findAll(): List<User>
├── findById(id): User
├── save(user): User
├── activate(id): void
├── deactivate(id): void
└── usernameExists(username, excludeId): boolean

ProductService (Singleton)
├── findAll(): List<Product>
├── findActiveProducts(): List<Product>
├── findById(id): Product
├── save(product): Product
├── activate(id): void
└── deactivate(id): void

SaleService (Singleton)
├── findAll(): List<Sale>
├── findById(id): Sale
├── save(sale): Sale
├── findToday(): List<Sale>
├── findYesterday(): List<Sale>
├── findThisWeek(): List<Sale>
├── findThisMonth(): List<Sale>
└── calculateTotal(sales): double
```

#### Capa de Presentación

```
LoginFrame (JFrame)
└── Ventana principal de autenticación

MainFrame (JFrame)
├── Menú principal
├── Barra de navegación
└── Panel de contenido dinámico

UserManagementPanel (JPanel)
└── Gestión completa de usuarios

ProductManagementPanel (JPanel)
└── Gestión completa de productos

SalePanel (JPanel)
└── Registro de nuevas ventas

ReportPanel (JPanel)
└── Reportes con filtros de fecha

UserDialog (JDialog)
└── Formulario de usuario

ProductDialog (JDialog)
└── Formulario de producto
```

## Flujo de Datos

### 1. Autenticación
```
Usuario ingresa credenciales
    ↓
LoginFrame captura datos
    ↓
UserService.authenticate()
    ↓
Validación de credenciales
    ↓
Si válido: MainFrame
Si inválido: Error message
```

### 2. Registro de Venta
```
Usuario selecciona productos
    ↓
SalePanel agrega items
    ↓
Cálculo automático de totales
    ↓
Usuario confirma venta
    ↓
SaleService.save()
    ↓
Venta registrada en memoria
```

### 3. Generación de Reportes
```
Usuario selecciona período
    ↓
ReportPanel solicita datos
    ↓
SaleService filtra por fechas
    ↓
Cálculo de totales
    ↓
Presentación en tabla
```

## Gestión de Datos en Memoria

### UserService
- **HashMap<Long, User>**: Almacena usuarios indexados por ID
- **Datos iniciales**: 2 usuarios (admin, operador)
- **ID autoincremental**: Asignación automática de IDs

### ProductService
- **HashMap<Long, Product>**: Almacena productos indexados por ID
- **Datos iniciales**: 10 productos predefinidos
- **Categorías**: Cafés, alimentos, fichas arcade

### SaleService
- **HashMap<Long, Sale>**: Almacena ventas indexadas por ID
- **Datos iniciales**: Ninguno (se crean durante el uso)
- **Filtrado**: Por rangos de fechas usando LocalDateTime

## Control de Acceso por Roles

### ADMIN
- ✅ Gestión de usuarios
- ✅ Gestión de productos
- ✅ Registro de ventas
- ✅ Reportes

### OPERADOR
- ❌ Gestión de usuarios
- ❌ Gestión de productos
- ✅ Registro de ventas
- ✅ Reportes

## Validaciones Implementadas

### Usuarios
- Username único (no duplicados)
- Todos los campos obligatorios
- Solo usuarios activos pueden autenticarse

### Productos
- Nombre y descripción obligatorios
- Precio debe ser positivo (> 0)
- Solo productos activos aparecen en ventas

### Ventas
- Debe tener al menos 1 item
- Cantidad mínima: 1
- Cantidad máxima: 100
- Cálculo automático de totales

## Características de Seguridad

1. **Almacenamiento de Contraseñas**: En texto plano (solo para prototipo)
2. **Sesión de Usuario**: Mantenida en UserService
3. **Validación de Roles**: En cada acción de UI
4. **Logout Seguro**: Limpia sesión actual

⚠️ **Nota de Seguridad**: Este es un prototipo educativo. En producción se debe:
- Hash de contraseñas (bcrypt, argon2)
- Tokens de sesión
- Auditoría de acciones
- Cifrado de datos sensibles

## Manejo de Excepciones

El sistema implementa manejo de excepciones en:
- Parsing de números (precios, cantidades)
- Validación de campos obligatorios
- Operaciones CRUD
- Mensajes de error amigables con JOptionPane

## Extensibilidad

### Para agregar nuevas funcionalidades:

1. **Nuevo modelo**: Crear clase en `models/`
2. **Nuevo servicio**: Crear singleton en `services/`
3. **Nueva UI**: Crear panel/dialog en `ui/`
4. **Integrar**: Agregar menú en MainFrame

### Ejemplo: Agregar "Clientes"

```java
// 1. Modelo
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
    // getters/setters
}

// 2. Servicio
public class CustomerService {
    private static CustomerService instance;
    private Map<Long, Customer> customers;
    // métodos CRUD
}

// 3. UI
public class CustomerManagementPanel extends JPanel {
    // tabla, botones, lógica
}

// 4. MainFrame
JMenu customersMenu = new JMenu("Clientes");
JMenuItem manageCustomersItem = new JMenuItem("Gestionar Clientes");
manageCustomersItem.addActionListener(e -> showCustomerManagement());
menuBar.add(customersMenu);
```

## Rendimiento

### Consideraciones Actuales
- **Datos en memoria**: Acceso O(1) por ID (HashMap)
- **Búsquedas**: O(n) cuando se filtra por atributos
- **Sin índices**: No hay optimización de consultas
- **Límite**: Memoria disponible de la JVM

### Optimizaciones Futuras
- Índices secundarios para búsquedas frecuentes
- Paginación de resultados
- Caché de consultas comunes
- Migración a base de datos

## Testing

### Testing Manual
1. Compilar: `./run.sh` o `run.bat`
2. Login con usuarios de prueba
3. Probar cada módulo
4. Verificar cálculos
5. Revisar reportes

### Testing Automatizado (Futuro)
- Unit tests para servicios
- Tests de integración
- Tests de UI con AssertJ Swing

## Troubleshooting

### Problema: No compila
**Solución**: Verificar Java 17+ instalado: `java -version`

### Problema: HeadlessException
**Solución**: Ejecutar en entorno con display gráfico

### Problema: Datos se pierden
**Explicación**: Normal, datos en memoria volátil

### Problema: No aparece producto en venta
**Solución**: Verificar que el producto esté ACTIVO

## Licencia y Contacto

Proyecto educativo - Pixel & Bean
Prototipo de gestión para café-arcade
