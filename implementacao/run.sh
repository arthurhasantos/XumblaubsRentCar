#!/bin/bash

echo "========================================"
echo "Sistema de Aluguel de Carros"
echo "Xumblaubs Rent Car"
echo "========================================"
echo

echo "Verificando se o Java 21 está instalado..."
java -version
if [ $? -ne 0 ]; then
    echo "ERRO: Java 21 não encontrado. Por favor, instale o Java 21."
    exit 1
fi

echo
echo "Verificando se o PostgreSQL está rodando..."
echo "(Certifique-se de que o PostgreSQL está instalado e rodando)"
echo

echo "Iniciando a aplicação..."
echo "URL: http://localhost:8080"
echo "Login: admin / admin123"
echo

./mvnw spring-boot:run
