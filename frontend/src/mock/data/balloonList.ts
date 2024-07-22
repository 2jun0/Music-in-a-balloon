import type { BalloonListItemData } from '@/type/balloonList';

import { balloons } from './balloon';

const balloonListItems: BalloonListItemData[] = balloons.map((balloon) => ({
  id: balloon.id,
  colorCode: balloon.colorCode,
  title: balloon.title,
  creatorId: balloon.creator.id,
  baseLon: balloon.baseLon,
  baseLat: balloon.baseLat,
  basedAt: balloon.basedAt,
  updatedAt: balloon.updatedAt,
  createdAt: balloon.createdAt,
}));

export const balloonList = {
  balloons: balloonListItems,
};

export const getBalloonPicked = (page: number) => ({
  balloons: balloonListItems.slice(page * 100, (page + 1) * 100),
});
