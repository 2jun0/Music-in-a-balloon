const balloons = [];
for (let i = 0; i < 100; i++) {
  balloons.push({
    id: i,
    title: 'Super Shy',
    uploadedStreamingMusicType: 'YOUTUBE_MUSIC',
    albumImageUrl: 'https://i.ytimg.com/vi/n7ePZLn9_lQ/sddefault.jpg',
    baseLon: Math.random() * 360,
    baseLat: Math.random() * 180 - 90,
    createdAt: '2024-05-01T18:04:34.53997Z',
  });
}

export const balloonList = {
  balloons,
};
