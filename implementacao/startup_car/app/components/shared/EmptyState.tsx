interface EmptyStateProps {
  message: string;
}

export default function EmptyState({ message }: EmptyStateProps) {
  return (
    <div className="p-8 text-center text-gray-500 dark:text-gray-400">
      {message}
    </div>
  );
}
