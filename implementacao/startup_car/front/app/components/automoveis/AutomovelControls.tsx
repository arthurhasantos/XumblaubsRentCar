"use client";

interface AutomovelControlsProps {
  searchTerm: string;
  onSearchChange: (value: string) => void;
  showInativos: boolean;
  onShowInativosChange: (value: boolean) => void;
  onNewAutomovel: () => void;
}

export function AutomovelControls({
  searchTerm,
  onSearchChange,
  showInativos,
  onShowInativosChange,
  onNewAutomovel,
}: AutomovelControlsProps) {
  return (
    <div className="mb-6 flex flex-col sm:flex-row gap-4 items-start sm:items-center justify-between">
      <div className="flex flex-col sm:flex-row gap-4 flex-1">
        {/* Campo de busca */}
        <div className="relative flex-1 max-w-md">
          <input
            type="text"
            placeholder="Buscar por marca..."
            value={searchTerm}
            onChange={(e) => onSearchChange(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent dark:bg-gray-700 dark:text-white"
          />
          <div className="absolute inset-y-0 right-0 pr-3 flex items-center pointer-events-none">
            <svg className="h-5 w-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </div>
        </div>

        {/* Checkbox para mostrar inativos */}
        <div className="flex items-center">
          <input
            type="checkbox"
            id="showInativos"
            checked={showInativos}
            onChange={(e) => onShowInativosChange(e.target.checked)}
            className="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
          />
          <label htmlFor="showInativos" className="ml-2 text-sm text-gray-700 dark:text-gray-300">
            Mostrar inativos
          </label>
        </div>
      </div>

      {/* Botão para novo automóvel */}
      <button
        onClick={onNewAutomovel}
        className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg font-medium transition-colors duration-200 flex items-center gap-2"
      >
        <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
        </svg>
        Novo Automóvel
      </button>
    </div>
  );
}
