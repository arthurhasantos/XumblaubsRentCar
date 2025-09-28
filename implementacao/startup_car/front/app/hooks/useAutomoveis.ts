"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { api } from "@/lib/api";
import { Automovel, AutomovelRequest, TipoProprietario } from "@/app/types/automovel";

export function useAutomoveis() {
  const [automoveis, setAutomoveis] = useState<Automovel[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingAutomovel, setEditingAutomovel] = useState<Automovel | null>(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [showInativos, setShowInativos] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<AutomovelRequest>();

  useEffect(() => {
    fetchAutomoveis();
  }, [showInativos]); // eslint-disable-line react-hooks/exhaustive-deps

  const fetchAutomoveis = async () => {
    try {
      setLoading(true);
      console.log("🔄 Iniciando fetch de automóveis...");
      const data = await api.get(`/automoveis`);
      console.log("✅ Dados recebidos:", data);
      console.log("📊 Total de carros recebidos:", data.length);
      
      // Filtrar baseado no checkbox "Mostrar inativos"
      if (!showInativos) {
        const automoveisAtivos = data.filter((automovel: Automovel) => automovel.ativo);
        console.log("🔍 Carros ativos filtrados:", automoveisAtivos.length);
        setAutomoveis(automoveisAtivos);
      } else {
        console.log("📋 Mostrando todos os carros");
        setAutomoveis(data);
      }
    } catch (error) {
      console.error("❌ Erro ao carregar automóveis:", error);
      toast.error("Erro ao carregar automóveis");
    } finally {
      setLoading(false);
    }
  };

  const searchAutomoveis = async (termo: string) => {
    if (termo.trim()) {
      try {
        const data = await api.get(`/automoveis/buscar?marca=${encodeURIComponent(termo)}`);
        // Filtrar resultados baseado no checkbox "Mostrar inativos"
        if (!showInativos) {
          const automoveisAtivos = data.filter((automovel: Automovel) => automovel.ativo);
          setAutomoveis(automoveisAtivos);
        } else {
          setAutomoveis(data);
        }
      } catch (error) {
        toast.error("Erro ao buscar automóveis");
      }
    } else {
      fetchAutomoveis();
    }
  };

  const onSubmit = async (data: AutomovelRequest) => {
    try {
      if (editingAutomovel) {
        await api.put(`/automoveis/${editingAutomovel.id}`, data);
        toast.success("Automóvel atualizado com sucesso!");
      } else {
        await api.post("/automoveis", data);
        toast.success("Automóvel criado com sucesso!");
      }
      
      closeModal();
      fetchAutomoveis();
    } catch (error: any) {
      const errorMessage = error.response?.data?.message || "Erro ao salvar automóvel";
      toast.error(errorMessage);
    }
  };

  const handleEdit = (automovel: Automovel) => {
    setEditingAutomovel(automovel);
    setValue("matricula", automovel.matricula);
    setValue("ano", automovel.ano);
    setValue("marca", automovel.marca);
    setValue("modelo", automovel.modelo);
    setValue("placa", automovel.placa);
    setValue("tipoProprietario", automovel.tipoProprietario);
    setValue("proprietarioId", automovel.proprietarioId);
    setValue("proprietarioNome", automovel.proprietarioNome || "");
    setValue("observacoes", automovel.observacoes || "");
    setValue("ativo", automovel.ativo);
    setShowModal(true);
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Tem certeza que deseja deletar este automóvel?")) {
      try {
        await api.delete(`/automoveis/${id}`);
        toast.success("Automóvel deletado com sucesso!");
        fetchAutomoveis();
      } catch (error) {
        toast.error("Erro ao deletar automóvel");
      }
    }
  };

  const handleActivate = async (id: number, ativo: boolean) => {
    try {
      if (ativo) {
        await api.put(`/automoveis/${id}/desativar`);
        toast.success("Automóvel desativado com sucesso!");
      } else {
        await api.put(`/automoveis/${id}/ativar`);
        toast.success("Automóvel ativado com sucesso!");
      }
      fetchAutomoveis();
    } catch (error) {
      toast.error("Erro ao alterar status do automóvel");
    }
  };

  const openModal = () => {
    setEditingAutomovel(null);
    reset();
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingAutomovel(null);
    reset();
  };

  return {
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
  };
}
