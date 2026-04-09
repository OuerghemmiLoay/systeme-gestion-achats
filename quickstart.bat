@echo off
REM Quick Start Script for Windows
REM Système de Gestion des Achats et Fournisseurs

echo.
echo ========================================
echo Systeme de Gestion - Quick Start Script
echo ========================================
echo.

:menu
cls
echo.
echo Choose an option:
echo.
echo 1. Run with Docker Compose (Recommended)
echo 2. Build and run locally with Gradle
echo 3. View project documentation
echo 4. Open Swagger UI (requires running API)
echo 5. Check Docker status
echo 6. Stop Docker containers
echo 7. Exit
echo.
set /p choice="Enter your choice (1-7): "

if "%choice%"=="1" goto docker_compose
if "%choice%"=="2" goto local_run
if "%choice%"=="3" goto docs
if "%choice%"=="4" goto swagger
if "%choice%"=="5" goto docker_status
if "%choice%"=="6" goto docker_stop
if "%choice%"=="7" exit /b 0
goto menu

:docker_compose
cls
echo.
echo Starting Docker Compose...
echo.
docker-compose up -d
if errorlevel 1 (
    echo Error: Docker Compose failed. Make sure Docker Desktop is running.
    pause
    goto menu
)
echo.
echo Services starting...
echo.
echo Waiting 30 seconds for services to be ready...
timeout /t 30 /nobreak
echo.
echo.
echo ========================================
echo API READY!
echo ========================================
echo.
echo API URL:       http://localhost:8080/api
echo Swagger UI:    http://localhost:8080/api/swagger-ui.html
echo Database:      localhost:3306
echo User:          systeme_user
echo Password:      systeme_password
echo.
echo.
echo To stop services: docker-compose down
echo To view logs:     docker-compose logs -f api
echo.
pause
goto menu

:local_run
cls
echo.
echo Building and running with Gradle...
echo.
if not exist "build.gradle" (
    echo Error: build.gradle not found. Please run from project root.
    pause
    goto menu
)
echo.
echo Step 1: Clean and Build
call gradlew clean build
if errorlevel 1 (
    echo Error: Build failed.
    pause
    goto menu
)
echo.
echo Step 2: Running Spring Boot Application
echo.
echo API will start on: http://localhost:8080/api
echo.
call gradlew bootRun
pause
goto menu

:docs
cls
echo.
echo Documentation Files:
echo.
echo 1. QUICKSTART.md           - 5-minute quick start
echo 2. README.md               - Complete guide
echo 3. ANGULAR_SETUP.md        - Front-end setup
echo 4. IMPLEMENTATION_SUMMARY.md - Technical details
echo 5. PROJECT_COMPLETION_REPORT.md - Final report
echo 6. Go back to menu
echo.
set /p doc_choice="Enter your choice (1-6): "

if "%doc_choice%"=="1" goto open_quickstart
if "%doc_choice%"=="2" goto open_readme
if "%doc_choice%"=="3" goto open_angular
if "%doc_choice%"=="4" goto open_impl
if "%doc_choice%"=="5" goto open_report
if "%doc_choice%"=="6" goto menu
goto docs

:open_quickstart
start notepad QUICKSTART.md
goto docs

:open_readme
start notepad README.md
goto docs

:open_angular
start notepad ANGULAR_SETUP.md
goto docs

:open_impl
start notepad IMPLEMENTATION_SUMMARY.md
goto docs

:open_report
start notepad PROJECT_COMPLETION_REPORT.md
goto docs

:swagger
cls
echo.
echo Opening Swagger UI...
echo.
start http://localhost:8080/api/swagger-ui.html
echo.
echo Swagger UI opened in default browser.
echo If it didn't open, manually visit: http://localhost:8080/api/swagger-ui.html
echo.
pause
goto menu

:docker_status
cls
echo.
echo Docker Compose Status:
echo.
docker-compose ps
echo.
pause
goto menu

:docker_stop
cls
echo.
echo Stopping Docker containers...
echo.
docker-compose down
echo.
echo Containers stopped.
echo.
echo To also remove database volume (DELETES DATA):
set /p remove="Type 'yes' to remove volumes: "
if /i "%remove%"=="yes" (
    docker-compose down -v
    echo Volumes removed.
)
echo.
pause
goto menu
