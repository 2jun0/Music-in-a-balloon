import { END_POINTS } from '@constant/api';

import type { BalloonData, BalloonFormData } from '@type/balloon';

import { axiosInstance } from '@api/axiosInstance';

export const postBalloon = async (newBalloon: BalloonFormData) => {
  const { data } = await axiosInstance.post<BalloonData>(END_POINTS.BALLOON_CREATE, newBalloon);

  return data;
};
