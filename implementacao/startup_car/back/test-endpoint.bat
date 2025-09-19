@echo off
echo Testando endpoint de clientes...
timeout /t 5 /nobreak >nul
curl -v http://localhost:8080/api/clientes
echo.
echo Testando endpoint de saúde...
curl -v http://localhost:8080/api/actuator/health
pause
