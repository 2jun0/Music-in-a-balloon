import type { ComponentPropsWithoutRef } from 'react';
import { Link } from 'react-router-dom';

import { buttonStyling } from '@component/common/SpotifyButton/SpotifyButton.style';

import SpotifyImage from '@asset/svg/spotify.svg';

interface SpotifyButtonProps extends ComponentPropsWithoutRef<'a'> {
  musicUrl: string;
}

const SpotifyButton = ({ musicUrl, ...attributes }: SpotifyButtonProps) => {
  return (
    <Link to={musicUrl} css={buttonStyling} target="_blank" {...attributes}>
      <SpotifyImage />
    </Link>
  );
};

export default SpotifyButton;
