import type { BalloonListItemData } from '@type/balloonList';

const balloons: BalloonListItemData[] = [];
for (let i = 1; i <= 10000; i += 1) {
  balloons.push({
    id: i,
    title: 'Super Shy',
    baseLon: Math.random() * 360,
    baseLat: Math.random() * 180 - 90,
    basedAt: '2024-05-01T18:04:34.53997Z',
    updatedAt: '2024-05-01T18:04:34.53997Z',
    createdAt: '2024-05-01T18:04:34.53997Z',
  });
}

export const balloonList = {
  balloons,
};
