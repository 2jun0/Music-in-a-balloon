import { useQueryClient } from '@tanstack/react-query';

import type { BalloonData } from '@type/balloon';

export const useBalloon = (balloonId: string) => {
  const queryClient = useQueryClient();
  const balloonData = queryClient.getQueryData<BalloonData>([, ''])!;

  return { balloonData };
};
