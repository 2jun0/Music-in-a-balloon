import { NETWORK } from '@constant/api';
import { QueryClient } from '@tanstack/react-query';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: NETWORK.RETRY_COUNT,
      gcTime: 0,
    },
  },
});
