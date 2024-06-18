import { useSuspenseQueries } from '@tanstack/react-query';

import { getBalloonList } from '@api/balloonList/getBalloonList';
import { getWave } from '@api/wave/getWave';

export const useMapPageQueries = () => {
  const [{ data: balloonListData }, { data: waveData }] = useSuspenseQueries({
    queries: [
      { queryKey: ['balloonList'], queryFn: getBalloonList },
      { queryKey: ['wave'], queryFn: getWave },
    ],
  });

  return { balloonListData, waveData };
};
