@echo off
echo ========================================
echo  Encerrando todas as aplicações
echo ========================================
echo.

echo 1. Encerrando processos na porta 8080 (Backend)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
    echo    Encerrando processo %%a
    taskkill /PID %%a /F >nul 2>&1
)

echo 2. Encerrando processos na porta 3000 (Frontend)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :3000') do (
    echo    Encerrando processo %%a
    taskkill /PID %%a /F >nul 2>&1
)

echo 3. Encerrando processos Java (Spring Boot)...
taskkill /IM java.exe /F >nul 2>&1

echo 4. Encerrando processos Node.js (Next.js)...
taskkill /IM node.exe /F >nul 2>&1

echo.
echo ✅ Todas as aplicações foram encerradas!
echo.
pause
