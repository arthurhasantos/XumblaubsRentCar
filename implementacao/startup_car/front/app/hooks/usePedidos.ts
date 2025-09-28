"use client";

import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { PedidoAluguelResponse, PedidoAluguelRequest, PedidoStats, StatusPedido } from '@/app/types/pedido';
import { ClienteResponse } from '@/app/types/cliente';
import { AutomovelResponse } from '@/app/types/automovel';
import { api } from '@/lib/api';
import { useAuth } from '@/contexts/AuthContext';

export const usePedidos = () => {
  const { user } = useAuth();
  const [pedidos, setPedidos] = useState<PedidoAluguelResponse[]>([]);
  const [clientes, setClientes] = useState<ClienteResponse[]>([]);
  const [automoveis, setAutomoveis] = useState<AutomovelResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingPedido, setEditingPedido] = useState<PedidoAluguelResponse | null>(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [showInativos, setShowInativos] = useState(false);
  const [stats, setStats] = useState<PedidoStats | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [statsError, setStatsError] = useState<string | null>(null);

  const {
    register,
    handleSubmit,
    reset,
    setValue,
    formState: { errors }
  } = useForm<PedidoAluguelRequest>();

  // Carregar dados iniciais
  useEffect(() => {
    if (user) {
      loadPedidos();
      loadClientes();
      loadAutomoveis();
      loadStats();
    }
  }, [user]);

  // Carregar pedidos
  const loadPedidos = async () => {
    try {
      setLoading(true);
      console.log('Carregando pedidos...');
      const response = await api.get('/pedidos');
      console.log('Pedidos carregados:', response);
      setPedidos(response || []);
    } catch (error) {
      console.error('Erro ao carregar pedidos:', error);
      setPedidos([]); // Garantir que sempre seja um array
    } finally {
      setLoading(false);
    }
  };

  // Carregar clientes
  const loadClientes = async () => {
    try {
      console.log('Carregando clientes...');
      const response = await api.get('/clientes');
      console.log('Clientes carregados:', response);
      setClientes((response || []).filter((cliente: any) => cliente.ativo));
    } catch (error) {
      console.error('Erro ao carregar clientes:', error);
      setClientes([]);
    }
  };

  // Carregar automóveis
  const loadAutomoveis = async () => {
    try {
      console.log('Carregando automóveis...');
      const response = await api.get('/automoveis');
      console.log('Automóveis carregados:', response);
      setAutomoveis((response || []).filter((automovel: any) => automovel.ativo));
    } catch (error) {
      console.error('Erro ao carregar automóveis:', error);
      setAutomoveis([]);
    }
  };

  // Carregar estatísticas
  const loadStats = async () => {
    try {
      setStatsError(null);
      const response = await api.get('/pedidos/estatisticas');
      setStats(response);
    } catch (error) {
      console.error('Erro ao carregar estatísticas:', error);
      setStats(null);
      setStatsError('Erro ao carregar estatísticas. Verifique sua conexão e tente novamente.');
    }
  };

  // Buscar pedidos
  const searchPedidos = async (term: string) => {
    try {
      setLoading(true);
      const response = await api.get(`/pedidos/filtros?search=${term}`);
      setPedidos(response || []);
    } catch (error) {
      console.error('Erro ao buscar pedidos:', error);
      setPedidos([]);
    } finally {
      setLoading(false);
    }
  };

  // Criar pedido
  const createPedido = async (data: PedidoAluguelRequest) => {
    try {
      setError(null);
      
      // Converter datas para formato ISO
      const pedidoData = {
        ...data,
        dataInicio: new Date(data.dataInicio + 'T00:00:00').toISOString(),
        dataFim: new Date(data.dataFim + 'T23:59:59').toISOString()
      };
      
      console.log('Dados do pedido sendo enviados:', pedidoData);
      const response = await api.post('/pedidos', pedidoData);
      setPedidos(prev => [response, ...(prev || [])]);
      loadStats();
      return response;
    } catch (error) {
      console.error('Erro ao criar pedido:', error);
      setError('Erro ao criar pedido. Verifique os dados e tente novamente.');
      throw error;
    }
  };

  // Atualizar pedido
  const updatePedido = async (id: number, data: PedidoAluguelRequest) => {
    try {
      setError(null);
      
      // Converter datas para formato ISO
      const pedidoData = {
        ...data,
        dataInicio: new Date(data.dataInicio + 'T00:00:00').toISOString(),
        dataFim: new Date(data.dataFim + 'T23:59:59').toISOString()
      };
      
      console.log('Dados do pedido sendo atualizados:', pedidoData);
      const response = await api.put(`/pedidos/${id}`, pedidoData);
      setPedidos(prev => prev?.map(pedido => 
        pedido.id === id ? response : pedido
      ) || []);
      loadStats();
      return response;
    } catch (error) {
      console.error('Erro ao atualizar pedido:', error);
      setError('Erro ao atualizar pedido. Verifique os dados e tente novamente.');
      throw error;
    }
  };

  // Aprovar pedido
  const aprovarPedido = async (id: number) => {
    try {
      const response = await api.put(`/pedidos/${id}/aprovar`, {});
      setPedidos(prev => (prev || []).map(pedido => 
        pedido.id === id ? response : pedido
      ));
      loadStats();
      return response;
    } catch (error) {
      console.error('Erro ao aprovar pedido:', error);
      throw error;
    }
  };

  // Rejeitar pedido
  const rejeitarPedido = async (id: number, motivo: string) => {
    try {
      const response = await api.put(`/pedidos/${id}/rejeitar`, { motivo });
      setPedidos(prev => (prev || []).map(pedido => 
        pedido.id === id ? response : pedido
      ));
      loadStats();
      return response;
    } catch (error) {
      console.error('Erro ao rejeitar pedido:', error);
      throw error;
    }
  };

  // Iniciar aluguel
  const iniciarAluguel = async (id: number) => {
    try {
      const response = await api.put(`/pedidos/${id}/iniciar`, {});
      setPedidos(prev => (prev || []).map(pedido => 
        pedido.id === id ? response : pedido
      ));
      loadStats();
      return response;
    } catch (error) {
      console.error('Erro ao iniciar aluguel:', error);
      throw error;
    }
  };

  // Finalizar aluguel
  const finalizarAluguel = async (id: number) => {
    try {
      const response = await api.put(`/pedidos/${id}/finalizar`, {});
      setPedidos(prev => (prev || []).map(pedido => 
        pedido.id === id ? response : pedido
      ));
      loadStats();
      return response;
    } catch (error) {
      console.error('Erro ao finalizar aluguel:', error);
      throw error;
    }
  };

  // Desativar pedido
  const desativarPedido = async (id: number) => {
    try {
      await api.delete(`/pedidos/${id}`);
      setPedidos(prev => (prev || []).filter(pedido => pedido.id !== id));
      loadStats();
    } catch (error) {
      console.error('Erro ao desativar pedido:', error);
      throw error;
    }
  };

  // Filtrar pedidos por status
  const filterByStatus = (status: StatusPedido) => {
    return pedidos?.filter(pedido => pedido.status === status) || [];
  };

  // Abrir modal para criar/editar
  const openModal = (pedido?: PedidoAluguelResponse) => {
    if (pedido) {
      setEditingPedido(pedido);
      setValue('clienteId', pedido.clienteId);
      setValue('automovelId', pedido.automovelId);
      setValue('dataInicio', pedido.dataInicio.split('T')[0]);
      setValue('dataFim', pedido.dataFim.split('T')[0]);
      setValue('observacoes', pedido.observacoes || '');
    } else {
      setEditingPedido(null);
      reset();
    }
    setShowModal(true);
  };

  // Fechar modal
  const closeModal = () => {
    setShowModal(false);
    setEditingPedido(null);
    reset();
  };

  // Submeter formulário
  const onSubmit = async (data: PedidoAluguelRequest) => {
    try {
      if (editingPedido) {
        await updatePedido(editingPedido.id, data);
      } else {
        await createPedido(data);
      }
      closeModal();
    } catch (error) {
      console.error('Erro ao submeter formulário:', error);
      // O erro já foi definido no createPedido ou updatePedido
    }
  };

  // Obter cor do status
  const getStatusColor = (status: StatusPedido) => {
    switch (status) {
      case StatusPedido.PENDENTE:
        return 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200';
      case StatusPedido.APROVADO:
        return 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200';
      case StatusPedido.REJEITADO:
        return 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200';
      case StatusPedido.EM_ANDAMENTO:
        return 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200';
      case StatusPedido.FINALIZADO:
        return 'bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200';
      default:
        return 'bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200';
    }
  };

  // Obter ícone do status
  const getStatusIcon = (status: StatusPedido) => {
    switch (status) {
      case StatusPedido.PENDENTE:
        return '⏳';
      case StatusPedido.APROVADO:
        return '✅';
      case StatusPedido.REJEITADO:
        return '❌';
      case StatusPedido.EM_ANDAMENTO:
        return '🚗';
      case StatusPedido.FINALIZADO:
        return '🏁';
      default:
        return '❓';
    }
  };

  return {
    // Estado
    pedidos,
    clientes,
    automoveis,
    loading,
    showModal,
    editingPedido,
    searchTerm,
    showInativos,
    stats,
    error,
    statsError,
    
    // Formulário
    register,
    handleSubmit,
    errors,
    
    // Ações
    setSearchTerm,
    setShowInativos,
    searchPedidos,
    onSubmit,
    openModal,
    closeModal,
    updatePedido,
    clearError: () => setError(null),
    clearStatsError: () => setStatsError(null),
    aprovarPedido,
    rejeitarPedido,
    iniciarAluguel,
    finalizarAluguel,
    desativarPedido,
    filterByStatus,
    loadPedidos,
    loadStats,
    
    // Utilitários
    getStatusColor,
    getStatusIcon
  };
};
