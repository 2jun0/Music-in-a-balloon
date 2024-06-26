import type { ComponentPropsWithoutRef } from 'react';
import { Link } from 'react-router-dom';

import { buttonStyling } from '@component/common/YouTubeButton/YouTubeButton.style';

import YouTubeImage from '@asset/svg/youtube.svg';

interface YouTubeButtonProps extends ComponentPropsWithoutRef<'a'> {
  videoUrl: string;
}

const YouTubeButton = ({ videoUrl, ...attributes }: YouTubeButtonProps) => {
  return (
    <Link to={videoUrl} css={buttonStyling} target="_blank" {...attributes}>
      <YouTubeImage />
    </Link>
  );
};

export default YouTubeButton;
