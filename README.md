# 🚀 Arquitectura de Microservicios - Proyecto Final

Este repositorio contiene la implementación de *tres proyectos desarrollados con arquitectura de microservicios*, aplicando buenas prácticas modernas de desarrollo backend, seguridad, documentación y despliegue con contenedores.

---

# 📌 Contenido del Repositorio

## 📁 1. gestion-tareas-microservicios-main

Sistema orientado a la administración de tareas y usuarios mediante microservicios independientes.

### 🔹 Tecnologías usadas:
- Java 17
- Spring Boot
- Spring Cloud Gateway
- Spring Data JPA
- MySQL
- Docker
- OpenAPI / Swagger

### 🔹 Microservicios:
- gateway-service
- ms-usuarios
- ms-tareas
- ms-estadisticas

### 🔹 Funcionalidades:
- Registro de usuarios
- Gestión de tareas
- Reportes estadísticos
- Enrutamiento centralizado

---

## 📁 2. microservicios-instituto

Sistema académico orientado a la gestión institucional.

### 🔹 Tecnologías usadas:
- Java 17
- Spring Boot
- Spring Cloud Gateway
- Eureka Server
- MySQL
- Docker
- Keycloak
- OpenAPI

### 🔹 Microservicios:
- instituto-service
- matricula-service
- gateway-service

### 🔹 Funcionalidades:
- Registro de alumnos
- Gestión de matrículas
- Seguridad con JWT
- Documentación automática

---

## 📁 3. microservicios-inventario

Sistema para administración de inventario empresarial.

### 🔹 Tecnologías usadas:
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Docker
- OpenAPI

### 🔹 Funcionalidades:
- Registro de productos
- Control de stock
- Actualización de inventario
- Gestión CRUD completa

---

# 🧩 Arquitectura Aplicada

Todos los proyectos fueron desarrollados bajo enfoque *Microservicios*, permitiendo:

✅ Escalabilidad independiente  
✅ Despliegue modular  
✅ Mejor mantenimiento  
✅ Separación de responsabilidades  
✅ Seguridad centralizada  
✅ Integración entre servicios

---

# 🔐 Seguridad Implementada

Se aplicó autenticación moderna mediante:

- Keycloak
- JWT Tokens
- API Gateway como punto único de entrada

---

# 📄 Documentación API

Cada servicio cuenta con documentación interactiva mediante:

- Swagger UI
- OpenAPI 3

---

# 🐳 Contenedorización

Los proyectos fueron preparados para despliegue usando:

- Docker
- Dockerfile por servicio
- Ejecución independiente por contenedor

---

# 👨‍💻 Autor

*Fabio Aliaga*  
Proyecto Final - Curso de Microservicios

---

# 📌 Observación

Este repositorio representa la aplicación práctica de conceptos vistos en clase:

- Monolito vs Microservicios
- API Gateway
- Seguridad OAuth2 / JWT
- Docker
- Spring Cloud
- Bases de datos distribuidas
- Documentación REST API

---
