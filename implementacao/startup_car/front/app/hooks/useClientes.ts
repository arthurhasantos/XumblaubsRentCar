"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { api } from "@/lib/api";
import { Cliente, ClienteFormData } from "@/app/types/cliente";

export function useClientes() {
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
      const data = await api.get(`/clientes?incluirInativos=${showInativos}`);
      setClientes(data);
    } catch (error) {
      toast.error("Erro ao carregar clientes");
    } finally {
      setLoading(false);
    }
  };

  const searchClientes = async (nome: string) => {
    if (nome.trim()) {
      try {
        const data = await api.get(`/clientes/buscar?nome=${encodeURIComponent(nome)}`);
        // Filtrar resultados baseado no checkbox "Mostrar inativos"
        if (!showInativos) {
          const clientesAtivos = data.filter((cliente: Cliente) => cliente.ativo);
          setClientes(clientesAtivos);
        } else {
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
      if (editingCliente) {
        await api.put(`/clientes/${editingCliente.id}`, data);
        toast.success("Cliente atualizado com sucesso!");
      } else {
        await api.post("/clientes", data);
        toast.success("Cliente criado com sucesso!");
      }
      
      setShowModal(false);
      setEditingCliente(null);
      reset();
      fetchClientes();
    } catch (error) {
      toast.error("Erro ao salvar cliente");
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
        await api.delete(`/clientes/${id}`);
        toast.success("Cliente desativado com sucesso!");
        fetchClientes();
      } catch (error) {
        toast.error("Erro ao desativar cliente");
      }
    }
  };

  const handleActivate = async (id: number) => {
    try {
      await api.put(`/clientes/${id}/ativar`, {});
      toast.success("Cliente ativado com sucesso!");
      fetchClientes();
    } catch (error) {
      toast.error("Erro ao ativar cliente");
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

  return {
    // State
    clientes,
    loading,
    showModal,
    editingCliente,
    searchTerm,
    showInativos,
    
    // Form
    register,
    handleSubmit,
    errors,
    
    // Actions
    setSearchTerm,
    setShowInativos,
    searchClientes,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  };
}
