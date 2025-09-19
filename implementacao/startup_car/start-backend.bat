@echo off
echo ========================================
echo  Iniciando Backend Spring Boot
echo ========================================
echo.

echo 1. Navegando para o diretório do backend...
cd /d "%~dp0back"
if %errorlevel% neq 0 (
    echo ❌ Erro ao navegar para o diretório back!
    pause
    exit /b 1
)

echo 2. Verificando se Maven está disponível...
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven não encontrado! Verifique se está instalado.
    pause
    exit /b 1
)

echo 3. Compilando o projeto...
call mvn clean compile
if %errorlevel% neq 0 (
    echo ❌ Erro na compilação!
    pause
    exit /b 1
)

echo 4. Iniciando Spring Boot...
echo    - Banco: xumblaubs_rentcar
echo    - Porta: 8080
echo    - Context: /api
echo    - Logs: logs/xumblaubs-backend.log
echo.
echo 🚀 Iniciando aplicação...
echo.

call mvn spring-boot:run
pause
