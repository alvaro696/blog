#!/bin/bash
# Script de inicio rápido para Docker
# Blog Spring Boot Application

# Verificar si Docker está instalado
if ! command -v docker &> /dev/null; then
    echo "Docker no está instalado"
    exit 1
fi

# Construir la imagen
echo "Construyendo imagen Docker..."
docker build -t blog-spring-boot:latest .

if [ $? -eq 0 ]; then
    echo "Imagen construida exitosamente"
    
    # Detener contenedor anterior si existe
    docker stop blog-app 2>/dev/null
    docker rm blog-app 2>/dev/null
    
    # Ejecutar nuevo contenedor
    echo "Iniciando contenedor..."
    docker run -d -p 8080:8080 --name blog-app blog-spring-boot:latest
    
    if [ $? -eq 0 ]; then
        echo "Aplicación iniciada exitosamente!"
        echo "Aplicación disponible en: http://localhost:8080"
        echo "Swagger UI: http://localhost:8080/swagger-ui/index.html"
        echo "H2 Console: http://localhost:8080/h2-console"
        echo ""
        echo "Para ver logs: docker logs -f blog-app"
        echo "Para detener: docker stop blog-app"
    else
        echo "Error al iniciar el contenedor"
        exit 1
    fi
else
    echo "Error al construir la imagen"
    exit 1
fi
