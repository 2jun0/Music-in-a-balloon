import { css } from '@emotion/react';

import { SMALL_MAX_WIDTH_PX } from '@/constant/ui';
import { Theme } from '@/style/Theme';

export const containerStyling = css({
  width: '100%',
  padding: '0 20px',
  align: 'center',
  gap: Theme.spacer.spacing4,

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    flexDirection: 'column',
  },
});

export const albumImageStyling = css({
  width: '128px',
  filter: 'drop-shadow(2px 2px 2px #c3c3c3)',
});

export const detailStyling = css({
  flexDirection: 'column',
  flexGrow: 1,
  margin: 'auto 0',
  flexShrink: 'initial',
});
