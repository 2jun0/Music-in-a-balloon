type StringType = 'YOUTUBE_MUSIC' | 'SPOTIFY_MUSIC';

interface MusicData {
  title: string;
  albumImageUrl: string;
  url: string;
  streamingType: StringType;
}

export interface YoutubeMusicData extends MusicData {
  youtubeId: string;
  youtubeUrl: string;
  streamingType: 'YOUTUBE_MUSIC';
}

export interface SpotifyMusicData extends MusicData {
  spotifyId: string;
  streamingType: 'SPOTIFY_MUSIC';
}
