import { UserRole } from './car-rental';

export interface User {
  id: number;
  name: string;
  email: string;
  roles: UserRole[];
  // Additional user data
  rg?: string;
  cpf?: string;
  address?: string;
  profession?: string;
  employer?: string;
  incomes?: Income[];
  createdAt?: string;
  updatedAt?: string;
}

export interface Income {
  id?: number;
  amount: number;
  source: string;
  description?: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  name: string;
  email: string;
  password: string;
  role: UserRole;
  // Additional fields for complete registration
  rg?: string;
  cpf?: string;
  address?: string;
  profession?: string;
  employer?: string;
  incomes?: Omit<Income, 'id'>[];
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  id: number;
  name: string;
  email: string;
  roles: UserRole[];
}

export interface MessageResponse {
  message: string;
}
