"use client";

import { useClientes } from "@/app/hooks/useClientes";
import { formatCPF } from "@/app/utils/formatters";
import { ClienteControls, ClienteTable, ClienteForm, ClienteStats } from "@/app/components/clientes";
import { LoadingSpinner, EmptyState, BackgroundDecoration } from "@/app/components/shared";
import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";

export default function ClientesPage() {
  const {
    clientes,
    loading,
    showModal,
    editingCliente,
    searchTerm,
    showInativos,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchClientes,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  } = useClientes();

  return (
    <ProtectedRoute requireAdmin={true}>
      <div className="min-h-screen dark:bg-gray-dark relative z-10 overflow-hidden bg-white">
        {/* Conteúdo principal */}
        <div className="container mx-auto px-4 py-8 pt-40">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
              CRUD de Clientes - Sistema de Aluguel
            </h1>
            <p className="text-gray-600 dark:text-gray-400">
              Gerencie os clientes conforme especificação UML (RG, CPF, Nome, Endereço, Profissão)
            </p>
            <div className="mt-2 px-3 py-1 bg-green-100 dark:bg-green-900 text-green-800 dark:text-green-200 rounded-full text-sm inline-block">
              🔒 Acesso Restrito - Apenas Administradores
            </div>
          </div>

          {/* Controles */}
          <ClienteControls
            searchTerm={searchTerm}
            onSearchChange={(value) => {
              setSearchTerm(value);
              searchClientes(value);
            }}
            showInativos={showInativos}
            onShowInativosChange={setShowInativos}
            onNewCliente={openModal}
          />

          {/* Tabela */}
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
            {loading ? (
              <LoadingSpinner />
            ) : (
              <>
                <ClienteTable
                  clientes={clientes}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onActivate={handleActivate}
                  formatCPF={formatCPF}
                />
                {clientes.length === 0 && (
                  <EmptyState message="Nenhum cliente encontrado" />
                )}
              </>
            )}
          </div>

          {/* Modal */}
          {showModal && (
            <ClienteForm
              editingCliente={editingCliente}
              onSubmit={onSubmit}
              onClose={closeModal}
            />
          )}

          {/* Estatísticas */}
          <ClienteStats clientes={clientes} />
        </div>
        
        {/* Background decorativo */}
        <BackgroundDecoration />
      </div>
    </ProtectedRoute>
  );
}
