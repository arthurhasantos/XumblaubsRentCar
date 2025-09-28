import { Menu } from "@/types/menu";

const menuData: Menu[] = [
  {
    id: 1,
    title: "Home",
    path: "/",
    newTab: false,
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
    title: "Pedidos de Aluguel",
    path: "/pedidos",
    newTab: false,
    requireAdmin: false, // Usuários comuns podem acessar
  },
];
export default menuData;
