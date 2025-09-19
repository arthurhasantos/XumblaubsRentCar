export interface User {
  id: number;
  name: string;
  email: string;
  roles: string[];
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface SignupRequest {
  name: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  id: number;
  name: string;
  email: string;
  roles: string[];
}

export interface MessageResponse {
  message: string;
}
