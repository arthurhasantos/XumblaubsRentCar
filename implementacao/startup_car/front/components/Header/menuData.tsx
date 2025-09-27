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
];
export default menuData;
