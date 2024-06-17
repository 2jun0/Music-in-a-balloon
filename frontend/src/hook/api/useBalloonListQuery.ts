import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import type { BalloonListData } from '@type/balloonList';

import { getBalloonList } from '@api/balloonList/getBalloonList';

export const useBalloonListQuery = () => {
  const { data: balloonListData } = useSuspenseQuery<BalloonListData, AxiosError>({
    queryKey: ['balloonList'],
    queryFn: getBalloonList,
  });

  return { balloonListData };
};
