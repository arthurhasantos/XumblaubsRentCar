"use client";

import { PedidoAluguel, PedidoAluguelRequest, Cliente, Automovel } from '@/app/types/pedido';
import { UseFormRegister, UseFormHandleSubmit, FieldErrors } from 'react-hook-form';

interface PedidoFormProps {
  editingPedido: PedidoAluguel | null;
  onSubmit: (data: PedidoAluguelRequest) => void;
  onClose: () => void;
  register: UseFormRegister<PedidoAluguelRequest>;
  handleSubmit: UseFormHandleSubmit<PedidoAluguelRequest>;
  errors: FieldErrors<PedidoAluguelRequest>;
  clientes: Cliente[];
  automoveis: Automovel[];
  error?: string | null;
  onClearError?: () => void;
}

export const PedidoForm: React.FC<PedidoFormProps> = ({
  editingPedido,
  onSubmit,
  onClose,
  register,
  handleSubmit,
  errors,
  clientes,
  automoveis,
  error,
  onClearError
}) => {
  const formatCPF = (cpf: string) => {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
  };

  const formatPlaca = (placa: string) => {
    if (/^[A-Z]{3}[0-9]{4}$/.test(placa)) {
      return placa.replace(/([A-Z]{3})([0-9]{4})/, "$1-$2");
    }
    return placa;
  };

  const getMinDate = () => {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    return tomorrow.toISOString().split('T')[0];
  };

  const getMinEndDate = (startDate: string) => {
    if (!startDate) return getMinDate();
    const start = new Date(startDate);
    start.setDate(start.getDate() + 1);
    return start.toISOString().split('T')[0];
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-2xl w-full mx-4 max-h-[90vh] overflow-y-auto">
        <div className="p-6">
          {/* Header */}
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">
              {editingPedido ? 'Editar Pedido' : 'Novo Pedido de Aluguel'}
            </h2>
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-300"
            >
              <svg className="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>

          {/* Error Message */}
          {error && (
            <div className="mb-4 bg-red-50 dark:bg-red-900/20 border border-red-200 dark:border-red-800 rounded-lg p-4">
              <div className="flex items-center justify-between">
                <div className="flex items-center">
                  <div className="flex-shrink-0">
                    <svg className="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
                      <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                    </svg>
                  </div>
                  <div className="ml-3">
                    <p className="text-sm text-red-800 dark:text-red-200">
                      {error}
                    </p>
                  </div>
                </div>
                {onClearError && (
                  <button
                    onClick={onClearError}
                    className="text-red-400 hover:text-red-600 dark:hover:text-red-300"
                  >
                    <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                    </svg>
                  </button>
                )}
              </div>
            </div>
          )}

          {/* Form */}
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            {/* Cliente */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Cliente *
              </label>
              <select
                {...register('clienteId', { required: 'Cliente é obrigatório' })}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
              >
                <option value="">Selecione um cliente</option>
                {clientes.map((cliente) => (
                  <option key={cliente.id} value={cliente.id}>
                    {cliente.nome} - {formatCPF(cliente.cpf)}
                  </option>
                ))}
              </select>
              {errors.clienteId && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">
                  {errors.clienteId.message}
                </p>
              )}
            </div>

            {/* Automóvel */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Automóvel *
              </label>
              <select
                {...register('automovelId', { required: 'Automóvel é obrigatório' })}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
              >
                <option value="">Selecione um automóvel</option>
                {automoveis.map((automovel) => (
                  <option key={automovel.id} value={automovel.id}>
                    {automovel.marca} {automovel.modelo} ({formatPlaca(automovel.placa)}) - {automovel.ano}
                  </option>
                ))}
              </select>
              {errors.automovelId && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">
                  {errors.automovelId.message}
                </p>
              )}
            </div>

            {/* Datas */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Data de Início *
                </label>
                <input
                  type="date"
                  {...register('dataInicio', { 
                    required: 'Data de início é obrigatória',
                    min: {
                      value: getMinDate(),
                      message: 'Data de início deve ser no futuro'
                    }
                  })}
                  min={getMinDate()}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.dataInicio && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">
                    {errors.dataInicio.message}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Data de Fim *
                </label>
                <input
                  type="date"
                  {...register('dataFim', { 
                    required: 'Data de fim é obrigatória',
                    min: {
                      value: getMinDate(),
                      message: 'Data de fim deve ser no futuro'
                    }
                  })}
                  min={getMinDate()}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.dataFim && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">
                    {errors.dataFim.message}
                  </p>
                )}
              </div>
            </div>

            {/* Observações */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Observações
              </label>
              <textarea
                {...register('observacoes')}
                rows={3}
                placeholder="Observações adicionais sobre o pedido..."
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
              />
              {errors.observacoes && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">
                  {errors.observacoes.message}
                </p>
              )}
            </div>

            {/* Informações do Pedido */}
            <div className="bg-gray-50 dark:bg-gray-700 rounded-lg p-4">
              <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-3">
                Informações do Pedido
              </h3>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
                <div>
                  <span className="text-gray-600 dark:text-gray-400">Status:</span>
                  <span className="ml-2 text-gray-900 dark:text-white font-medium">
                    {editingPedido ? editingPedido.statusDescricao : 'Pendente'}
                  </span>
                </div>
                <div>
                  <span className="text-gray-600 dark:text-gray-400">Valor estimado:</span>
                  <span className="ml-2 text-gray-900 dark:text-white font-medium">
                    R$ 100,00 por dia
                  </span>
                </div>
              </div>
            </div>

            {/* Botões */}
            <div className="flex justify-end gap-3 pt-4 border-t border-gray-200 dark:border-gray-600">
              <button
                type="button"
                onClick={onClose}
                className="px-4 py-2 text-gray-700 dark:text-gray-300 bg-gray-200 dark:bg-gray-600 hover:bg-gray-300 dark:hover:bg-gray-500 rounded-lg transition-colors duration-200"
              >
                Cancelar
              </button>
              <button
                type="submit"
                className="px-4 py-2 bg-primary hover:bg-primary-dark text-white rounded-lg transition-colors duration-200"
              >
                {editingPedido ? 'Atualizar' : 'Criar'} Pedido
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};
