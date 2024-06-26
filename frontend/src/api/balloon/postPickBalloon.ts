import { END_POINTS } from '@constant/api';

import type { BalloonData } from '@type/balloon';

import { axiosInstance } from '@api/axiosInstance';

export const postPickBalloon = async (balloonId: number) => {
  const { data } = await axiosInstance.post<BalloonData>(END_POINTS.BALLOON_PICK(balloonId));

  return data;
};
