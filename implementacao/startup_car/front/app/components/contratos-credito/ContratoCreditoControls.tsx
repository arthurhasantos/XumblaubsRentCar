"use client";

interface ContratoCreditoControlsProps {
  searchTerm: string;
  onSearchChange: (value: string) => void;
  showInativos: boolean;
  onShowInativosChange: (checked: boolean) => void;
  onNewContrato: () => void;
}

export default function ContratoCreditoControls({
  searchTerm,
  onSearchChange,
  showInativos,
  onShowInativosChange,
  onNewContrato,
}: ContratoCreditoControlsProps) {
  return (
    <div className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 mb-6">
      <div className="flex flex-col md:flex-row gap-4 items-center justify-between">
        <div className="flex flex-col md:flex-row gap-4 flex-1">
          <div className="relative flex-1">
            <input
              type="text"
              placeholder="Buscar por número..."
              value={searchTerm}
              onChange={(e) => onSearchChange(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
            />
          </div>
          <label className="flex items-center space-x-2">
            <input
              type="checkbox"
              checked={showInativos}
              onChange={(e) => onShowInativosChange(e.target.checked)}
              className="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
            />
            <span className="text-sm text-gray-700 dark:text-gray-300">
              Mostrar inativos
            </span>
          </label>
        </div>
        <button
          onClick={onNewContrato}
          className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg font-medium transition-colors"
        >
          + Novo Contrato
        </button>
      </div>
    </div>
  );
}
