const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
  res.send(`
    <!DOCTYPE html>
    <html>
    <head>
        <title>Teste - Servidor Funcionando</title>
        <style>
            body { font-family: Arial, sans-serif; text-align: center; padding: 50px; }
            .success { color: green; font-size: 24px; }
        </style>
    </head>
    <body>
        <h1 class="success">✅ Servidor funcionando!</h1>
        <p>Se você está vendo esta página, o servidor está rodando corretamente na porta 3000.</p>
        <p>O problema pode estar no Next.js, não na rede.</p>
    </body>
    </html>
  `);
});

app.listen(port, 'localhost', () => {
  console.log(`🚀 Servidor de teste rodando em http://localhost:${port}`);
  console.log('Pressione Ctrl+C para parar');
});
