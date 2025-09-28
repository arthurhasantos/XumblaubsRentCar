"use client";

import { Banco } from "@/app/types/banco";

interface BancoStatsProps {
  bancos: Banco[];
}

export default function BancoStats({ bancos }: BancoStatsProps) {
  const totalBancos = bancos.length;
  const bancosAtivos = bancos.filter(banco => banco.ativo).length;
  const totalLimiteCredito = bancos
    .filter(banco => banco.ativo && banco.limiteCreditoMaximo)
    .reduce((sum, banco) => sum + (banco.limiteCreditoMaximo || 0), 0);

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
      <div className="bg-blue-100 dark:bg-blue-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-blue-800 dark:text-blue-400 mb-2">Total de Bancos</h3>
        <p className="text-3xl font-bold text-blue-800 dark:text-blue-400">{totalBancos}</p>
      </div>
      <div className="bg-green-100 dark:bg-green-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-green-800 dark:text-green-400 mb-2">Bancos Ativos</h3>
        <p className="text-3xl font-bold text-green-800 dark:text-green-400">{bancosAtivos}</p>
      </div>
      <div className="bg-purple-100 dark:bg-purple-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-purple-800 dark:text-purple-400 mb-2">Limite Total de Crédito</h3>
        <p className="text-3xl font-bold text-purple-800 dark:text-purple-400">
          R$ {totalLimiteCredito.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
        </p>
      </div>
    </div>
  );
}
