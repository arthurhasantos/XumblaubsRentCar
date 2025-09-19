import api from './api';
import { LoginRequest, SignupRequest, AuthResponse, MessageResponse } from '@/types/auth';
import Cookies from 'js-cookie';

export const authService = {
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/signin', credentials);
    const { accessToken, ...userData } = response.data;
    
    // Store token in cookie
    Cookies.set('token', accessToken, { expires: 1 }); // 1 day
    
    return response.data;
  },

  async signup(userData: SignupRequest): Promise<MessageResponse> {
    const response = await api.post<MessageResponse>('/auth/signup', userData);
    return response.data;
  },

  async logout(): Promise<void> {
    try {
      await api.post('/auth/signout');
    } catch (error) {
      console.error('Logout error:', error);
    } finally {
      // Always remove token from cookie
      Cookies.remove('token');
    }
  },

  getToken(): string | undefined {
    return Cookies.get('token');
  },

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
};
