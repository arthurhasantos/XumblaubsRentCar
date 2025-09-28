"use client";

import { Empregadora } from "@/app/types/empregadora";

interface EmpregadoraTableProps {
  empregadoras: Empregadora[];
  onEdit: (empregadora: Empregadora) => void;
  onDelete: (id: number) => void;
  onActivate: (id: number) => void;
}

export default function EmpregadoraTable({ 
  empregadoras, 
  onEdit, 
  onDelete, 
  onActivate 
}: EmpregadoraTableProps) {
  return (
    <div className="overflow-x-auto">
      <table className="w-full">
        <thead className="bg-gray-50 dark:bg-gray-700">
          <tr>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              ID
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Nome
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Cliente
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Rendimento
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
              Cargo
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
          {empregadoras.map((empregadora) => (
            <tr key={empregadora.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {empregadora.id}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <div className="text-sm font-medium text-gray-900 dark:text-white">
                  {empregadora.nome}
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {empregadora.clienteNome}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                R$ {empregadora.rendimento.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900 dark:text-white">
                {empregadora.cargo || 'N/A'}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <span className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                  empregadora.ativo 
                    ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' 
                    : 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
                }`}>
                  {empregadora.ativo ? 'Ativo' : 'Inativo'}
                </span>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                <div className="flex space-x-2">
                  <button
                    onClick={() => onEdit(empregadora)}
                    className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300"
                  >
                    Editar
                  </button>
                  {empregadora.ativo ? (
                    <button
                      onClick={() => onDelete(empregadora.id)}
                      className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                    >
                      Desativar
                    </button>
                  ) : (
                    <button
                      onClick={() => onActivate(empregadora.id)}
                      className="text-green-600 hover:text-green-900 dark:text-green-400 dark:hover:text-green-300"
                    >
                      Ativar
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
}