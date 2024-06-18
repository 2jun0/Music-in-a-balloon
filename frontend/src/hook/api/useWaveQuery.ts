import { useSuspenseQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import type { WaveData } from '@type/wave';

import { getWave } from '@api/wave/getWave';

export const useWaveQuery = () => {
  const { data: waveData } = useSuspenseQuery<WaveData, AxiosError>({
    queryKey: ['wave'],
    queryFn: getWave,
  });

  return { waveData };
};
