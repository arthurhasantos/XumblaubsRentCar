export interface PedidoAluguel {
  id: number;
  clienteId: number;
  clienteNome: string;
  clienteCpf: string;
  automovelId: number;
  automovelMarca: string;
  automovelModelo: string;
  automovelPlaca: string;
  dataInicio: string;
  dataFim: string;
  status: StatusPedido;
  statusDescricao: string;
  valorTotal: number;
  observacoes?: string;
  motivoRejeicao?: string;
  contratoCreditoId?: number;
  contratoCreditoNumero?: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
  diasAluguel: number;
}

export enum StatusPedido {
  PENDENTE = 'PENDENTE',
  APROVADO = 'APROVADO',
  REJEITADO = 'REJEITADO',
  EM_ANDAMENTO = 'EM_ANDAMENTO',
  FINALIZADO = 'FINALIZADO'
}

export interface PedidoAluguelRequest {
  clienteId: number;
  automovelId: number;
  dataInicio: string;
  dataFim: string;
  observacoes?: string;
  contratoCreditoId?: number;
}

export interface PedidoAluguelResponse {
  id: number;
  clienteId: number;
  clienteNome: string;
  clienteCpf: string;
  automovelId: number;
  automovelMarca: string;
  automovelModelo: string;
  automovelPlaca: string;
  dataInicio: string;
  dataFim: string;
  status: StatusPedido;
  statusDescricao: string;
  valorTotal: number;
  observacoes?: string;
  motivoRejeicao?: string;
  contratoCreditoId?: number;
  contratoCreditoNumero?: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
  diasAluguel: number;
}

export interface PedidoStats {
  totalPedidos: number;
  pedidosPendentes: number;
  pedidosAprovados: number;
  pedidosRejeitados: number;
  pedidosEmAndamento: number;
  pedidosFinalizados: number;
}

export interface Cliente {
  id: number;
  nome: string;
  cpf: string;
  rg: string;
  endereco: string;
  profissao: string;
  ativo: boolean;
}

export interface Automovel {
  id: number;
  matricula: string;
  ano: number;
  marca: string;
  modelo: string;
  placa: string;
  tipoProprietario: string;
  proprietarioNome: string;
  ativo: boolean;
}
