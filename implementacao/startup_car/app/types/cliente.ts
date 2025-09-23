export interface Cliente {
  id: number;
  rg: string;
  cpf: string;
  nome: string;
  endereco: string;
  profissao: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface ClienteFormData {
  rg: string;
  cpf: string;
  nome: string;
  endereco: string;
  profissao: string;
}
