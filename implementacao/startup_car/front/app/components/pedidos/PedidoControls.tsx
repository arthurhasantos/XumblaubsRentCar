"use client";

import { useState } from 'react';
import { StatusPedido } from '@/app/types/pedido';

interface PedidoControlsProps {
  searchTerm: string;
  onSearchChange: (value: string) => void;
  showInativos: boolean;
  onShowInativosChange: (value: boolean) => void;
  onNewPedido: () => void;
  onFilterByStatus?: (status: StatusPedido | null) => void;
  selectedStatus?: StatusPedido | null;
}

export const PedidoControls: React.FC<PedidoControlsProps> = ({
  searchTerm,
  onSearchChange,
  showInativos,
  onShowInativosChange,
  onNewPedido,
  onFilterByStatus,
  selectedStatus
}) => {
  const [showFilters, setShowFilters] = useState(false);

  const statusOptions = [
    { value: null, label: 'Todos os Status', icon: '📋' },
    { value: StatusPedido.PENDENTE, label: 'Pendentes', icon: '⏳' },
    { value: StatusPedido.APROVADO, label: 'Aprovados', icon: '✅' },
    { value: StatusPedido.REJEITADO, label: 'Rejeitados', icon: '❌' },
    { value: StatusPedido.EM_ANDAMENTO, label: 'Em Andamento', icon: '🚗' },
    { value: StatusPedido.FINALIZADO, label: 'Finalizados', icon: '🏁' }
  ];

  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 mb-6">
      <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
        {/* Busca */}
        <div className="flex-1">
          <div className="relative">
            <input
              type="text"
              placeholder="Buscar por cliente, automóvel ou placa..."
              value={searchTerm}
              onChange={(e) => onSearchChange(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
            />
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <svg className="h-5 w-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </div>
          </div>
        </div>

        {/* Filtros */}
        <div className="flex items-center gap-3">
          {/* Filtro por Status */}
          {onFilterByStatus && (
            <div className="relative">
              <select
                value={selectedStatus || ''}
                onChange={(e) => onFilterByStatus(e.target.value ? e.target.value as StatusPedido : null)}
                className="appearance-none bg-white dark:bg-gray-700 border border-gray-300 dark:border-gray-600 rounded-lg px-4 py-2 pr-8 text-gray-700 dark:text-white focus:ring-2 focus:ring-primary focus:border-transparent"
              >
                {statusOptions.map((option) => (
                  <option key={option.value || 'all'} value={option.value || ''}>
                    {option.icon} {option.label}
                  </option>
                ))}
              </select>
              <div className="absolute inset-y-0 right-0 flex items-center pr-2 pointer-events-none">
                <svg className="h-4 w-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
                </svg>
              </div>
            </div>
          )}

          {/* Mostrar Inativos */}
          <label className="flex items-center">
            <input
              type="checkbox"
              checked={showInativos}
              onChange={(e) => onShowInativosChange(e.target.checked)}
              className="rounded border-gray-300 text-primary focus:ring-primary focus:ring-2"
            />
            <span className="ml-2 text-sm text-gray-700 dark:text-gray-300">
              Mostrar inativos
            </span>
          </label>

          {/* Botão Novo Pedido */}
          <button
            onClick={onNewPedido}
            className="bg-primary hover:bg-primary-dark text-white px-4 py-2 rounded-lg transition-colors duration-200 flex items-center gap-2"
          >
            <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
            </svg>
            Novo Pedido
          </button>
        </div>
      </div>

      {/* Filtros Avançados (se necessário) */}
      {showFilters && (
        <div className="mt-4 pt-4 border-t border-gray-200 dark:border-gray-600">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Data Início
              </label>
              <input
                type="date"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Data Fim
              </label>
              <input
                type="date"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Valor Mínimo
              </label>
              <input
                type="number"
                placeholder="R$ 0,00"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
              />
            </div>
          </div>
        </div>
      )}

      {/* Botão para mostrar/ocultar filtros avançados */}
      <div className="mt-4 flex justify-center">
        <button
          onClick={() => setShowFilters(!showFilters)}
          className="text-sm text-primary hover:text-primary-dark flex items-center gap-1"
        >
          {showFilters ? 'Ocultar' : 'Mostrar'} Filtros Avançados
          <svg 
            className={`h-4 w-4 transition-transform ${showFilters ? 'rotate-180' : ''}`} 
            fill="none" 
            stroke="currentColor" 
            viewBox="0 0 24 24"
          >
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 9l-7 7-7-7" />
          </svg>
        </button>
      </div>
    </div>
  );
};
