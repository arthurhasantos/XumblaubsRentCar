"use client";

import { useForm } from "react-hook-form";
import { ContratoCredito, ContratoCreditoFormData } from "@/app/types/contrato-credito";

interface ContratoCreditoFormProps {
  editingContrato: ContratoCredito | null;
  onSubmit: (data: ContratoCreditoFormData) => void;
  onClose: () => void;
}

export default function ContratoCreditoForm({ editingContrato, onSubmit, onClose }: ContratoCreditoFormProps) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ContratoCreditoFormData>();

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white dark:bg-gray-800 rounded-lg p-6 w-full max-w-2xl mx-4 max-h-[90vh] overflow-y-auto">
        <h2 className="text-xl font-bold mb-4 text-gray-900 dark:text-white">
          {editingContrato ? "Editar Contrato" : "Novo Contrato de Crédito"}
        </h2>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Número do Contrato *
              </label>
              <input
                {...register("numero", { required: "Número é obrigatório" })}
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
                placeholder="Número do contrato"
              />
              {errors.numero && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.numero.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Cliente ID *
              </label>
              <input
                {...register("clienteId", { required: "Cliente é obrigatório", valueAsNumber: true })}
                type="number"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
                placeholder="ID do cliente"
              />
              {errors.clienteId && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.clienteId.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Banco ID *
              </label>
              <input
                {...register("bancoId", { required: "Banco é obrigatório", valueAsNumber: true })}
                type="number"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
                placeholder="ID do banco"
              />
              {errors.bancoId && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.bancoId.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Valor Total *
              </label>
              <input
                {...register("valorTotal", { required: "Valor é obrigatório", valueAsNumber: true })}
                type="number"
                step="0.01"
                min="0.01"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
                placeholder="0.00"
              />
              {errors.valorTotal && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.valorTotal.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Prazo (meses) *
              </label>
              <input
                {...register("prazoMeses", { required: "Prazo é obrigatório", valueAsNumber: true })}
                type="number"
                min="1"
                max="120"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
                placeholder="12"
              />
              {errors.prazoMeses && (
                <p className="mt-1 text-sm text-red-600 dark:text-red-400">{errors.prazoMeses.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                Taxa de Juros (%)
              </label>
              <input
                {...register("taxaJuros", { valueAsNumber: true })}
                type="number"
                step="0.01"
                min="0"
                max="100"
                className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
                placeholder="0.00"
              />
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
              Observações
            </label>
            <textarea
              {...register("observacoes")}
              rows={3}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:text-white"
              placeholder="Observações sobre o contrato"
            />
          </div>

          <div className="flex justify-end space-x-3 pt-4">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-md shadow-sm text-sm font-medium text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Cancelar
            </button>
            <button
              type="submit"
              className="px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              {editingContrato ? "Atualizar" : "Criar"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
