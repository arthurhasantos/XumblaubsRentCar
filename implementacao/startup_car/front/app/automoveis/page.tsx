"use client";

import { useAutomoveis } from "@/app/hooks/useAutomoveis";
import { formatPlaca } from "@/app/utils/formatters";
import { AutomovelControls, AutomovelTable, AutomovelForm, AutomovelStats } from "@/app/components/automoveis";
import { LoadingSpinner, EmptyState, BackgroundDecoration } from "@/app/components/shared";
import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";

export default function AutomoveisPage() {
  const {
    automoveis,
    loading,
    showModal,
    editingAutomovel,
    searchTerm,
    showInativos,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchAutomoveis,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  } = useAutomoveis();

  return (
    <div className="min-h-screen dark:bg-gray-dark relative z-10 overflow-hidden bg-white">
        {/* Conteúdo principal */}
        <div className="container mx-auto px-4 py-8 pt-40">
          <div className="mb-8">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
              CRUD de Automóveis - Sistema de Aluguel
            </h1>
            <p className="text-gray-600 dark:text-gray-400">
              Gerencie a frota de automóveis conforme especificação UML (Matrícula, Ano, Marca, Modelo, Placa, Tipo Proprietário)
            </p>
            <div className="mt-2 px-3 py-1 bg-green-100 dark:bg-green-900 text-green-800 dark:text-green-200 rounded-full text-sm inline-block">
              🔒 Acesso Restrito - Apenas Administradores
            </div>
          </div>

          {/* Controles */}
          <AutomovelControls
            searchTerm={searchTerm}
            onSearchChange={(value) => {
              setSearchTerm(value);
              searchAutomoveis(value);
            }}
            showInativos={showInativos}
            onShowInativosChange={setShowInativos}
            onNewAutomovel={openModal}
          />

          {/* Tabela */}
          <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
            {loading ? (
              <LoadingSpinner />
            ) : (
              <>
                <AutomovelTable
                  automoveis={automoveis}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                  onActivate={handleActivate}
                  formatPlaca={formatPlaca}
                />
                {automoveis.length === 0 && (
                  <EmptyState message="Nenhum automóvel encontrado" />
                )}
              </>
            )}
          </div>

          {/* Modal */}
          {showModal && (
            <AutomovelForm
              editingAutomovel={editingAutomovel}
              onSubmit={onSubmit}
              onClose={closeModal}
              register={register}
              handleSubmit={handleSubmit}
              errors={errors}
            />
          )}

          {/* Estatísticas */}
          <AutomovelStats automoveis={automoveis} />
        </div>
        
        {/* Background decorativo */}
        <BackgroundDecoration />
      </div>
  );
}
