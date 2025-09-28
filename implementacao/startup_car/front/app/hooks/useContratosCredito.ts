"use client";

import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import toast from "react-hot-toast";
import { api } from "@/lib/api";
import { ContratoCredito, ContratoCreditoFormData } from "@/app/types/contrato-credito";

export function useContratosCredito() {
  const [contratos, setContratos] = useState<ContratoCredito[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingContrato, setEditingContrato] = useState<ContratoCredito | null>(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [showInativos, setShowInativos] = useState(false);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors },
  } = useForm<ContratoCreditoFormData>();

  useEffect(() => {
    fetchContratos();
  }, [showInativos]); // eslint-disable-line react-hooks/exhaustive-deps

  const fetchContratos = async () => {
    try {
      setLoading(true);
      const data = await api.get(`/contratos-credito?incluirInativos=${showInativos}`);
      setContratos(data);
    } catch (error) {
      toast.error("Erro ao carregar contratos");
    } finally {
      setLoading(false);
    }
  };

  const searchContratos = async (numero: string) => {
    if (numero.trim()) {
      try {
        const data = await api.get(`/contratos-credito/buscar?numero=${encodeURIComponent(numero)}`);
        // Filtrar resultados baseado no checkbox "Mostrar inativos"
        if (!showInativos) {
          const contratosAtivos = data.filter((contrato: ContratoCredito) => contrato.ativo);
          setContratos(contratosAtivos);
        } else {
          setContratos(data);
        }
      } catch (error) {
        toast.error("Erro ao buscar contratos");
      }
    } else {
      fetchContratos();
    }
  };

  const onSubmit = async (data: ContratoCreditoFormData) => {
    try {
      if (editingContrato) {
        await api.put(`/contratos-credito/${editingContrato.id}`, data);
        toast.success("Contrato atualizado com sucesso!");
      } else {
        await api.post("/contratos-credito", data);
        toast.success("Contrato criado com sucesso!");
      }
      closeModal();
      fetchContratos();
    } catch (error) {
      toast.error("Erro ao salvar contrato");
    }
  };

  const handleEdit = (contrato: ContratoCredito) => {
    setEditingContrato(contrato);
    setValue("numero", contrato.numero);
    setValue("clienteId", contrato.clienteId);
    setValue("bancoId", contrato.bancoId);
    setValue("valorTotal", contrato.valorTotal);
    setValue("prazoMeses", contrato.prazoMeses);
    setValue("taxaJuros", contrato.taxaJuros || 0);
    setValue("observacoes", contrato.observacoes || "");
    openModal();
  };

  const handleDelete = async (id: number) => {
    if (window.confirm("Tem certeza que deseja excluir este contrato?")) {
      try {
        await api.delete(`/contratos-credito/${id}`);
        toast.success("Contrato excluído com sucesso!");
        fetchContratos();
      } catch (error) {
        toast.error("Erro ao excluir contrato");
      }
    }
  };

  const handleActivate = async (id: number) => {
    try {
      await api.put(`/contratos-credito/${id}/aprovar`);
      toast.success("Contrato aprovado com sucesso!");
      fetchContratos();
    } catch (error) {
      toast.error("Erro ao aprovar contrato");
    }
  };

  const openModal = () => {
    setEditingContrato(null);
    reset();
    setShowModal(true);
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingContrato(null);
    reset();
  };

  return {
    contratos,
    loading,
    showModal,
    editingContrato,
    searchTerm,
    showInativos,
    register,
    handleSubmit,
    errors,
    setSearchTerm,
    setShowInativos,
    searchContratos,
    onSubmit,
    handleEdit,
    handleDelete,
    handleActivate,
    openModal,
    closeModal,
  };
}
