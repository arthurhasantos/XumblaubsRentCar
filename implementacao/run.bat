@echo off
echo ========================================
echo Sistema de Aluguel de Carros
echo Xumblaubs Rent Car
echo ========================================
echo.

echo Verificando se o Java 21 esta instalado...
java -version
if %errorlevel% neq 0 (
    echo ERRO: Java 21 nao encontrado. Por favor, instale o Java 21.
    pause
    exit /b 1
)

echo.
echo Verificando se o PostgreSQL esta rodando...
echo (Certifique-se de que o PostgreSQL esta instalado e rodando)
echo.

echo Iniciando a aplicacao...
echo URL: http://localhost:8080
echo Login: admin / admin123
echo.

mvnw.cmd spring-boot:run

pause
