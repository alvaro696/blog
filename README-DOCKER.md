## **BONUS: Dockerización completa**

La API REST está completamente dockerizada y lista para producción:

### Ejecutar con Docker

```bash
# Construir imagen
docker build -t blog-spring-boot:latest .

# Ejecutar contenedor
docker run -d -p 8080:8080 --name blog-app blog-spring-boot:latest

# Ver logs
docker logs -f blog-app

# Detener
docker stop blog-app && docker rm blog-app
```

### Usar Docker Compose (Recomendado)

```bash
# Todo en un comando
docker-compose up -d

# Detener todo
docker-compose down
```

### Script automatizado

```bash
# En Windows PowerShell
.\docker-manager.ps1

# En Linux/Mac
./docker-run.sh
```

** Ventajas de la versión Docker:**

- Mismo entorno en cualquier máquina
- Fácil despliegue en producción
- No requiere instalar Java localmente
- Aislamiento completo de dependencias
- Escalabilidad con orquestadores (Kubernetes, Docker Swarm)

