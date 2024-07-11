import type { BalloonListItemData } from '@/type/balloonList';

import { balloons } from './balloon';

const balloonListItems: BalloonListItemData[] = balloons.map((balloon) => ({
  id: balloon.id,
  colorCode: balloon.colorCode,
  title: balloon.title,
  baseLon: balloon.baseLon,
  baseLat: balloon.baseLat,
  basedAt: balloon.basedAt,
  updatedAt: balloon.updatedAt,
  createdAt: balloon.createdAt,
}));

export const balloonList = {
  balloons: balloonListItems,
};
