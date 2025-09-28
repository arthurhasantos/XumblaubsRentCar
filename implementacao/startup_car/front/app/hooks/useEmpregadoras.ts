"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { api } from "@/lib/api";
import { Empregadora, EmpregadoraFormData } from "@/app/types/empregadora";

export function useEmpregadoras() {
  const [empregadoras, setEmpregadoras] = useState<Empregadora[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingEmpregadora, setEditingEmpregadora] = useState<Empregadora | null>(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [showInativos, setShowInativos] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<EmpregadoraFormData>();

  useEffect(() => {
    fetchEmpregadoras();
  }, [showInativos]); // eslint-disable-line react-hooks/exhaustive-deps

  const fetchEmpregadoras = async () => {
    try {
      setLoading(true);
      const data = await api.get(`/empregadoras?incluirInativos=${showInativos}`);
      setEmpregadoras(data);
    } catch (error) {
      toast.error("Erro ao carregar empregadoras");
    } finally {
      setLoading(false);
    }
  };

  const searchEmpregadoras = async (nome: string) => {
    if (nome.trim()) {
      try {
        const data = await api.get(`/empregadoras/buscar?nome=${encodeURIComponent(nome)}`);
        // Filtrar resultados baseado no checkbox "Mostrar inativos"
        if (!showInativos) {
          const empregadorasAtivas = data.filter((empregadora: Empregadora) => empregadora.ativo);
          setEmpregadoras(empregadorasAtivas);
        } else {
          setEmpregadoras(data);
        }
      } catch (error) {
        toast.error("Erro ao buscar empregadoras");
      }
    } else {
      fetchEmpregadoras();
    }
  };

  const onSubmit = async (data: EmpregadoraFormData) => {
    try {
      if (editingEmpregadora) {
        await api.put(`/empregadoras/${editingEmpregadora.id}`, data);
        toast.success("Empregadora atualizada com sucesso!");
      } else {
        await api.post("/empregadoras", data);
        toast.success("Empregadora criada com sucesso!");
      }
      closeModal();
      fetchEmpregadoras();
    } catch (error) {
      toast.error("Erro ao salvar empregadora");
    }
  };

  const handleEdit = (empregadora: Empregadora) => {
    setEditingEmpregadora(empregadora);
    setValue("clienteId", empregadora.clienteId);
    setValue("nome", empregadora.nome);
    setValue("rendimento", empregadora.rendimento);
    setValue("cargo", empregadora.cargo || "");
    setValue("cnpj", empregadora.cnpj || "");
    setValue("endereco", empregadora.endereco || "");
    setValue("email", empregadora.email || "");
    setValue("senha", empregadora.senha || "");
    setValue("telefone", empregadora.telefone || "");
    setValue("dataAdmissao", empregadora.dataAdmissao ? empregadora.dataAdmissao.split('T')[0] : "");
    openModal();
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Tem certeza que deseja excluir esta empregadora?")) {
      try {
        await api.delete(`/empregadoras/${id}`);
        toast.success("Empregadora excluída com sucesso!");
        fetchEmpregadoras();
      } catch (error) {
        toast.error("Erro ao excluir empregadora");
      }
    }
  };

  const handleActivate = async (id: number) => {
    try {
      await api.put(`/empregadoras/${id}/ativar`);
      toast.success("Empregadora ativada com sucesso!");
      fetchEmpregadoras();
    } catch (error) {
      toast.error("Erro ao ativar empregadora");
    }
  };

  const openModal = () => {
    setEditingEmpregadora(null);
    reset();
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingEmpregadora(null);
    reset();
  };

  return {
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
  };
}