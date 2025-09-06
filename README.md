# BackEnd de Gestión de Blogs

Prueba técnica API REST desarrollada con Spring Boot (java17) que permite gestionar blogs, autores y comentarios de manera sencilla. Incluye funcionalidades como crear blogs, agregar comentarios y mantener un historial automático de cambios.

## Datos

- **Ponstulante:** Alvaro Willy Mamani Arhuata
- **Contacto:** alvaro00.am@outlook.com 61108270

## ¿Qué hace este api rest?

- **Crear y gestionar autores** de blogs
- **Escribir y publicar blogs** con diferentes temas
- **Permitir comentarios** de los lectores con puntuaciones
- **Ver el historial** de cambios de cada blog
- **Buscar blogs** por autor o tema

## Requisitos

- **Java 17**
- Conexión a internet para la primera descarga
- Puerto 8080 libre en tu computadora

## Cómo ejecutar la aplicación

### **OPCIÓN 1: Con Docker (Recomendado para producción)**

```bash
# 1. Construir la imagen Docker
docker build -t blog-spring-boot:latest .

# 2. Ejecutar el contenedor
docker run -d -p 8080:8080 --name blog-app blog-spring-boot:latest

# 3. Ver logs (opcional)
docker logs -f blog-app

# 4. Detener cuando termines
docker stop blog-app && docker rm blog-app
```

**O usar Docker Compose (aún más fácil):**

```bash
# Todo en un comando
docker-compose up -d

# Detener
docker-compose down
```

### Opción 1: Descarga directa (más fácil)

```bash
# 1. Descargar el proyecto
git clone https://github.com/alvaro696/blog.git
cd blog

# 2. Ejecutar en Windows
.\mvnw.cmd spring-boot:run

# 2. Ejecutar en Mac/Linux
./mvnw spring-boot:run
```

### Opción 2: Con Maven instalado

```bash
mvn spring-boot:run
```

**¡Listo!** La aplicación estará funcionando en unos minutos.

## Cómo usar la aplicación

Una vez que veas el mensaje "Started BlogApplication", abre tu navegador y visita:

| Página                                                                       | Para qué sirve                                                                            |
| ---------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------- |
| **[Documentación Interactiva](http://localhost:8080/swagger-ui/index.html)** | Probar todas las funciones sin programar                                                  |
| **[Base de Datos](http://localhost:8080/h2-console)**                        | Ver los datos guardados (Usuario: `sa`, Password: `password`, JDBC: `jdbc:h2:mem:blogdb`) |
| **[Estado del Sistema](http://localhost:8080/actuator/health)**              | Verificar que todo funciona bien                                                          |

## Funcionalidades principales

### Autores

- Crear perfiles de autores con nombre, email y país
- Buscar autores por nombre o país
- Cada autor puede escribir múltiples blogs

### Blogs

- Escribir blogs con título, contenido y tema
- Elegir si permites comentarios o no
- Ver historial automático de todos los cambios
- Buscar blogs por autor

### Comentarios

- Los lectores pueden comentar y puntuar (0-10)
- Solo en blogs que permiten comentarios
- Ver estadísticas: promedio, mejor y peor puntuación

### Historial

- Se guarda automáticamente cada cambio del blog
- Ver qué cambió, cuándo y versión anterior
- Útil para recuperar información o auditoría

## Pruebas rápidas

### Usar Swagger (más fácil)

1. **Abrir**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
2. **Crear autor**: Usar "POST /api/autores" con ejemplo incluido
3. **Crear blog**: Usar "POST /api/blogs" con autorId=1
4. **Agregar comentario**: Usar "POST /api/blogs/1/comentarios"
5. **Ver historial**: Modificar blog y usar "GET /api/blogs/1/historial"

## Requisitos técnicos

| Requerimiento | Versión    | ¿Incluido?             |
| ------------- | ---------- | ---------------------- |
| Java          | 17+        | Compatible             |
| Maven         | 3.8+       | Maven Wrapper incluido |
| Puerto        | 8080       | Configurable           |
| Navegador     | Cualquiera | Para Swagger           |

## ¿Problemas?

**Puerto ocupado**: Cambiar puerto en `application.properties`

```properties
server.port=8081
```

**Java no encontrado**: Verificar con `java --version`

**No funciona**: Revisar logs en la consola y usar [Swagger](http://localhost:8080/swagger-ui/index.html) para probar directamente

**Proyecto completado** - Todas las funcionalidades implementadas, probadas y dockerizadas
