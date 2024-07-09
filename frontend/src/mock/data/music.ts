import type { SpotifyMusicData, YoutubeMusicData } from '@/type/music';

export const spotifyMusic: SpotifyMusicData = {
  title: 'Super Shy',
  albumImageUrl: 'https://i.scdn.co/image/ab67616d0000b2733d98a0ae7c78a3a9babaf8af',
  url: 'https://open.spotify.com/track/5sdQOyqq2IDhvmx2lHOpwd',
  streamingType: 'SPOTIFY_MUSIC',
  spotifyId: '5sdQOyqq2IDhvmx2lHOpwd',
};

export const youtubeMusic: YoutubeMusicData = {
  title: 'Super Shy',
  albumImageUrl: 'https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg',
  url: 'https://music.youtube.com/watch?v=n7ePZLn9_lQ',
  streamingType: 'YOUTUBE_MUSIC',
  youtubeId: 'n7ePZLn9_lQ',
  youtubeUrl: 'https://youtu.be/n7ePZLn9_lQ',
};
