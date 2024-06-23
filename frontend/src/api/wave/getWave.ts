import { END_POINTS } from '@constant/api';

import type { WaveData } from '@type/wave';

import { axiosInstance } from '@api/axiosInstance';

export const getWave = async () => {
  const { data } = await axiosInstance.get<WaveData>(END_POINTS.WAVE);

  return data;
};
