const http = require('http');

const server = http.createServer((req, res) => {
  res.writeHead(200, { 'Content-Type': 'text/html' });
  res.end('<h1>Teste do servidor funcionando!</h1>');
});

server.listen(3000, 'localhost', () => {
  console.log('Servidor de teste rodando em http://localhost:3000');
});
