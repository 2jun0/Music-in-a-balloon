import type { BalloonData } from '@type/balloon';

export const balloon: BalloonData = {
  id: 1,
  title: 'Super Shy',
  message: 'I love this song 🥰',
  uploadedStreamingMusicType: 'YOUTUBE_MUSIC',
  albumImageUrl: 'https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg',
  youtubeMusic: {
    id: 1,
    title: 'Super Shy',
    albumImageUrl: 'https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg',
    url: 'https://music.youtube.com/watch?v=n7ePZLn9_lQ',
  },
  spotifyeMusic: null,
  baseLon: Math.random() * 360,
  baseLat: Math.random() * 180 - 90,
  basedAt: '2024-05-01T18:04:34.53997Z',
  updatedAt: '2024-05-01T18:04:34.53997Z',
  createdAt: '2024-05-01T18:04:34.53997Z',
};
