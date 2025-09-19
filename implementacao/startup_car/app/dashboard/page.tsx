"use client";

import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";
import { useAuth } from "@/contexts/AuthContext";

const DashboardPage = () => {
  const { user } = useAuth();

  return (
    <ProtectedRoute>
      <section className="relative z-10 overflow-hidden pb-16 pt-36 md:pb-20 lg:pb-28 lg:pt-[180px]">
        <div className="container">
          <div className="-mx-4 flex flex-wrap">
            <div className="w-full px-4">
              <div className="shadow-three mx-auto max-w-[800px] rounded bg-white px-6 py-10 dark:bg-dark sm:p-[60px]">
                <h1 className="mb-6 text-center text-3xl font-bold text-black dark:text-white">
                  Dashboard
                </h1>
                
                <div className="mb-8 rounded-lg bg-gray-50 p-6 dark:bg-gray-800">
                  <h2 className="mb-4 text-xl font-semibold text-black dark:text-white">
                    Informações do Usuário
                  </h2>
                  <div className="space-y-2">
                    <p className="text-base text-body-color dark:text-white/70">
                      <span className="font-medium">Nome:</span> {user?.name}
                    </p>
                    <p className="text-base text-body-color dark:text-white/70">
                      <span className="font-medium">Email:</span> {user?.email}
                    </p>
                    <p className="text-base text-body-color dark:text-white/70">
                      <span className="font-medium">ID:</span> {user?.id}
                    </p>
                    <p className="text-base text-body-color dark:text-white/70">
                      <span className="font-medium">Roles:</span> {user?.roles?.join(', ')}
                    </p>
                  </div>
                </div>

                <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
                  <div className="rounded-lg bg-primary/10 p-6">
                    <h3 className="mb-2 text-lg font-semibold text-primary">
                      Funcionalidade 1
                    </h3>
                    <p className="text-body-color dark:text-white/70">
                      Esta é uma área protegida que só usuários autenticados podem acessar.
                    </p>
                  </div>
                  
                  <div className="rounded-lg bg-primary/10 p-6">
                    <h3 className="mb-2 text-lg font-semibold text-primary">
                      Funcionalidade 2
                    </h3>
                    <p className="text-body-color dark:text-white/70">
                      Aqui você pode adicionar mais funcionalidades do seu sistema.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </ProtectedRoute>
  );
};

export default DashboardPage;
