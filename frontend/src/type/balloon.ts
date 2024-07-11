import type { SpotifyMusicData, YoutubeMusicData } from './music';
import type { ReactionKeyType } from './reaction';

export interface BalloonData {
  id: number;
  title: string;
  message: string;
  colorCode: string;
  uploadedStreamingMusicType: string;
  albumImageUrl: string;
  youtubeMusic: YoutubeMusicData | null;
  spotifyMusic: SpotifyMusicData | null;
  baseLat: number;
  baseLon: number;
  basedAt: string;
  createdAt: string;
  updatedAt: string;
}

export interface BalloonPosition {
  id: number;
  name: string;
  lat: number;
  lon: number;
  color: string;
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
  colorCode: string | null;
}

export interface BalloonReactData {
  balloonReactionType: ReactionKeyType;
}

export interface BalloonPickData {
  userLatitude: number;
  userLongitude: number;
}
