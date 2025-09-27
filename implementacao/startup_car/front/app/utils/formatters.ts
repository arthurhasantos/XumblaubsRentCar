export const formatCPF = (cpf: string): string => {
  return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
};

export const formatPlaca = (placa: string): string => {
  // Formato antigo: ABC1234 -> ABC-1234
  if (/^[A-Z]{3}[0-9]{4}$/.test(placa)) {
    return placa.replace(/([A-Z]{3})([0-9]{4})/, "$1-$2");
  }
  // Formato novo: ABC1D23 -> ABC1D23
  if (/^[A-Z]{3}[0-9][A-Z][0-9]{2}$/.test(placa)) {
    return placa;
  }
  return placa;
};