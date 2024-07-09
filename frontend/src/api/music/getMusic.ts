import { END_POINTS } from '@constant/api';

import type { SpotifyMusicData, YoutubeMusicData } from '@type/music';

import { axiosInstance } from '@api/axiosInstance';

export const getMusic = async (streamingUrl: string) => {
  const { data } = await axiosInstance.get<YoutubeMusicData | SpotifyMusicData>(END_POINTS.MUSIC, {
    params: { streamingUrl },
  });

  return data;
};
