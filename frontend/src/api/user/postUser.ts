import { END_POINTS } from '@constant/api';

import type { RegisterData } from '@type/user';

import { axiosInstance } from '@api/axiosInstance';

export const postUser = async (newUserData: RegisterData) => {
  await axiosInstance.post(END_POINTS.USER, newUserData);
};
