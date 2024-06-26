import { END_POINTS } from '@constant/api';

import type { BalloonData } from '@type/balloon';

import { axiosInstance } from '@api/axiosInstance';

export const getBalloon = (balloonId: number) => async () => {
  const { data } = await axiosInstance.get<BalloonData>(END_POINTS.BALLOON(balloonId));

  return data;
};
