export interface Empregadora {
  id: number;
  nome: string;
  rendimento: number;
  clienteId: number;
  clienteNome: string;
  endereco?: string;
  telefone?: string;
  email?: string;
  cnpj?: string;
  cargo?: string;
  dataAdmissao?: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface EmpregadoraFormData {
  nome: string;
  rendimento: number;
  clienteId: number;
  endereco?: string;
  telefone?: string;
  email?: string;
  cnpj?: string;
  cargo?: string;
  dataAdmissao?: string;
}
