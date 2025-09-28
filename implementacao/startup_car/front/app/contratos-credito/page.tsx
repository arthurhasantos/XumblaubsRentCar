"use client";

import { useContratosCredito } from "@/app/hooks/useContratosCredito";
import { ContratoCreditoControls, ContratoCreditoTable, ContratoCreditoForm, ContratoCreditoStats } from "@/app/components/contratos-credito";
import { LoadingSpinner, EmptyState, BackgroundDecoration } from "@/app/components/shared";
import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";

export default function ContratosCreditoPage() {
  const {
    contratos,
    loading,
    showModal,
    editingContrato,
    searchTerm,
    showInativos,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchContratos,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  } = useContratosCredito();

  return (
    <ProtectedRoute requireAdmin={true}>
      <div className="min-h-screen dark:bg-gray-dark relative z-10 overflow-hidden bg-white">
        {/* Conteúdo principal */}
        <div className="container mx-auto px-4 py-8 pt-40">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
              CRUD de Contratos de Crédito - Sistema de Aluguel
            </h1>
            <p className="text-gray-600 dark:text-gray-400">
              Gerencie os contratos de crédito para financiamento de aluguéis
            </p>
            <div className="mt-2 px-3 py-1 bg-green-100 dark:bg-green-900 text-green-800 dark:text-green-200 rounded-full text-sm inline-block">
              🔒 Acesso Restrito - Apenas Administradores
            </div>
          </div>

          {/* Controles */}
          <ContratoCreditoControls
            searchTerm={searchTerm}
            onSearchChange={(value) => {
              setSearchTerm(value);
              searchContratos(value);
            }}
            showInativos={showInativos}
            onShowInativosChange={setShowInativos}
            onNewContrato={openModal}
          />

          {/* Tabela */}
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
            {loading ? (
              <LoadingSpinner />
            ) : (
              <>
                <ContratoCreditoTable
                  contratos={contratos}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onActivate={handleActivate}
                />
                {contratos.length === 0 && (
                  <EmptyState message="Nenhum contrato encontrado" />
                )}
              </>
            )}
          </div>

          {/* Modal */}
          {showModal && (
            <ContratoCreditoForm
              editingContrato={editingContrato}
              onSubmit={onSubmit}
              onClose={closeModal}
            />
          )}

          {/* Estatísticas */}
          <ContratoCreditoStats contratos={contratos} />
        </div>
        
        {/* Background decorativo */}
        <BackgroundDecoration />
      </div>
    </ProtectedRoute>
  );
}
