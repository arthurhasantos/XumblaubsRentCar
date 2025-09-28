"use client";

import { Banco } from "@/app/types/banco";

interface BancoStatsProps {
  bancos: Banco[];
}

export default function BancoStats({ bancos }: BancoStatsProps) {
  const totalBancos = bancos.length;
  const bancosAtivos = bancos.filter(banco => banco.ativo).length;

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-8">
      <div className="bg-blue-100 dark:bg-blue-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-blue-800 dark:text-blue-400 mb-2">Total de Bancos</h3>
        <p className="text-3xl font-bold text-blue-800 dark:text-blue-400">{totalBancos}</p>
      </div>
      <div className="bg-green-100 dark:bg-green-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-green-800 dark:text-green-400 mb-2">Bancos Ativos</h3>
        <p className="text-3xl font-bold text-green-800 dark:text-green-400">{bancosAtivos}</p>
      </div>
    </div>
  );
}
