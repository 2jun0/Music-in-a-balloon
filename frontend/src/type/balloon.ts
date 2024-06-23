export interface BalloonData {
  id: number;
  title: string;
  baseLat: number;
  baseLon: number;
  createdAt: string;
}

export interface BalloonPosition {
  id: number;
  name: string;
  lat: number;
  lon: number;
}

export interface BalloonFormData {
  streamingMusicUrl: string | null;
  latitude: number | null;
  longitude: number | null;
}
