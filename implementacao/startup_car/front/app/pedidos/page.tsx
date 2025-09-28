"use client";

import { usePedidos } from "@/app/hooks/usePedidos";
import { formatPlaca } from "@/app/utils/formatters";
import { PedidoControls, PedidoTable, PedidoForm, PedidoStats } from "@/app/components/pedidos";
import { LoadingSpinner, EmptyState, BackgroundDecoration } from "@/app/components/shared";
import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";
import { useAuth } from "@/contexts/AuthContext";
import { StatusPedido } from "@/app/types/pedido";
import { useState } from "react";

export default function PedidosPage() {
  const { user } = useAuth();
  const [selectedStatus, setSelectedStatus] = useState<StatusPedido | null>(null);
  const [showRejectModal, setShowRejectModal] = useState(false);
  const [rejectPedidoId, setRejectPedidoId] = useState<number | null>(null);
  const [rejectMotivo, setRejectMotivo] = useState('');

  const {
    pedidos,
    clientes,
    automoveis,
    loading,
    showModal,
    editingPedido,
    searchTerm,
    showInativos,
    stats,
    error,
    statsError,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchPedidos,
    onSubmit,
    openModal,
    closeModal,
    clearError,
    clearStatsError,
    aprovarPedido,
    rejeitarPedido,
    iniciarAluguel,
    finalizarAluguel,
    desativarPedido,
    filterByStatus,
    getStatusColor,
    getStatusIcon
  } = usePedidos();

  const isAdmin = user?.roles.includes('ROLE_ADMIN') || false;

  // Filtrar pedidos por status
  const filteredPedidos = selectedStatus 
    ? filterByStatus(selectedStatus)
    : (pedidos || []).filter(pedido => showInativos || pedido.ativo);

  const handleAprovar = async (id: number) => {
    try {
      await aprovarPedido(id);
      alert('Pedido aprovado com sucesso!');
    } catch (error) {
      alert('Erro ao aprovar pedido. Tente novamente.');
    }
  };

  const handleRejeitar = async (id: number) => {
    setRejectPedidoId(id);
    setShowRejectModal(true);
  };

  const confirmRejeitar = async () => {
    if (rejectPedidoId && rejectMotivo.trim()) {
      try {
        await rejeitarPedido(rejectPedidoId, rejectMotivo);
        alert('Pedido rejeitado com sucesso!');
        setShowRejectModal(false);
        setRejectPedidoId(null);
        setRejectMotivo('');
      } catch (error) {
        alert('Erro ao rejeitar pedido. Tente novamente.');
      }
    } else {
      alert('Por favor, informe o motivo da rejeição.');
    }
  };

  const handleIniciar = async (id: number) => {
    try {
      await iniciarAluguel(id);
      alert('Aluguel iniciado com sucesso!');
    } catch (error) {
      alert('Erro ao iniciar aluguel. Tente novamente.');
    }
  };

  const handleFinalizar = async (id: number) => {
    try {
      await finalizarAluguel(id);
      alert('Aluguel finalizado com sucesso!');
    } catch (error) {
      alert('Erro ao finalizar aluguel. Tente novamente.');
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Tem certeza que deseja excluir este pedido?')) {
      try {
        await desativarPedido(id);
        alert('Pedido excluído com sucesso!');
      } catch (error) {
        alert('Erro ao excluir pedido. Tente novamente.');
      }
    }
  };

  const handleEdit = (pedido: any) => {
    openModal(pedido);
  };

  return (
    <ProtectedRoute>
      <div className="min-h-screen dark:bg-gray-dark relative z-10 overflow-hidden bg-white">
        {/* Conteúdo principal */}
        <div className="container mx-auto px-4 py-8 pt-40">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
              Sistema de Pedidos de Aluguel
            </h1>
            <p className="text-gray-600 dark:text-gray-400">
              Gerencie pedidos de aluguel de automóveis - Crie, visualize e gerencie o status dos pedidos
            </p>
            <div className="mt-2 px-3 py-1 bg-blue-100 dark:bg-blue-900 text-blue-800 dark:text-blue-200 rounded-full text-sm inline-block">
              🚗 Sistema de Aluguel de Carros
            </div>
          </div>

          {/* Estatísticas */}
          <PedidoStats 
            stats={stats} 
            error={statsError} 
            onRetry={clearStatsError} 
          />

          {/* Controles */}
          <PedidoControls
            searchTerm={searchTerm}
            onSearchChange={(value) => {
              setSearchTerm(value);
              searchPedidos(value);
            }}
            showInativos={showInativos}
            onShowInativosChange={setShowInativos}
            onNewPedido={() => openModal()}
            onFilterByStatus={setSelectedStatus}
            selectedStatus={selectedStatus}
          />

          {/* Tabela */}
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
            {loading ? (
              <LoadingSpinner />
            ) : (
              <>
                <PedidoTable
                  pedidos={filteredPedidos}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onAprovar={handleAprovar}
                  onRejeitar={handleRejeitar}
                  onIniciar={handleIniciar}
                  onFinalizar={handleFinalizar}
                  getStatusColor={getStatusColor}
                  getStatusIcon={getStatusIcon}
                  formatPlaca={formatPlaca}
                  isAdmin={isAdmin}
                />
                {filteredPedidos.length === 0 && (
                  <EmptyState message="Nenhum pedido encontrado" />
                )}
              </>
            )}
          </div>

          {/* Modal de Formulário */}
          {showModal && (
            <PedidoForm
              editingPedido={editingPedido}
              onSubmit={onSubmit}
              onClose={closeModal}
              register={register}
              handleSubmit={handleSubmit}
              errors={errors}
              clientes={clientes}
              automoveis={automoveis}
              error={error}
              onClearError={clearError}
            />
          )}

          {/* Modal de Rejeição */}
          {showRejectModal && (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
              <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl max-w-md w-full mx-4">
                <div className="p-6">
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-4">
                    Rejeitar Pedido
                  </h3>
                  <div className="mb-4">
                    <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                      Motivo da Rejeição *
                    </label>
                    <textarea
                      value={rejectMotivo}
                      onChange={(e) => setRejectMotivo(e.target.value)}
                      rows={3}
                      placeholder="Informe o motivo da rejeição..."
                      className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent dark:bg-gray-700 dark:text-white"
                    />
                  </div>
                  <div className="flex justify-end gap-3">
                    <button
                      onClick={() => {
                        setShowRejectModal(false);
                        setRejectPedidoId(null);
                        setRejectMotivo('');
                      }}
                      className="px-4 py-2 text-gray-700 dark:text-gray-300 bg-gray-200 dark:bg-gray-600 hover:bg-gray-300 dark:hover:bg-gray-500 rounded-lg transition-colors duration-200"
                    >
                      Cancelar
                    </button>
                    <button
                      onClick={confirmRejeitar}
                      className="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded-lg transition-colors duration-200"
                    >
                      Rejeitar
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>
        
        {/* Background decorativo */}
        <BackgroundDecoration />
      </div>
    </ProtectedRoute>
  );
}
