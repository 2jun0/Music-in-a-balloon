import { BalloonColorCodes } from '@/style/BalloonColorCode';

import type { BalloonData } from '@type/balloon';

import { spotifyMusic, youtubeMusic } from '@mock/data/music';

import { users } from './user';

const randomColor = () => {
  return BalloonColorCodes[Math.floor(Math.random() * BalloonColorCodes.length)];
};

const randomCreator = () => {
  return users[Math.floor(Math.floor(Math.random() * users.length))];
};

export const balloons: BalloonData[] = [];

for (let i = 1; i <= 10000; i += 1) {
  balloons.push({
    id: i,
    title: 'Super Shy',
    colorCode: randomColor(),
    message: 'I love this song 🥰',
    creator: randomCreator(),
    uploadedStreamingMusicType: 'YOUTUBE_MUSIC',
    albumImageUrl: 'https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg',
    youtubeMusic,
    spotifyMusic,
    baseLon: Math.random() * 360,
    baseLat: Math.random() * 180 - 90,
    basedAt: '2024-05-01T18:04:34.53997Z',
    updatedAt: '2024-05-01T18:04:34.53997Z',
    createdAt: '2024-05-01T18:04:34.53997Z',
  });
}
