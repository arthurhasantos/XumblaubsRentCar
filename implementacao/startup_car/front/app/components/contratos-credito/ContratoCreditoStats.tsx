"use client";

import { ContratoCredito } from "@/app/types/contrato-credito";

interface ContratoCreditoStatsProps {
  contratos: ContratoCredito[];
}

export default function ContratoCreditoStats({ contratos }: ContratoCreditoStatsProps) {
  const totalContratos = contratos.length;
  const contratosAtivos = contratos.filter(contrato => contrato.ativo).length;
  const valorTotalContratos = contratos
    .filter(contrato => contrato.ativo)
    .reduce((sum, contrato) => sum + contrato.valorTotal, 0);

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
      <div className="bg-blue-100 dark:bg-blue-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-blue-800 dark:text-blue-400 mb-2">Total de Contratos</h3>
        <p className="text-3xl font-bold text-blue-800 dark:text-blue-400">{totalContratos}</p>
      </div>
      <div className="bg-green-100 dark:bg-green-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-green-800 dark:text-green-400 mb-2">Contratos Ativos</h3>
        <p className="text-3xl font-bold text-green-800 dark:text-green-400">{contratosAtivos}</p>
      </div>
      <div className="bg-purple-100 dark:bg-purple-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-purple-800 dark:text-purple-400 mb-2">Valor Total dos Contratos</h3>
        <p className="text-3xl font-bold text-purple-800 dark:text-purple-400">
          R$ {valorTotalContratos.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
        </p>
      </div>
    </div>
  );
}