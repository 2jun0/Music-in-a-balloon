import { END_POINTS } from '@constant/api';

import type { GeolocationData } from '@type/geolocation';

import { axiosInstance } from '@api/axiosInstance';

export const getGeolocation = async () => {
  const { data } = await axiosInstance.get<GeolocationData>(END_POINTS.GEOLOCATION);

  return data;
};
