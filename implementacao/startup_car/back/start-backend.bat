@echo off
echo ========================================
echo  Iniciando Sistema de Aluguel de Carros
echo ========================================
echo.
echo 1. Compilando o projeto...
call mvn clean compile
echo.
echo 2. Iniciando o Spring Boot...
echo    - Banco: xumblaubs_rentcar
echo    - Porta: 8080
echo    - Context: /api
echo    - Profile: dev
echo.
echo 3. As tabelas serao criadas automaticamente!
echo.
call mvn spring-boot:run -Dspring-boot.run.profiles=dev
pause
