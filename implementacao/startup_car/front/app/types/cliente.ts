export interface Cliente {
  id: number;
  rg: string;
  cpf: string;
  nome: string;
  endereco: string;
  profissao: string;
  email: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
  empregadoras?: Empregadora[];
  rendimentoTotal?: number;
}

export interface ClienteFormData {
  rg: string;
  cpf: string;
  nome: string;
  endereco: string;
  profissao: string;
  email: string;
  senha: string;
}

// Importar tipos necessários
import { Empregadora } from './empregadora';
