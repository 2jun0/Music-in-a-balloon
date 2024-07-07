import { END_POINTS } from '@constant/api';

import type { BalloonData, BalloonPickData } from '@type/balloon';

import { axiosInstance } from '@api/axiosInstance';

interface PostPickBalloonProps {
  balloonId: number;
  data: BalloonPickData;
}

export const postPickBalloon = async ({ balloonId, data }: PostPickBalloonProps) => {
  await axiosInstance.post<BalloonData>(END_POINTS.BALLOON_PICK(balloonId), data);
};
