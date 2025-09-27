"use client";

import { Automovel, TipoProprietario } from "@/app/types/automovel";

interface AutomovelStatsProps {
  automoveis: Automovel[];
}

export function AutomovelStats({ automoveis }: AutomovelStatsProps) {
  const stats = {
    total: automoveis.length,
    ativos: automoveis.filter(a => a.ativo).length,
    inativos: automoveis.filter(a => !a.ativo).length,
    porTipo: {
      CLIENTE: automoveis.filter(a => a.tipoProprietario === TipoProprietario.CLIENTE).length,
      EMPRESA: automoveis.filter(a => a.tipoProprietario === TipoProprietario.EMPRESA).length,
      BANCO: automoveis.filter(a => a.tipoProprietario === TipoProprietario.BANCO).length,
    }
  };

  const getTipoProprietarioLabel = (tipo: TipoProprietario) => {
    switch (tipo) {
      case TipoProprietario.CLIENTE:
        return "Clientes";
      case TipoProprietario.EMPRESA:
        return "Empresas";
      case TipoProprietario.BANCO:
        return "Bancos";
      default:
        return tipo;
    }
  };

  const getTipoProprietarioColor = (tipo: TipoProprietario) => {
    switch (tipo) {
      case TipoProprietario.CLIENTE:
        return "bg-green-500";
      case TipoProprietario.EMPRESA:
        return "bg-blue-500";
      case TipoProprietario.BANCO:
        return "bg-purple-500";
      default:
        return "bg-gray-500";
    }
  };

  return (
    <div className="mt-8">
      <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-4">
        📊 Estatísticas dos Automóveis
      </h3>
      
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {/* Total */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md border border-gray-200 dark:border-gray-700">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-gray-100 dark:bg-gray-700">
              <svg className="h-6 w-6 text-gray-600 dark:text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Total</p>
              <p className="text-2xl font-semibold text-gray-900 dark:text-white">{stats.total}</p>
            </div>
          </div>
        </div>

        {/* Ativos */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md border border-gray-200 dark:border-gray-700">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-green-100 dark:bg-green-900">
              <svg className="h-6 w-6 text-green-600 dark:text-green-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Ativos</p>
              <p className="text-2xl font-semibold text-green-600 dark:text-green-400">{stats.ativos}</p>
            </div>
          </div>
        </div>

        {/* Inativos */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md border border-gray-200 dark:border-gray-700">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-red-100 dark:bg-red-900">
              <svg className="h-6 w-6 text-red-600 dark:text-red-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Inativos</p>
              <p className="text-2xl font-semibold text-red-600 dark:text-red-400">{stats.inativos}</p>
            </div>
          </div>
        </div>

        {/* Por Tipo */}
        <div className="bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md border border-gray-200 dark:border-gray-700">
          <div className="flex items-center">
            <div className="p-3 rounded-full bg-blue-100 dark:bg-blue-900">
              <svg className="h-6 w-6 text-blue-600 dark:text-blue-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
              </svg>
            </div>
            <div className="ml-4">
              <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Por Tipo</p>
              <p className="text-lg font-semibold text-gray-900 dark:text-white">
                {Object.entries(stats.porTipo).map(([tipo, count]) => (
                  <span key={tipo} className="block text-sm">
                    {getTipoProprietarioLabel(tipo as TipoProprietario)}: {count}
                  </span>
                ))}
              </p>
            </div>
          </div>
        </div>
      </div>

      {/* Gráfico de barras simples por tipo */}
      {stats.total > 0 && (
        <div className="mt-6 bg-white dark:bg-gray-800 p-6 rounded-lg shadow-md border border-gray-200 dark:border-gray-700">
          <h4 className="text-md font-semibold text-gray-900 dark:text-white mb-4">
            Distribuição por Tipo de Proprietário
          </h4>
          <div className="space-y-3">
            {Object.entries(stats.porTipo).map(([tipo, count]) => {
              const percentage = stats.total > 0 ? (count / stats.total) * 100 : 0;
              return (
                <div key={tipo} className="flex items-center">
                  <div className="w-20 text-sm text-gray-600 dark:text-gray-400">
                    {getTipoProprietarioLabel(tipo as TipoProprietario)}
                  </div>
                  <div className="flex-1 mx-4">
                    <div className="bg-gray-200 dark:bg-gray-700 rounded-full h-2">
                      <div
                        className={`h-2 rounded-full ${getTipoProprietarioColor(tipo as TipoProprietario)}`}
                        style={{ width: `${percentage}%` }}
                      ></div>
                    </div>
                  </div>
                  <div className="w-12 text-sm text-gray-900 dark:text-white text-right">
                    {count}
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      )}
    </div>
  );
}
