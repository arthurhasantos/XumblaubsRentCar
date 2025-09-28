"use client";

import { useBancos } from "@/app/hooks/useBancos";
import { BancoControls, BancoTable, BancoForm, BancoStats } from "@/app/components/bancos";
import { LoadingSpinner, EmptyState, BackgroundDecoration } from "@/app/components/shared";
import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";

export default function BancosPage() {
  const {
    bancos,
    loading,
    showModal,
    editingBanco,
    searchTerm,
    showInativos,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchBancos,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  } = useBancos();

  return (
    <ProtectedRoute requireAdmin={true}>
      <div className="min-h-screen dark:bg-gray-dark relative z-10 overflow-hidden bg-white">
        {/* Conteúdo principal */}
        <div className="container mx-auto px-4 py-8 pt-40">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
              CRUD de Bancos - Sistema de Aluguel
            </h1>
            <p className="text-gray-600 dark:text-gray-400">
              Gerencie os bancos agentes do sistema de aluguel de carros
            </p>
            <div className="mt-2 px-3 py-1 bg-green-100 dark:bg-green-900 text-green-800 dark:text-green-200 rounded-full text-sm inline-block">
              🔒 Acesso Restrito - Apenas Administradores
            </div>
          </div>

          {/* Controles */}
          <BancoControls
            searchTerm={searchTerm}
            onSearchChange={(value) => {
              setSearchTerm(value);
              searchBancos(value);
            }}
            showInativos={showInativos}
            onShowInativosChange={setShowInativos}
            onNewBanco={openModal}
          />

          {/* Tabela */}
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
            {loading ? (
              <LoadingSpinner />
            ) : (
              <>
                <BancoTable
                  bancos={bancos}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onActivate={handleActivate}
                />
                {bancos.length === 0 && (
                  <EmptyState message="Nenhum banco encontrado" />
                )}
              </>
            )}
          </div>

          {/* Modal */}
          {showModal && (
            <BancoForm
              editingBanco={editingBanco}
              onSubmit={onSubmit}
              onClose={closeModal}
            />
          )}

          {/* Estatísticas */}
          <BancoStats bancos={bancos} />
        </div>
        
        {/* Background decorativo */}
        <BackgroundDecoration />
      </div>
    </ProtectedRoute>
  );
}
