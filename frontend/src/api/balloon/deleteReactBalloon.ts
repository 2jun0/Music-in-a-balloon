import { END_POINTS } from '@constant/api';

import { axiosInstance } from '@api/axiosInstance';

export const deleteReactBalloon = async (balloonId: number) => {
  await axiosInstance.delete<void>(END_POINTS.BALLOON_REACTION(balloonId));
};
