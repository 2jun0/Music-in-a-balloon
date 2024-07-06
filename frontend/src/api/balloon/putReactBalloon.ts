import { END_POINTS } from '@constant/api';

import type { BalloonReactData } from '@/type/balloon';

import { axiosInstance } from '@api/axiosInstance';

interface PutReactBalloonParams {
  balloonId: number;
  data: BalloonReactData;
}

export const putReactBalloon = async ({ balloonId, data }: PutReactBalloonParams) => {
  await axiosInstance.put<void>(END_POINTS.BALLOON_REACTION(balloonId), data);
};
