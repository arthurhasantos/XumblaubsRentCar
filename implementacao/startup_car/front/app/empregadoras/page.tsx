"use client";

import { useEmpregadoras } from "@/app/hooks/useEmpregadoras";
import { EmpregadoraControls, EmpregadoraTable, EmpregadoraForm, EmpregadoraStats } from "@/app/components/empregadoras";
import { LoadingSpinner, EmptyState, BackgroundDecoration } from "@/app/components/shared";
import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";

export default function EmpregadorasPage() {
  const {
    empregadoras,
    loading,
    showModal,
    editingEmpregadora,
    searchTerm,
    showInativos,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchEmpregadoras,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  } = useEmpregadoras();

  return (
    <ProtectedRoute requireAdmin={true}>
      <div className="min-h-screen dark:bg-gray-dark relative z-10 overflow-hidden bg-white">
        {/* Conteúdo principal */}
        <div className="container mx-auto px-4 py-8 pt-40">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
              CRUD de Empregadoras - Sistema de Aluguel
            </h1>
            <p className="text-gray-600 dark:text-gray-400">
              Gerencie as empregadoras dos clientes (máximo 3 por cliente)
            </p>
            <div className="mt-2 px-3 py-1 bg-green-100 dark:bg-green-900 text-green-800 dark:text-green-200 rounded-full text-sm inline-block">
              🔒 Acesso Restrito - Apenas Administradores
            </div>
          </div>

          {/* Controles */}
          <EmpregadoraControls
            searchTerm={searchTerm}
            onSearchChange={(value) => {
              setSearchTerm(value);
              searchEmpregadoras(value);
            }}
            showInativos={showInativos}
            onShowInativosChange={setShowInativos}
            onNewEmpregadora={openModal}
          />

          {/* Tabela */}
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
            {loading ? (
              <LoadingSpinner />
            ) : (
              <>
                <EmpregadoraTable
                  empregadoras={empregadoras}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onActivate={handleActivate}
                />
                {empregadoras.length === 0 && (
                  <EmptyState message="Nenhuma empregadora encontrada" />
                )}
              </>
            )}
          </div>

          {/* Modal */}
          {showModal && (
            <EmpregadoraForm
              editingEmpregadora={editingEmpregadora}
              onSubmit={onSubmit}
              onClose={closeModal}
            />
          )}

          {/* Estatísticas */}
          <EmpregadoraStats empregadoras={empregadoras} />
        </div>
        
        {/* Background decorativo */}
        <BackgroundDecoration />
      </div>
    </ProtectedRoute>
  );
}