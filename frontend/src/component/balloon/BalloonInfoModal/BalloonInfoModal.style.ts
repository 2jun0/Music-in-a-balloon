import { css } from '@emotion/react';

import { SMALL_MAX_WIDTH_PX } from '@/constant/ui';

import { Theme } from '@style/Theme';

export const modalStyling = css({
  width: '500px',

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    width: `calc(100vw - ${Theme.spacer.spacing4})`,
  },
});

export const containerStyling = css({
  flexDirection: 'column',
  gap: Theme.spacer.spacing3,
  width: '100%',
});

export const albumImageStyling = css({
  width: '75%',
  margin: '0 auto',
});

export const listenMusicContainerStyling = css({
  flexDirection: 'column',
  alignItems: 'center',
  gap: Theme.spacer.spacing4,
  margin: '0 auto',
});

export const linkContainerStyling = css({
  justifyContent: 'center',
  alignItems: 'center',
  gap: '10px',
});

export const buttonStyling = css({
  width: '100%',
});

export const creatorNameStyling = css({
  position: 'absolute',
  right: '0',
  bottom: '0',
});

export const bottomContainerStyling = css({
  position: 'relative',
  width: '100%',
  bottom: 0,
});
