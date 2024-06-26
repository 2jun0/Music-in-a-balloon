import { FOOTER_HEIGHT_PX } from '@constant/ui';
import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const containerStyling = css({
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',
  gap: Theme.spacer.spacing1,

  width: '100vw',
  height: FOOTER_HEIGHT_PX,
  padding: '0 64px',

  backgroundColor: Theme.color.white,

  '@media screen and (max-width: 600px)': {
    height: '20px',
  },

  '& *': {
    color: Theme.color.gray600,
  },
});
