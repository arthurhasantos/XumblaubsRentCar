export interface Banco {
  id: number;
  nome: string;
  codigo: string;
  cnpj: string;
  endereco?: string;
  telefone?: string;
  email?: string;
  senha?: string;
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
  senha?: string;
}
