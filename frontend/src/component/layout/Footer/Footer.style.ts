import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const footerStyling = css({
  position: 'absolute',
  bottom: 0,

  zIndex: Theme.zIndex.overlayTop,
  width: '100%',

  '& *': {
    color: Theme.color.gray700,
  },
});

export const containerStyling = css({
  flexDirection: 'column',
  justify: 'space-between',
  justifyContent: 'center',
  alignItems: 'center',
  align: 'center',
});
