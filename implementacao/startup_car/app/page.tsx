import ScrollUp from "@/components/Common/ScrollUp";
import Hero from "@/components/Hero";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Sistema de Aluguel de Carros",
  description: "Sistema completo para gestão de aluguel de carros com análise financeira e contratos de crédito",
  // other metadata
};

export default function Home() {
  return (
    <>
      <ScrollUp />
      <Hero />
    </>
  );
}
