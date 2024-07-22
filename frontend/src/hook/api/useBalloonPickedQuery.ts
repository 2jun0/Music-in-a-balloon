import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import { getBalloonPicked } from '@/api/balloon/getBalloonPicked';

import type { BalloonListData } from '@type/balloonList';

export const useBalloonPickedQuery = (page: number) => {
  const { data: balloonListData } = useSuspenseQuery<BalloonListData, AxiosError>({
    queryKey: ['balloonPicked'],
    queryFn: () => getBalloonPicked(page),
  });

  return { balloonListData };
};
