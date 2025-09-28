"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";

interface Cliente {
  id: number;
  rg: string;
  cpf: string;
  nome: string;
  endereco: string;
  profissao: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

interface ClienteFormData {
  rg: string;
  cpf: string;
  nome: string;
  endereco: string;
  profissao: string;
}

const API_BASE_URL = "http://localhost:8080/api";

export default function ClientesPage() {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingCliente, setEditingCliente] = useState<Cliente | null>(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [showInativos, setShowInativos] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<ClienteFormData>();

  useEffect(() => {
    fetchClientes();
  }, [showInativos]); // eslint-disable-line react-hooks/exhaustive-deps

  const fetchClientes = async () => {
    try {
      setLoading(true);
      const response = await fetch(
        `${API_BASE_URL}/clientes?incluirInativos=${showInativos}`
      );
      if (response.ok) {
        const data = await response.json();
        setClientes(data);
      } else {
        toast.error("Erro ao carregar clientes");
      }
    } catch (error) {
      toast.error("Erro ao conectar com o servidor");
    } finally {
      setLoading(false);
    }
  };

  const searchClientes = async (nome: string) => {
    if (nome.trim()) {
      try {
        const response = await fetch(
          `${API_BASE_URL}/clientes/buscar?nome=${encodeURIComponent(nome)}`
        );
        if (response.ok) {
          const data = await response.json();
          setClientes(data);
        }
      } catch (error) {
        toast.error("Erro ao buscar clientes");
      }
    } else {
      fetchClientes();
    }
  };

  const onSubmit = async (data: ClienteFormData) => {
    try {
      const url = editingCliente
        ? `${API_BASE_URL}/clientes/${editingCliente.id}`
        : `${API_BASE_URL}/clientes`;
      
      const method = editingCliente ? "PUT" : "POST";
      
      const response = await fetch(url, {
        method,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (response.ok) {
        toast.success(
          editingCliente ? "Cliente atualizado com sucesso!" : "Cliente criado com sucesso!"
        );
        setShowModal(false);
        setEditingCliente(null);
        reset();
        fetchClientes();
      } else {
        const errorData = await response.json();
        toast.error(errorData.message || "Erro ao salvar cliente");
      }
    } catch (error) {
      toast.error("Erro ao conectar com o servidor");
    }
  };

  const handleEdit = (cliente: Cliente) => {
    setEditingCliente(cliente);
    setValue("rg", cliente.rg);
    setValue("cpf", cliente.cpf);
    setValue("nome", cliente.nome);
    setValue("endereco", cliente.endereco);
    setValue("profissao", cliente.profissao);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (confirm("Tem certeza que deseja desativar este cliente?")) {
      try {
        const response = await fetch(`${API_BASE_URL}/clientes/${id}`, {
          method: "DELETE",
        });

        if (response.ok) {
          toast.success("Cliente desativado com sucesso!");
          fetchClientes();
        } else {
          toast.error("Erro ao desativar cliente");
        }
      } catch (error) {
        toast.error("Erro ao conectar com o servidor");
      }
    }
  };

  const handleActivate = async (id: number) => {
    try {
      const response = await fetch(`${API_BASE_URL}/clientes/${id}/ativar`, {
        method: "PUT",
      });

      if (response.ok) {
        toast.success("Cliente ativado com sucesso!");
        fetchClientes();
      } else {
        toast.error("Erro ao ativar cliente");
      }
    } catch (error) {
      toast.error("Erro ao conectar com o servidor");
    }
  };

  const openModal = () => {
    setEditingCliente(null);
    reset();
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingCliente(null);
    reset();
  };

  const formatCPF = (cpf: string) => {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 dark:text-white mb-2">
          CRUD de Clientes - Sistema de Aluguel
        </h1>
        <p className="text-gray-600 dark:text-gray-400">
          Gerencie os clientes conforme especificação UML (RG, CPF, Nome, Endereço, Profissão)
        </p>
      </div>

      {/* Controles */}
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 mb-6">
        <div className="flex flex-col md:flex-row gap-4 items-center justify-between">
          <div className="flex flex-col md:flex-row gap-4 flex-1">
            <div className="relative flex-1">
              <input
                type="text"
                placeholder="Buscar por nome..."
                value={searchTerm}
                onChange={(e) => {
                  setSearchTerm(e.target.value);
                  searchClientes(e.target.value);
                }}
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
              />
            </div>
            <label className="flex items-center space-x-2">
              <input
                type="checkbox"
                checked={showInativos}
                onChange={(e) => setShowInativos(e.target.checked)}
                className="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
              />
              <span className="text-sm text-gray-700 dark:text-gray-300">
                Mostrar inativos
              </span>
            </label>
          </div>
          <button
            onClick={openModal}
            className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg font-medium transition-colors"
          >
            + Novo Cliente
          </button>
        </div>
      </div>

      {/* Tabela */}
      <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
        {loading ? (
          <div className="p-8 text-center">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600 mx-auto"></div>
            <p className="mt-2 text-gray-600 dark:text-gray-400">Carregando...</p>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50 dark:bg-gray-700">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                    ID
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                    RG
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                    CPF
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                    Nome
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                    Endereço
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">
                    Profissão
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
                {clientes.map((cliente) => (
                  <tr key={cliente.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm font-medium text-gray-900 dark:text-white">
                        {cliente.id}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900 dark:text-white">
                        {cliente.rg}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900 dark:text-white">
                        {formatCPF(cliente.cpf)}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm font-medium text-gray-900 dark:text-white">
                        {cliente.nome}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900 dark:text-white">
                        {cliente.endereco}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <div className="text-sm text-gray-900 dark:text-white">
                        {cliente.profissao}
                      </div>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span
                        className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                          cliente.ativo
                            ? "bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200"
                            : "bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200"
                        }`}
                      >
                        {cliente.ativo ? "Ativo" : "Inativo"}
                      </span>
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div className="flex space-x-2">
                        <button
                          onClick={() => handleEdit(cliente)}
                          className="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300"
                        >
                          Editar
                        </button>
                        {cliente.ativo ? (
                          <button
                            onClick={() => handleDelete(cliente.id)}
                            className="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300"
                          >
                            Desativar
                          </button>
                        ) : (
                          <button
                            onClick={() => handleActivate(cliente.id)}
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
            {clientes.length === 0 && (
              <div className="p-8 text-center text-gray-500 dark:text-gray-400">
                Nenhum cliente encontrado
              </div>
            )}
          </div>
        )}
      </div>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white dark:bg-gray-800 rounded-lg p-6 w-full max-w-md mx-4">
            <h2 className="text-xl font-bold mb-4 text-gray-900 dark:text-white">
              {editingCliente ? "Editar Cliente" : "Novo Cliente"}
            </h2>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  RG *
                </label>
                <input
                  {...register("rg", { required: "RG é obrigatório" })}
                  placeholder="1234567890"
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.rg && (
                  <p className="text-red-500 text-sm mt-1">{errors.rg.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  CPF *
                </label>
                <input
                  {...register("cpf", {
                    required: "CPF é obrigatório",
                    pattern: {
                      value: /^\d{3}\.\d{3}\.\d{3}-\d{2}$/,
                      message: "CPF deve estar no formato 000.000.000-00",
                    },
                  })}
                  placeholder="000.000.000-00"
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.cpf && (
                  <p className="text-red-500 text-sm mt-1">{errors.cpf.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Nome *
                </label>
                <input
                  {...register("nome", { required: "Nome é obrigatório" })}
                  placeholder="Nome completo"
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.nome && (
                  <p className="text-red-500 text-sm mt-1">{errors.nome.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Endereço *
                </label>
                <textarea
                  {...register("endereco", { required: "Endereço é obrigatório" })}
                  placeholder="Endereço completo"
                  rows={3}
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.endereco && (
                  <p className="text-red-500 text-sm mt-1">{errors.endereco.message}</p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
                  Profissão *
                </label>
                <input
                  {...register("profissao", { required: "Profissão é obrigatória" })}
                  placeholder="Profissão"
                  className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
                />
                {errors.profissao && (
                  <p className="text-red-500 text-sm mt-1">{errors.profissao.message}</p>
                )}
              </div>

              <div className="flex justify-end space-x-3 pt-4">
                <button
                  type="button"
                  onClick={closeModal}
                  className="px-4 py-2 text-gray-700 dark:text-gray-300 bg-gray-200 dark:bg-gray-600 rounded-lg hover:bg-gray-300 dark:hover:bg-gray-500 transition-colors"
                >
                  Cancelar
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
                >
                  {editingCliente ? "Atualizar" : "Criar"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Estatísticas */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">
        <div className="bg-blue-100 dark:bg-blue-900/20 p-6 rounded-lg">
          <h3 className="text-lg font-semibold text-blue-800 dark:text-blue-400 mb-2">Total de Clientes</h3>
          <p className="text-3xl font-bold text-blue-800 dark:text-blue-400">{clientes.length}</p>
        </div>
        <div className="bg-green-100 dark:bg-green-900/20 p-6 rounded-lg">
          <h3 className="text-lg font-semibold text-green-800 dark:text-green-400 mb-2">Clientes Ativos</h3>
          <p className="text-3xl font-bold text-green-800 dark:text-green-400">
            {clientes.filter(c => c.ativo).length}
          </p>
        </div>
        <div className="bg-purple-100 dark:bg-purple-900/20 p-6 rounded-lg">
          <h3 className="text-lg font-semibold text-purple-800 dark:text-purple-400 mb-2">Sistema Online</h3>
          <p className="text-3xl font-bold text-purple-800 dark:text-purple-400">✓</p>
        </div>
      </div>
    </div>
  );
}
