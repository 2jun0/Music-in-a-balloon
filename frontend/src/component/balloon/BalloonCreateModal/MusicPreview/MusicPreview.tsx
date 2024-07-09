import Flex from '@/component/Flex/Flex';
import type { SpotifyMusicData, YoutubeMusicData } from '@/type/music';

import Text from '@component/Text/Text';

import { albumImageStyling, containerStyling, detailStyling } from './MusicPreview.style';

interface MusicPreviewProps {
  musicData: YoutubeMusicData | SpotifyMusicData;
}

const MusicPreview = ({ musicData }: MusicPreviewProps) => {
  const streamimgSite = musicData.streamingType === 'SPOTIFY_MUSIC' ? 'Spotify' : 'YouTube Music';

  return (
    <Flex css={containerStyling}>
      <img css={albumImageStyling} src={musicData.albumImageUrl} alt="album" />
      <Flex css={detailStyling}>
        <Text>
          <b>Title</b>: {musicData.title}
        </Text>
        <Text>
          <b>Streaming site</b> : {streamimgSite}
        </Text>
      </Flex>
    </Flex>
  );
};

export default MusicPreview;
