# Script de Docker para Windows PowerShell
# Blog Spring Boot Application - Docker Management

Write-Host "=== Docker Management para Blog Spring Boot ===" -ForegroundColor Green

function Show-Menu {
    Write-Host "`nSelecciona una opción:" -ForegroundColor Yellow
    Write-Host "1. Construir imagen Docker"
    Write-Host "2. Ejecutar contenedor"
    Write-Host "3. Usar Docker Compose"
    Write-Host "4. Detener contenedores"
    Write-Host "5. Ver logs"
    Write-Host "6. Limpiar contenedores e imágenes"
    Write-Host "7. Salir"
}

function Build-Image {
    Write-Host "Construyendo imagen Docker..." -ForegroundColor Blue
    docker build -t blog-spring-boot:latest .
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Imagen construida exitosamente" -ForegroundColor Green
    } else {
        Write-Host "❌ Error al construir la imagen" -ForegroundColor Red
    }
}

function Run-Container {
    Write-Host "Ejecutando contenedor..." -ForegroundColor Blue
    docker run -d -p 8080:8080 --name blog-app blog-spring-boot:latest
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Contenedor iniciado en http://localhost:8080" -ForegroundColor Green
        Write-Host "📖 Swagger UI: http://localhost:8080/swagger-ui/index.html" -ForegroundColor Cyan
        Write-Host "🗄️  H2 Console: http://localhost:8080/h2-console" -ForegroundColor Cyan
    } else {
        Write-Host "❌ Error al ejecutar el contenedor" -ForegroundColor Red
    }
}

function Use-DockerCompose {
    Write-Host "Usando Docker Compose..." -ForegroundColor Blue
    docker-compose up -d
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Servicios iniciados con Docker Compose" -ForegroundColor Green
        Write-Host "📖 Swagger UI: http://localhost:8080/swagger-ui/index.html" -ForegroundColor Cyan
    } else {
        Write-Host "❌ Error con Docker Compose" -ForegroundColor Red
    }
}

function Stop-Containers {
    Write-Host "Deteniendo contenedores..." -ForegroundColor Blue
    docker stop blog-app 2>$null
    docker-compose down 2>$null
    Write-Host "✅ Contenedores detenidos" -ForegroundColor Green
}

function Show-Logs {
    Write-Host "Mostrando logs del contenedor..." -ForegroundColor Blue
    if (docker ps -q -f name=blog-app) {
        docker logs -f blog-app
    } elseif (docker-compose ps -q blog-app) {
        docker-compose logs -f blog-app
    } else {
        Write-Host "❌ No se encontró contenedor en ejecución" -ForegroundColor Red
    }
}

function Clean-Docker {
    Write-Host "Limpiando contenedores e imágenes..." -ForegroundColor Blue
    docker stop blog-app 2>$null
    docker rm blog-app 2>$null
    docker-compose down 2>$null
    docker rmi blog-spring-boot:latest 2>$null
    docker system prune -f
    Write-Host "✅ Limpieza completada" -ForegroundColor Green
}

# Verificar que Docker esté instalado
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Docker no está instalado. Instala Docker Desktop desde https://www.docker.com/" -ForegroundColor Red
    exit 1
}

# Menú principal
do {
    Show-Menu
    $choice = Read-Host "`nIngresa tu opción (1-7)"
    
    switch ($choice) {
        "1" { Build-Image }
        "2" { Run-Container }
        "3" { Use-DockerCompose }
        "4" { Stop-Containers }
        "5" { Show-Logs }
        "6" { Clean-Docker }
        "7" { 
            Write-Host "¡Hasta luego! 👋" -ForegroundColor Green
            break 
        }
        default { 
            Write-Host "❌ Opción inválida. Selecciona 1-7." -ForegroundColor Red 
        }
    }
    
    if ($choice -ne "7") {
        Read-Host "`nPresiona Enter para continuar..."
    }
} while ($choice -ne "7")
