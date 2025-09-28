const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

// Helper function to get auth token
const getAuthToken = (): string | null => {
  if (typeof window !== 'undefined') {
    const token = localStorage.getItem('authToken');
    console.log('Token encontrado:', token ? 'Sim' : 'Não');
    return token;
  }
  return null;
};

// Helper function to get headers with auth token
const getHeaders = (): HeadersInit => {
  const token = getAuthToken();
  const headers: HeadersInit = {
    'Content-Type': 'application/json',
  };
  
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
    console.log('Headers com token:', headers);
  } else {
    console.log('Nenhum token encontrado');
  }
  
  return headers;
};

// Simple API utility using fetch
export const api = {
  get: async (url: string) => {
    const fullUrl = `${API_BASE_URL}/api${url}`;
    console.log('🌐 GET request para:', fullUrl);
    console.log('🔧 API_BASE_URL:', API_BASE_URL);
    
    const headers = getHeaders();
    console.log('📋 Headers sendo enviados:', headers);
    
    const response = await fetch(fullUrl, {
      method: 'GET',
      headers: headers,
    });
    
    console.log('📡 Response status:', response.status);
    console.log('📡 Response ok:', response.ok);
    
    if (!response.ok) {
      const errorText = await response.text();
      console.error('❌ Error response:', errorText);
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const data = await response.json();
    console.log('✅ Response data:', data);
    return data;
  },

  post: async (url: string, data: any) => {
    const fullUrl = `${API_BASE_URL}/api${url}`;
    console.log('POST request para:', fullUrl);
    console.log('Dados enviados:', data);
    
    const headers = getHeaders();
    console.log('Headers sendo enviados:', headers);
    
    const response = await fetch(fullUrl, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify(data),
    });
    
    console.log('Response status:', response.status);
    console.log('Response headers:', response.headers);
    
    if (!response.ok) {
      const errorText = await response.text();
      console.log('Error response:', errorText);
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    return response.json();
  },

  put: async (url: string, data: any) => {
    const fullUrl = `${API_BASE_URL}/api${url}`;
    console.log('PUT request para:', fullUrl);
    
    const response = await fetch(fullUrl, {
      method: 'PUT',
      headers: getHeaders(),
      body: JSON.stringify(data),
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    return response.json();
  },

  delete: async (url: string) => {
    const fullUrl = `${API_BASE_URL}/api${url}`;
    console.log('DELETE request para:', fullUrl);
    
    const response = await fetch(fullUrl, {
      method: 'DELETE',
      headers: getHeaders(),
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    return response.json();
  },
};

export default api;
