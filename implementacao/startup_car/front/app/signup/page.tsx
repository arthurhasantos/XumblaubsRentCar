"use client";

import Link from "next/link";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { useAuth } from "@/contexts/AuthContext";
import { api } from "@/lib/api";
import { AuthBackground } from "@/app/components/shared";
import toast from "react-hot-toast";

interface SignupForm {
  name: string;
  email: string;
  password: string;
}

const SignupPage = () => {
  const { loading } = useAuth();
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(false);
  
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<SignupForm>();

  const onSubmit = async (data: SignupForm) => {
    setIsLoading(true);
    try {
      await api.post('/api/auth/signup', data);
      toast.success('Conta criada com sucesso!');
      router.push('/signin');
    } catch (error) {
      toast.error('Erro ao criar conta');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <section className="relative z-10 overflow-hidden pb-16 pt-36 md:pb-20 lg:pb-28 lg:pt-[180px]">
        <div className="container">
          <div className="-mx-4 flex flex-wrap">
            <div className="w-full px-4">
              <div className="shadow-three mx-auto max-w-[500px] rounded bg-white px-6 py-10 dark:bg-dark sm:p-[60px]">
                <h3 className="mb-3 text-center text-2xl font-bold text-black dark:text-white sm:text-3xl">
                  Criar Conta
                </h3>
                <p className="mb-11 text-center text-base font-medium text-body-color">
                  Registre-se para acessar o CRUD de clientes
                </p>
                <form onSubmit={handleSubmit(onSubmit)}>
                  <div className="mb-8">
                    <label
                      htmlFor="name"
                      className="mb-3 block text-sm text-dark dark:text-white"
                    >
                      Nome Completo
                    </label>
                    <input
                      type="text"
                      {...register("name", { 
                        required: "Nome é obrigatório",
                        minLength: {
                          value: 2,
                          message: "Nome deve ter pelo menos 2 caracteres"
                        }
                      })}
                      placeholder="Digite seu nome completo"
                      className="border-stroke dark:text-body-color-dark dark:shadow-two w-full rounded-sm border bg-[#f8f8f8] px-6 py-3 text-base text-body-color outline-none transition-all duration-300 focus:border-primary dark:border-transparent dark:bg-[#2C303B] dark:focus:border-primary dark:focus:shadow-none"
                    />
                    {errors.name && (
                      <p className="mt-1 text-sm text-red-500">{errors.name.message}</p>
                    )}
                  </div>

                  <div className="mb-8">
                    <label
                      htmlFor="email"
                      className="mb-3 block text-sm text-dark dark:text-white"
                    >
                      Email
                    </label>
                    <input
                      type="email"
                      {...register("email", { 
                        required: "Email é obrigatório",
                        pattern: {
                          value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                          message: "Email inválido"
                        }
                      })}
                      placeholder="Digite seu email"
                      className="border-stroke dark:text-body-color-dark dark:shadow-two w-full rounded-sm border bg-[#f8f8f8] px-6 py-3 text-base text-body-color outline-none transition-all duration-300 focus:border-primary dark:border-transparent dark:bg-[#2C303B] dark:focus:border-primary dark:focus:shadow-none"
                    />
                    {errors.email && (
                      <p className="mt-1 text-sm text-red-500">{errors.email.message}</p>
                    )}
                  </div>

                  <div className="mb-8">
                    <label
                      htmlFor="password"
                      className="mb-3 block text-sm text-dark dark:text-white"
                    >
                      Senha
                    </label>
                    <input
                      type="password"
                      {...register("password", { 
                        required: "Senha é obrigatória"
                      })}
                      placeholder="Digite sua senha"
                      className="border-stroke dark:text-body-color-dark dark:shadow-two w-full rounded-sm border bg-[#f8f8f8] px-6 py-3 text-base text-body-color outline-none transition-all duration-300 focus:border-primary dark:border-transparent dark:bg-[#2C303B] dark:focus:border-primary dark:focus:shadow-none"
                    />
                    {errors.password && (
                      <p className="mt-1 text-sm text-red-500">{errors.password.message}</p>
                    )}
                  </div>

                  <div className="mb-6">
                    <button 
                      type="submit"
                      disabled={isLoading || loading}
                      className="shadow-submit dark:shadow-submit-dark flex w-full items-center justify-center rounded-sm bg-primary px-9 py-4 text-base font-medium text-white duration-300 hover:bg-primary/90 disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      {isLoading || loading ? 'Criando conta...' : 'Criar Conta'}
                    </button>
                  </div>
                </form>
                <p className="text-center text-base font-medium text-body-color">
                  Já tem uma conta?{" "}
                  <Link href="/signin" className="text-primary hover:underline">
                    Entrar
                  </Link>
                </p>
              </div>
            </div>
          </div>
        </div>
        <AuthBackground />
      </section>
    </>
  );
};

export default SignupPage;