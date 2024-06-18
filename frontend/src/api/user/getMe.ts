import { END_POINTS } from '@constant/api';

import type { UserData } from '@type/user';

import { axiosInstance } from '@api/axiosInstance';

export const getMe = async () => {
  const { data } = await axiosInstance.get<UserData>(END_POINTS.USER_ME);

  return data;
};
