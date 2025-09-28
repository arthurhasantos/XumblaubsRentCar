export interface ContratoCredito {
  id: number;
  numero: string;
  valorTotal: number;
  prazoMeses: number;
  bancoId: number;
  bancoNome: string;
  clienteId: number;
  clienteNome: string;
  status: StatusContratoCredito;
  taxaJuros?: number;
  valorParcela?: number;
  observacoes?: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface ContratoCreditoFormData {
  numero: string;
  valorTotal: number;
  prazoMeses: number;
  bancoId: number;
  clienteId: number;
  taxaJuros?: number;
  observacoes?: string;
}

export enum StatusContratoCredito {
  PENDENTE = "PENDENTE",
  ATIVO = "ATIVO",
  QUITADO = "QUITADO",
  CANCELADO = "CANCELADO",
  VENCIDO = "VENCIDO"
}

export const StatusContratoCreditoLabels = {
  [StatusContratoCredito.PENDENTE]: "Pendente",
  [StatusContratoCredito.ATIVO]: "Ativo",
  [StatusContratoCredito.QUITADO]: "Quitado",
  [StatusContratoCredito.CANCELADO]: "Cancelado",
  [StatusContratoCredito.VENCIDO]: "Vencido"
};

export const StatusContratoCreditoColors = {
  [StatusContratoCredito.PENDENTE]: "bg-yellow-100 text-yellow-800",
  [StatusContratoCredito.ATIVO]: "bg-green-100 text-green-800",
  [StatusContratoCredito.QUITADO]: "bg-blue-100 text-blue-800",
  [StatusContratoCredito.CANCELADO]: "bg-red-100 text-red-800",
  [StatusContratoCredito.VENCIDO]: "bg-gray-100 text-gray-800"
};
