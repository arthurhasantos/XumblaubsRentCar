"use client";

import { Automovel, TipoProprietario } from "@/app/types/automovel";

interface AutomovelTableProps {
  automoveis: Automovel[];
  onEdit: (automovel: Automovel) => void;
  onDelete: (id: number) => void;
  onActivate: (id: number, ativo: boolean) => void;
  formatPlaca: (placa: string) => string;
}

export function AutomovelTable({
  automoveis,
  onEdit,
  onDelete,
  onActivate,
  formatPlaca,
}: AutomovelTableProps) {
  const getTipoProprietarioLabel = (tipo: TipoProprietario) => {
    switch (tipo) {
      case TipoProprietario.CLIENTE:
        return "Cliente";
      case TipoProprietario.EMPRESA:
        return "Empresa";
      case TipoProprietario.BANCO:
        return "Banco";
      default:
        return tipo;
    }
  };

  const getTipoProprietarioColor = (tipo: TipoProprietario) => {
    switch (tipo) {
      case TipoProprietario.CLIENTE:
        return "bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200";
      case TipoProprietario.EMPRESA:
        return "bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200";
      case TipoProprietario.BANCO:
        return "bg-purple-100 text-purple-800 dark:bg-purple-900 dark:text-purple-200";
      default:
        return "bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200";
    }
  };

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
        <thead className="bg-gray-50 dark:bg-gray-700">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Matrícula
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Marca/Modelo
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Ano
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Placa
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Tipo Proprietário
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Proprietário
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Status
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Ações
            </th>
          </tr>
        </thead>
        <tbody className="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
          {automoveis.map((automovel) => (
            <tr key={automovel.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
              <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900 dark:text-white">
                {automovel.matricula}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <div className="text-sm font-medium text-gray-900 dark:text-white">
                  {automovel.marca}
                </div>
                <div className="text-sm text-gray-500 dark:text-gray-400">
                  {automovel.modelo}
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {automovel.ano}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm font-mono text-gray-900 dark:text-white">
                {formatPlaca(automovel.placa)}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${getTipoProprietarioColor(automovel.tipoProprietario)}`}>
                  {getTipoProprietarioLabel(automovel.tipoProprietario)}
                </span>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {automovel.proprietarioNome || "-"}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                  automovel.ativo 
                    ? "bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200" 
                    : "bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200"
                }`}>
                  {automovel.ativo ? "Ativo" : "Inativo"}
                </span>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                <div className="flex space-x-2">
                  <button
                    onClick={() => onEdit(automovel)}
                    className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300"
                    title="Editar"
                  >
                    <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" />
                    </svg>
                  </button>
                  <button
                    onClick={() => onActivate(automovel.id, automovel.ativo)}
                    className={`${
                      automovel.ativo 
                        ? "text-orange-600 hover:text-orange-900 dark:text-orange-400 dark:hover:text-orange-300" 
                        : "text-green-600 hover:text-green-900 dark:text-green-400 dark:hover:text-green-300"
                    }`}
                    title={automovel.ativo ? "Desativar" : "Ativar"}
                  >
                    {automovel.ativo ? (
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18.364 18.364A9 9 0 005.636 5.636m12.728 12.728L5.636 5.636m12.728 12.728L18.364 5.636M5.636 18.364l12.728-12.728" />
                      </svg>
                    ) : (
                      <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                      </svg>
                    )}
                  </button>
                  <button
                    onClick={() => onDelete(automovel.id)}
                    className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                    title="Deletar"
                  >
                    <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
