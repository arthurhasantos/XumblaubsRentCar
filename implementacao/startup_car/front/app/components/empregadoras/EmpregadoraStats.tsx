"use client";

import { Empregadora } from "@/app/types/empregadora";

interface EmpregadoraStatsProps {
  empregadoras: Empregadora[];
}

export default function EmpregadoraStats({ empregadoras }: EmpregadoraStatsProps) {
  const totalEmpregadoras = empregadoras.length;
  const empregadorasAtivas = empregadoras.filter(empregadora => empregadora.ativo).length;
  const rendimentoTotal = empregadoras
    .filter(empregadora => empregadora.ativo)
    .reduce((sum, empregadora) => sum + empregadora.rendimento, 0);

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
      <div className="bg-blue-100 dark:bg-blue-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-blue-800 dark:text-blue-400 mb-2">Total de Empregadoras</h3>
        <p className="text-3xl font-bold text-blue-800 dark:text-blue-400">{totalEmpregadoras}</p>
      </div>
      <div className="bg-green-100 dark:bg-green-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-green-800 dark:text-green-400 mb-2">Empregadoras Ativas</h3>
        <p className="text-3xl font-bold text-green-800 dark:text-green-400">{empregadorasAtivas}</p>
      </div>
      <div className="bg-purple-100 dark:bg-purple-900/20 p-6 rounded-lg">
        <h3 className="text-lg font-semibold text-purple-800 dark:text-purple-400 mb-2">Rendimento Total</h3>
        <p className="text-3xl font-bold text-purple-800 dark:text-purple-400">
          R$ {rendimentoTotal.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
        </p>
      </div>
    </div>
  );
}