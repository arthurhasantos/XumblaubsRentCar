export interface Automovel {
  id: number;
  matricula: string;
  ano: number;
  marca: string;
  modelo: string;
  placa: string;
  tipoProprietario: TipoProprietario;
  proprietarioId?: number;
  proprietarioNome?: string;
  observacoes?: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

export enum TipoProprietario {
  CLIENTE = 'CLIENTE',
  EMPRESA = 'EMPRESA',
  BANCO = 'BANCO'
}

export interface AutomovelRequest {
  matricula: string;
  ano: number;
  marca: string;
  modelo: string;
  placa: string;
  tipoProprietario: TipoProprietario;
  proprietarioId?: number;
  proprietarioNome?: string;
  observacoes?: string;
  ativo?: boolean;
}

export interface AutomovelResponse {
  id: number;
  matricula: string;
  ano: number;
  marca: string;
  modelo: string;
  placa: string;
  tipoProprietario: TipoProprietario;
  proprietarioId?: number;
  proprietarioNome?: string;
  observacoes?: string;
  ativo: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface AutomovelStats {
  total: number;
  ativos: number;
  inativos: number;
  porTipo: {
    CLIENTE: number;
    EMPRESA: number;
    BANCO: number;
  };
}
