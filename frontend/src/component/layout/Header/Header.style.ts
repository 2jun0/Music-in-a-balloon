import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const headerStyling = css({
  position: 'absolute',

  zIndex: Theme.zIndex.overlayTop,
  backgroundColor: Theme.color.whiteOpaque,
  width: '100%',
});

export const toolbarStyling = css({
  margin: '0 auto',

  [`@media screen and (max-width: 1200px)`]: {
    width: '100%',
  },

  [`@media screen and (min-width: 1200px)`]: {
    width: '1200px',
  },

  '@media all': {
    minHeight: '56px',
  },
});
