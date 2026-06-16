# Focuss App 

(Leer las notas finales)

Aplicación web de productividad basada en la técnica Pomodoro. Permite gestionar tareas, establecer metas y medir el tiempo de enfoque.

---

## Tecnologías

Se aplican las tecnologías solicitadas para el Avance 2.

| Capa       | Tecnología                          |
|------------|-------------------------------------|
| Frontend   | HTML5, Bootstrap 5, JavaScript------|
| Backend    | Spring Boot, Java 17, Maven   ------|

---

## Estructura del proyecto

```
Focuss/
├── docs/
├── scripts/
│   └── focuss_db.sql
├── app-frontend/
│   ├── index.html
│   ├── css/
│   │   └── styles.css
│   ├── js/
│   │   └── script.js
│   └── pages/
│       ├── login.html
│       ├── registro.html
│       ├── dashboard.html
│       ├── tareas.html
│       ├── timer.html
│       ├── estadisticas.html
│       ├── metas.html
│       ├── perfil.html
│       ├── configuracion.html
│       └── demo.html
│
├── app-backend/
│   └── focuss-backend/
│       └── src/main/java/com/focus/focuss_backend/
│           ├── model/
│           │   ├── Usuario.java
│           │   ├── Tarea.java
│           │   └── Meta.java
│           ├── repository/
│           │   ├── UsuarioRepository.java
│           │   ├── TareaRepository.java
│           │   └── MetaRepository.java
│           ├── service/
│           │   ├── UsuarioService.java
│           │   ├── TareaService.java
│           │   └── MetaService.java
│           └── controller/
│               ├── AuthController.java
│               ├── TareaController.java
│               └── MetaController.java
│
└── README.txt
```

## Cómo ejecutar el proyecto

### 1. Levantar el backend

1. Ejecutar el script scripts/focuss_db.sql en MySQL Workbench
2. Verificar que la base de datos focuss_db se creó correctamente

### 2. Levantar el backend

1. Abrir la carpeta `app-backend/focuss-backend` en IntelliJ IDEA
2. Ejecutar la clase `FocussBackendApplication.java`
3. El servidor estará disponible en `http://localhost:8080`

### 3. Abrir el frontend

1. Abrir la carpeta `app-frontend` en VSCode
2. Abrir `index.html` directamente en el navegador

> La base de datos y el backend debe estar corriendo antes de usar el frontend.

---

## Endpoints de la API

### Autenticación

| Método | Endpoint              | Descripción         |
|--------|-----------------------|---------------------|
| POST   | `/api/auth/login`     | Iniciar sesión      |
| POST   | `/api/auth/register`  | Registrar usuario   |

### Tareas

| Método | Endpoint                        | Descripción                  |
|--------|---------------------------------|------------------------------|
| GET    | `/api/tareas/usuario/{id}`      | Obtener tareas del usuario   |
| POST   | `/api/tareas`                   | Crear nueva tarea            |
| PUT    | `/api/tareas/{id}`              | Actualizar tarea             |
| DELETE | `/api/tareas/{id}`              | Eliminar tarea               |

### Metas

| Método | Endpoint                       | Descripción                 |
|--------|--------------------------------|-----------------------------|
| GET    | `/api/metas/usuario/{id}`      | Obtener metas del usuario   |
| POST   | `/api/metas`                   | Crear nueva meta            |

---

## Usuarios de prueba

Data de prueba agregada en cada usuario.

| Nombre       | Email               | Contraseña |
|--------------|---------------------|------------|
| Juan Pérez   | juan@email.com      | 12345678   |
| María García | maria@email.com     | 12345678   |

---

## Funcionalidades

- Registro e inicio de sesión con validación
- Gestión de tareas (agregar, completar, eliminar)
- Temporizador Pomodoro con anillo animado y modos
- Activar timer directamente desde una tarea
- Metas con barra de progreso
- Dashboard con estadísticas reales del usuario
- Perfil con datos del usuario
- Modo demo sin registro (límite de 2 tareas)
- Protección de rutas para páginas privadas

---

## Notas (Cambios recientes)

- Se añadió scripts para la base de datos
- Ahora los datos persisten en MySQL, ya no se pierden al reiniciar.
- Queda pendiente el método de negocio con cuenta premium, actualmente posee un demo y una cuenta gratuita
