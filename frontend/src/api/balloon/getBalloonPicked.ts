import { END_POINTS } from '@constant/api';

import type { BalloonListData } from '@type/balloonList';

import { axiosInstance } from '@api/axiosInstance';

export const getBalloonPicked = async (page: number) => {
  const { data } = await axiosInstance.get<BalloonListData>(END_POINTS.BALLOON_PICKED, {
    params: {
      page,
    },
  });

  return data;
};
