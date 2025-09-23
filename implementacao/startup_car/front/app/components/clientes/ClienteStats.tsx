"use client";

import { Cliente } from "@/app/types/cliente";

interface ClienteStatsProps {
  clientes: Cliente[];
}

export default function ClienteStats({ clientes }: ClienteStatsProps) {
  const totalClientes = clientes.length;
  const clientesAtivos = clientes.filter(c => c.ativo).length;

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
      <div className="bg-blue-100 dark:bg-blue-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-blue-800 dark:text-blue-400 mb-2">Total de Clientes</h3>
        <p className="text-3xl font-bold text-blue-800 dark:text-blue-400">{totalClientes}</p>
      </div>
      <div className="bg-green-100 dark:bg-green-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-green-800 dark:text-green-400 mb-2">Clientes Ativos</h3>
        <p className="text-3xl font-bold text-green-800 dark:text-green-400">{clientesAtivos}</p>
      </div>
      <div className="bg-purple-100 dark:bg-purple-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-purple-800 dark:text-purple-400 mb-2">Sistema Online</h3>
        <p className="text-3xl font-bold text-purple-800 dark:text-purple-400">✓</p>
      </div>
    </div>
  );
}
