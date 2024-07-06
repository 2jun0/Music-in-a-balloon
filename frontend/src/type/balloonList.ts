export interface BalloonListData {
  balloons: BalloonListItemData[];
}

export interface BalloonListItemData {
  id: number;
  title: string;
  baseLat: number;
  baseLon: number;
  basedAt: string;
  createdAt: string;
  updatedAt: string;
}
