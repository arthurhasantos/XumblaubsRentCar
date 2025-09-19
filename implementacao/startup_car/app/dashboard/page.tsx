"use client";

import { ProtectedRoute } from "@/components/Auth/ProtectedRoute";
import { useAuth } from "@/contexts/AuthContext";
import Link from "next/link";
import { UserRole } from "@/types/car-rental";

const DashboardPage = () => {
  const { user } = useAuth();

  const getRoleText = (role: UserRole) => {
    switch (role) {
      case 'ROLE_CLIENT':
        return 'Cliente Individual';
      case 'ROLE_COMPANY':
        return 'Empresa';
      case 'ROLE_BANK':
        return 'Banco';
      default:
        return role;
    }
  };

  const isClient = user?.roles?.includes('ROLE_CLIENT');
  const isCompany = user?.roles?.includes('ROLE_COMPANY');
  const isBank = user?.roles?.includes('ROLE_BANK');

  return (
    <ProtectedRoute>
      <section className="relative z-10 overflow-hidden pb-16 pt-36 md:pb-20 lg:pb-28 lg:pt-[180px]">
        <div className="container">
          <div className="-mx-4 flex flex-wrap">
            <div className="w-full px-4">
              <div className="shadow-three mx-auto max-w-[1200px] rounded bg-white px-6 py-10 dark:bg-dark sm:p-[60px]">
                <h1 className="mb-6 text-center text-3xl font-bold text-black dark:text-white">
                  Dashboard - Sistema de Aluguel de Carros
                </h1>
                
                <div className="mb-8 rounded-lg bg-gray-50 p-6 dark:bg-gray-800">
                  <h2 className="mb-4 text-xl font-semibold text-black dark:text-white">
                    Informações do Usuário
                  </h2>
                  <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
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
                    </div>
                    <div className="space-y-2">
                      <p className="text-base text-body-color dark:text-white/70">
                        <span className="font-medium">Tipo de Usuário:</span> {user?.roles?.map(getRoleText).join(', ')}
                      </p>
                      {user?.cpf && (
                        <p className="text-base text-body-color dark:text-white/70">
                          <span className="font-medium">CPF:</span> {user.cpf}
                        </p>
                      )}
                      {user?.profession && (
                    <p className="text-base text-body-color dark:text-white/70">
                          <span className="font-medium">Profissão:</span> {user.profession}
                    </p>
                      )}
                    </div>
                  </div>
                </div>

                {/* Role-specific navigation */}
                <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3">
                  {/* Client features */}
                  {isClient && (
                    <>
                      <Link href="/rental-requests" className="group">
                        <div className="rounded-lg bg-primary/10 p-6 transition-all duration-300 group-hover:bg-primary/20">
                          <h3 className="mb-2 text-lg font-semibold text-primary">
                            Minhas Solicitações
                          </h3>
                          <p className="text-body-color dark:text-white/70">
                            Visualize, crie e gerencie suas solicitações de aluguel de carros.
                          </p>
                        </div>
                      </Link>
                      
                      <Link href="/rental-requests/new" className="group">
                        <div className="rounded-lg bg-green-100 p-6 transition-all duration-300 group-hover:bg-green-200 dark:bg-green-900/20 dark:group-hover:bg-green-900/30">
                          <h3 className="mb-2 text-lg font-semibold text-green-800 dark:text-green-400">
                            Nova Solicitação
                          </h3>
                          <p className="text-body-color dark:text-white/70">
                            Crie uma nova solicitação de aluguel de carro.
                          </p>
                        </div>
                      </Link>
                    </>
                  )}

                  {/* Company features */}
                  {isCompany && (
                    <>
                      <Link href="/cars" className="group">
                        <div className="rounded-lg bg-blue-100 p-6 transition-all duration-300 group-hover:bg-blue-200 dark:bg-blue-900/20 dark:group-hover:bg-blue-900/30">
                          <h3 className="mb-2 text-lg font-semibold text-blue-800 dark:text-blue-400">
                            Gestão de Carros
                          </h3>
                          <p className="text-body-color dark:text-white/70">
                            Gerencie o inventário de carros da sua empresa.
                          </p>
                        </div>
                      </Link>
                      
                      <Link href="/rental-requests" className="group">
                        <div className="rounded-lg bg-orange-100 p-6 transition-all duration-300 group-hover:bg-orange-200 dark:bg-orange-900/20 dark:group-hover:bg-orange-900/30">
                          <h3 className="mb-2 text-lg font-semibold text-orange-800 dark:text-orange-400">
                            Avaliar Solicitações
                          </h3>
                          <p className="text-body-color dark:text-white/70">
                            Analise e avalie solicitações de aluguel de carros.
                          </p>
                        </div>
                      </Link>
                    </>
                  )}

                  {/* Bank features */}
                  {isBank && (
                    <>
                      <Link href="/rental-requests" className="group">
                        <div className="rounded-lg bg-purple-100 p-6 transition-all duration-300 group-hover:bg-purple-200 dark:bg-purple-900/20 dark:group-hover:bg-purple-900/30">
                          <h3 className="mb-2 text-lg font-semibold text-purple-800 dark:text-purple-400">
                            Análise Financeira
                          </h3>
                          <p className="text-body-color dark:text-white/70">
                            Realize análise financeira das solicitações de aluguel.
                          </p>
                        </div>
                      </Link>
                      
                      <Link href="/credit-contracts" className="group">
                        <div className="rounded-lg bg-indigo-100 p-6 transition-all duration-300 group-hover:bg-indigo-200 dark:bg-indigo-900/20 dark:group-hover:bg-indigo-900/30">
                          <h3 className="mb-2 text-lg font-semibold text-indigo-800 dark:text-indigo-400">
                            Contratos de Crédito
                          </h3>
                          <p className="text-body-color dark:text-white/70">
                            Gerencie contratos de crédito associados aos aluguéis.
                          </p>
                        </div>
                      </Link>
                    </>
                  )}

                  {/* Common features */}
                  <Link href="/cars" className="group">
                    <div className="rounded-lg bg-gray-100 p-6 transition-all duration-300 group-hover:bg-gray-200 dark:bg-gray-800 dark:group-hover:bg-gray-700">
                      <h3 className="mb-2 text-lg font-semibold text-gray-800 dark:text-gray-200">
                        Ver Carros Disponíveis
                      </h3>
                      <p className="text-body-color dark:text-white/70">
                        Visualize todos os carros disponíveis para aluguel.
                      </p>
                    </div>
                  </Link>
                </div>

                {/* Quick stats */}
                <div className="mt-8 grid grid-cols-1 gap-6 md:grid-cols-3">
                  <div className="rounded-lg bg-primary/10 p-6">
                    <h3 className="mb-2 text-lg font-semibold text-primary">
                      Sistema Ativo
                    </h3>
                    <p className="text-3xl font-bold text-black dark:text-white">
                      ✓
                    </p>
                  </div>
                  
                  <div className="rounded-lg bg-green-100 p-6 dark:bg-green-900/20">
                    <h3 className="mb-2 text-lg font-semibold text-green-800 dark:text-green-400">
                      Bem-vindo!
                    </h3>
                    <p className="text-sm text-body-color dark:text-white/70">
                      Acesse as funcionalidades disponíveis para seu tipo de usuário.
                    </p>
                  </div>
                  
                  <div className="rounded-lg bg-blue-100 p-6 dark:bg-blue-900/20">
                    <h3 className="mb-2 text-lg font-semibold text-blue-800 dark:text-blue-400">
                      Suporte
                    </h3>
                    <p className="text-sm text-body-color dark:text-white/70">
                      Em caso de dúvidas, entre em contato com o suporte.
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
