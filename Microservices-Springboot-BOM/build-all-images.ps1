# Build all microservices and generate Docker images
$services = @("configserver", "eurekaserver", "accounts", "loans", "cards", "gatewayserver", "message")
$basePath = "D:\Personal Projects\Eazy-Bank-Microservices\Microservices-Springboot-BOM"

Write-Host "Building common module first..." -ForegroundColor Green
Set-Location "$basePath\eazy-bom"
mvn clean install -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to build common module" -ForegroundColor Red
    exit 1
}

foreach ($service in $services) {
    Write-Host "`nBuilding and creating Docker image for $service..." -ForegroundColor Yellow
    Set-Location "$basePath\$service"
    mvn clean package jib:dockerBuild "-DskipTests" "-Djib.skip=false"
    if ($LASTEXITCODE -ne 0) {
        Write-Host "Failed to build $service" -ForegroundColor Red
        exit 1
    }
    Write-Host "Successfully built $service" -ForegroundColor Green
}

Write-Host "`nAll services built successfully!" -ForegroundColor Green
