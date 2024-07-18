export interface BalloonListData {
  balloons: BalloonListItemData[];
}

export interface BalloonListItemData {
  id: number;
  title: string;
  colorCode: string;
  creatorId: number;
  baseLat: number;
  baseLon: number;
  basedAt: string;
  createdAt: string;
  updatedAt: string;
}
