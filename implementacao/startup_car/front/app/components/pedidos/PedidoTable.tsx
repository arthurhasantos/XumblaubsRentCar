"use client";

import { PedidoAluguelResponse, StatusPedido } from '@/app/types/pedido';

interface PedidoTableProps {
  pedidos: PedidoAluguelResponse[];
  onEdit: (pedido: PedidoAluguelResponse) => void;
  onDelete: (id: number) => void;
  onAprovar: (id: number) => void;
  onRejeitar: (id: number) => void;
  onIniciar: (id: number) => void;
  onFinalizar: (id: number) => void;
  getStatusColor: (status: StatusPedido) => string;
  getStatusIcon: (status: StatusPedido) => string;
  formatPlaca: (placa: string) => string;
  isAdmin?: boolean;
}

export const PedidoTable: React.FC<PedidoTableProps> = ({
  pedidos,
  onEdit,
  onDelete,
  onAprovar,
  onRejeitar,
  onIniciar,
  onFinalizar,
  getStatusColor,
  getStatusIcon,
  formatPlaca,
  isAdmin = false
}) => {
  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const formatCurrency = (value: number) => {
    return new Intl.NumberFormat('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    }).format(value);
  };

  const canAprovar = (pedido: PedidoAluguelResponse) => {
    return isAdmin && pedido.status === StatusPedido.PENDENTE;
  };

  const canRejeitar = (pedido: PedidoAluguelResponse) => {
    return isAdmin && pedido.status === StatusPedido.PENDENTE;
  };

  const canIniciar = (pedido: PedidoAluguelResponse) => {
    return isAdmin && pedido.status === StatusPedido.APROVADO;
  };

  const canFinalizar = (pedido: PedidoAluguelResponse) => {
    return isAdmin && pedido.status === StatusPedido.EM_ANDAMENTO;
  };

  const canEdit = (pedido: PedidoAluguelResponse) => {
    return pedido.status === StatusPedido.PENDENTE;
  };

  const canDelete = (pedido: PedidoAluguelResponse) => {
    return isAdmin && pedido.status === StatusPedido.PENDENTE;
  };

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
        <thead className="bg-gray-50 dark:bg-gray-700">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Cliente
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Automóvel
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Período
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Status
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Valor
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Dias
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Ações
            </th>
          </tr>
        </thead>
        <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
          {pedidos.map((pedido) => (
            <tr key={pedido.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
              <td className="px-6 py-4 whitespace-nowrap">
                <div>
                  <div className="text-sm font-medium text-gray-900 dark:text-white">
                    {pedido.clienteNome}
                  </div>
                  <div className="text-sm text-gray-500 dark:text-gray-400">
                    {pedido.clienteCpf}
                  </div>
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <div>
                  <div className="text-sm font-medium text-gray-900 dark:text-white">
                    {pedido.automovelMarca} {pedido.automovelModelo}
                  </div>
                  <div className="text-sm text-gray-500 dark:text-gray-400">
                    {formatPlaca(pedido.automovelPlaca)}
                  </div>
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <div>
                  <div className="text-sm text-gray-900 dark:text-white">
                    {formatDate(pedido.dataInicio)}
                  </div>
                  <div className="text-sm text-gray-500 dark:text-gray-400">
                    até {formatDate(pedido.dataFim)}
                  </div>
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusColor(pedido.status)}`}>
                  {getStatusIcon(pedido.status)} {pedido.statusDescricao}
                </span>
                {pedido.motivoRejeicao && (
                  <div className="text-xs text-red-600 dark:text-red-400 mt-1">
                    {pedido.motivoRejeicao}
                  </div>
                )}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {formatCurrency(pedido.valorTotal)}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {pedido.diasAluguel} dias
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                <div className="flex items-center gap-2">
                  {/* Aprovar */}
                  {canAprovar(pedido) && (
                    <button
                      onClick={() => onAprovar(pedido.id)}
                      className="text-green-600 hover:text-green-900 dark:text-green-400 dark:hover:text-green-300"
                      title="Aprovar pedido"
                    >
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                      </svg>
                    </button>
                  )}

                  {/* Rejeitar */}
                  {canRejeitar(pedido) && (
                    <button
                      onClick={() => onRejeitar(pedido.id)}
                      className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                      title="Rejeitar pedido"
                    >
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                      </svg>
                    </button>
                  )}

                  {/* Iniciar */}
                  {canIniciar(pedido) && (
                    <button
                      onClick={() => onIniciar(pedido.id)}
                      className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300"
                      title="Iniciar aluguel"
                    >
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M14.828 14.828a4 4 0 01-5.656 0M9 10h1m4 0h1m-6 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                      </svg>
                    </button>
                  )}

                  {/* Finalizar */}
                  {canFinalizar(pedido) && (
                    <button
                      onClick={() => onFinalizar(pedido.id)}
                      className="text-gray-600 hover:text-gray-900 dark:text-gray-400 dark:hover:text-gray-300"
                      title="Finalizar aluguel"
                    >
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                      </svg>
                    </button>
                  )}

                  {/* Editar */}
                  {canEdit(pedido) && (
                    <button
                      onClick={() => onEdit(pedido)}
                      className="text-indigo-600 hover:text-indigo-900 dark:text-indigo-400 dark:hover:text-indigo-300"
                      title="Editar pedido"
                    >
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                      </svg>
                    </button>
                  )}

                  {/* Excluir */}
                  {canDelete(pedido) && (
                    <button
                      onClick={() => onDelete(pedido.id)}
                      className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                      title="Excluir pedido"
                    >
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                      </svg>
                    </button>
                  )}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
