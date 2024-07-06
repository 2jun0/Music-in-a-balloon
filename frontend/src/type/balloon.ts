import type { MusicData } from './music';
import type { ReactionKeyType } from './reaction';

export interface BalloonData {
  id: number;
  title: string;
  uploadedStreamingMusicType: string;
  albumImageUrl: string;
  youtubeMusic: MusicData | null;
  spotifyeMusic: MusicData | null;
  baseLat: number;
  baseLon: number;
  message: string;
  basedAt: string;
  createdAt: string;
  updatedAt: string;
}

export interface BalloonPosition {
  id: number;
  name: string;
  lat: number;
  lon: number;
}

export interface BalloonInfo {
  id: number;
  title: string;
  uploadedStreamingMusicType: string;
  albumImageUrl: string;
}

export interface BalloonFormData {
  streamingMusicUrl: string | null;
  latitude: number | null;
  longitude: number | null;
  message: string | null;
}

export interface BalloonReactData {
  balloonReactType: ReactionKeyType | null;
}

export interface BalloonPickData {
  userLatitude: number;
  userLongitude: number;
}
