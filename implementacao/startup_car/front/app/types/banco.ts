export interface Banco {
  id: number;
  nome: string;
  codigo: string;
  cnpj: string;
  endereco?: string;
  telefone?: string;
  email?: string;
  taxaJurosPadrao?: number;
  limiteCreditoMaximo?: number;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface BancoFormData {
  nome: string;
  codigo: string;
  cnpj: string;
  endereco?: string;
  telefone?: string;
  email?: string;
  taxaJurosPadrao?: number;
  limiteCreditoMaximo?: number;
}
