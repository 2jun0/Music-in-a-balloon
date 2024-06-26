import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import type { BalloonData } from '@type/balloon';

import { getBalloon } from '@api/balloon/getBalloon';

export const useBalloonQuery = (balloonId: number) => {
  const { data: balloonData } = useSuspenseQuery<BalloonData, AxiosError>({
    queryKey: ['balloon'],
    queryFn: getBalloon(balloonId),
  });

  return { balloonData };
};
