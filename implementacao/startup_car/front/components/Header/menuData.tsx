import { Menu } from "@/types/menu";

const menuData: Menu[] = [
  {
    id: 1,
    title: "Pedidos de Aluguel",
    path: "/pedidos",
    newTab: false,
    requireAdmin: false, // Usuários comuns podem acessar
  },
  {
    id: 2,
    title: "CRUD Clientes",
    path: "/clientes",
    newTab: false,
    requireAdmin: true,
  },
  {
    id: 3,
    title: "CRUD Automóveis",
    path: "/automoveis",
    newTab: false,
    requireAdmin: true,
  },
  {
    id: 4,
    title: "CRUD Bancos",
    path: "/bancos",
    newTab: false,
    requireAdmin: true,
  },
  {
    id: 5,
    title: "Contratos de Crédito",
    path: "/contratos-credito",
    newTab: false,
    requireAdmin: true,
  },
  {
    id: 6,
    title: "CRUD Empregadoras",
    path: "/empregadoras",
    newTab: false,
    requireAdmin: true,
  },
];
export default menuData;
