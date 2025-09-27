'use client';

import { useAuth } from '@/contexts/AuthContext';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

interface ProtectedRouteProps {
  children: React.ReactNode;
  requireAdmin?: boolean;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children, requireAdmin = false }) => {
  const { user, loading } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!loading && !user) {
      router.push('/signin');
    } else if (!loading && user && requireAdmin && !user.roles.includes('ROLE_ADMIN')) {
      router.push('/');
    }
  }, [user, loading, router, requireAdmin]);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-32 w-32 border-b-2 border-primary"></div>
      </div>
    );
  }

  if (!user) {
    return null;
  }

  if (requireAdmin && !user.roles.includes('ROLE_ADMIN')) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-center">
          <h1 className="text-2xl font-bold text-red-600 mb-4">Acesso Negado</h1>
          <p className="text-gray-600 dark:text-gray-400 mb-4">
            Você não tem permissão para acessar esta página.
          </p>
          <p className="text-sm text-gray-500 dark:text-gray-500">
            Apenas administradores podem acessar o CRUD de clientes.
          </p>
        </div>
      </div>
    );
  }

  return <>{children}</>;
};
