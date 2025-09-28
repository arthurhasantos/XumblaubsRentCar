"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { api } from "@/lib/api";
import { Banco, BancoFormData } from "@/app/types/banco";

export function useBancos() {
  const [bancos, setBancos] = useState<Banco[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingBanco, setEditingBanco] = useState<Banco | null>(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [showInativos, setShowInativos] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<BancoFormData>();

  useEffect(() => {
    fetchBancos();
  }, [showInativos]); // eslint-disable-line react-hooks/exhaustive-deps

  const fetchBancos = async () => {
    try {
      setLoading(true);
      const data = await api.get(`/bancos?incluirInativos=${showInativos}`);
      setBancos(data);
    } catch (error) {
      toast.error("Erro ao carregar bancos");
    } finally {
      setLoading(false);
    }
  };

  const searchBancos = async (nome: string) => {
    if (nome.trim()) {
      try {
        const data = await api.get(`/bancos/buscar?nome=${encodeURIComponent(nome)}`);
        // Filtrar resultados baseado no checkbox "Mostrar inativos"
        if (!showInativos) {
          const bancosAtivos = data.filter((banco: Banco) => banco.ativo);
          setBancos(bancosAtivos);
        } else {
          setBancos(data);
        }
      } catch (error) {
        toast.error("Erro ao buscar bancos");
      }
    } else {
      fetchBancos();
    }
  };

  const onSubmit = async (data: BancoFormData) => {
    try {
      if (editingBanco) {
        await api.put(`/bancos/${editingBanco.id}`, data);
        toast.success("Banco atualizado com sucesso!");
      } else {
        await api.post("/bancos", data);
        toast.success("Banco criado com sucesso!");
      }
      closeModal();
      fetchBancos();
    } catch (error) {
      toast.error("Erro ao salvar banco");
    }
  };

  const handleEdit = (banco: Banco) => {
    setEditingBanco(banco);
    setValue("nome", banco.nome);
    setValue("codigo", banco.codigo);
    setValue("cnpj", banco.cnpj);
        setValue("endereco", banco.endereco || "");
        setValue("telefone", banco.telefone || "");
        setValue("email", banco.email || "");
        setValue("senha", banco.senha || "");
    openModal();
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Tem certeza que deseja excluir este banco?")) {
      try {
        await api.delete(`/bancos/${id}`);
        toast.success("Banco excluído com sucesso!");
        fetchBancos();
      } catch (error) {
        toast.error("Erro ao excluir banco");
      }
    }
  };

  const handleActivate = async (id: number) => {
    try {
      await api.put(`/bancos/${id}/ativar`);
      toast.success("Banco ativado com sucesso!");
      fetchBancos();
    } catch (error) {
      toast.error("Erro ao ativar banco");
    }
  };

  const openModal = () => {
    setEditingBanco(null);
    reset();
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingBanco(null);
    reset();
  };

  return {
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
  };
}
