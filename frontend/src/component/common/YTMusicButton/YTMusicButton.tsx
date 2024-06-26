import type { ComponentPropsWithoutRef } from 'react';
import { Link } from 'react-router-dom';

import { buttonStyling } from '@component/common/YTMusicButton/YTMusicButton.style';

import YTMusicImage from '@asset/svg/yt-music.svg';

interface YTMusicButtonProps extends ComponentPropsWithoutRef<'a'> {
  musicUrl: string;
}

const YTMusicButton = ({ musicUrl, ...attributes }: YTMusicButtonProps) => {
  return (
    <Link to={musicUrl} css={buttonStyling} {...attributes}>
      <YTMusicImage />
    </Link>
  );
};

export default YTMusicButton;
