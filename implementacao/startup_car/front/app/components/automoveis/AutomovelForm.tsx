"use client";

import { useState } from "react";
import { UseFormRegister, FieldErrors, UseFormHandleSubmit } from "react-hook-form";
import { Automovel, AutomovelRequest, TipoProprietario } from "@/app/types/automovel";

interface AutomovelFormProps {
  editingAutomovel: Automovel | null;
  onSubmit: (data: AutomovelRequest) => void;
  onClose: () => void;
  register: UseFormRegister<AutomovelRequest>;
  handleSubmit: UseFormHandleSubmit<AutomovelRequest>;
  errors: FieldErrors<AutomovelRequest>;
}

export function AutomovelForm({
  editingAutomovel,
  onSubmit,
  onClose,
  register,
  handleSubmit,
  errors,
}: AutomovelFormProps) {
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleFormSubmit = async (data: AutomovelRequest) => {
    setIsSubmitting(true);
    try {
      await onSubmit(data);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-xl w-full max-w-2xl max-h-[90vh] overflow-y-auto">
        <div className="p-6">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">
              {editingAutomovel ? "Editar Automóvel" : "Novo Automóvel"}
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

          <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              {/* Matrícula */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Matrícula *
                </label>
                <input
                  type="text"
                  {...register("matricula", { required: "Matrícula é obrigatória" })}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                  placeholder="Ex: MAT001"
                />
                {errors.matricula && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.matricula.message}</p>
                )}
              </div>

              {/* Ano */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Ano *
                </label>
                <input
                  type="number"
                  min="1900"
                  max="2030"
                  {...register("ano", { 
                    required: "Ano é obrigatório",
                    min: { value: 1900, message: "Ano deve ser maior que 1900" }
                  })}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                  placeholder="Ex: 2023"
                />
                {errors.ano && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.ano.message}</p>
                )}
              </div>

              {/* Marca */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Marca *
                </label>
                <input
                  type="text"
                  {...register("marca", { required: "Marca é obrigatória" })}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                  placeholder="Ex: Volkswagen"
                />
                {errors.marca && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.marca.message}</p>
                )}
              </div>

              {/* Modelo */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Modelo *
                </label>
                <input
                  type="text"
                  {...register("modelo", { required: "Modelo é obrigatório" })}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                  placeholder="Ex: Golf"
                />
                {errors.modelo && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.modelo.message}</p>
                )}
              </div>

              {/* Placa */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Placa *
                </label>
                <input
                  type="text"
                  {...register("placa", { 
                    required: "Placa é obrigatória",
                    pattern: {
                      value: /^[A-Z]{3}[0-9]{4}$|^[A-Z]{3}[0-9][A-Z][0-9]{2}$/,
                      message: "Placa deve estar no formato ABC1234 ou ABC1D23"
                    }
                  })}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                  placeholder="Ex: ABC1234"
                />
                {errors.placa && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.placa.message}</p>
                )}
              </div>

              {/* Tipo Proprietário */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Tipo Proprietário *
                </label>
                <select
                  {...register("tipoProprietario", { required: "Tipo de proprietário é obrigatório" })}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                >
                  <option value="">Selecione...</option>
                  <option value={TipoProprietario.CLIENTE}>Cliente</option>
                  <option value={TipoProprietario.EMPRESA}>Empresa</option>
                  <option value={TipoProprietario.BANCO}>Banco</option>
                </select>
                {errors.tipoProprietario && (
                  <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.tipoProprietario.message}</p>
                )}
              </div>

              {/* Proprietário Nome */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Nome do Proprietário
                </label>
                <input
                  type="text"
                  {...register("proprietarioNome")}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                  placeholder="Ex: Localiza"
                />
              </div>

              {/* Status */}
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                  Status
                </label>
                <select
                  {...register("ativo")}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                >
                  <option value="true">Ativo</option>
                  <option value="false">Inativo</option>
                </select>
              </div>
            </div>

            {/* Observações */}
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-2">
                Observações
              </label>
              <textarea
                {...register("observacoes")}
                rows={3}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                placeholder="Observações adicionais sobre o automóvel..."
              />
            </div>

            {/* Botões */}
            <div className="flex justify-end space-x-4 pt-6 border-t border-gray-200 dark:border-gray-700">
              <button
                type="button"
                onClick={onClose}
                className="px-6 py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-gray-700 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors duration-200"
              >
                Cancelar
              </button>
              <button
                type="submit"
                disabled={isSubmitting}
                className="px-6 py-2 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-400 text-white rounded-lg font-medium transition-colors duration-200 flex items-center gap-2"
              >
                {isSubmitting && (
                  <svg className="animate-spin h-4 w-4" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                )}
                {editingAutomovel ? "Atualizar" : "Criar"}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
