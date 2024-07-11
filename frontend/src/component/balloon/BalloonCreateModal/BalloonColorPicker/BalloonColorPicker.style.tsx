import { css } from '@emotion/react';

import { SMALL_MAX_WIDTH_PX } from '@/constant/ui';
import { Theme } from '@/style/Theme';

export const containerStyling = css({
  padding: '0 20px',
  alignItems: 'center',
  gap: Theme.spacer.spacing6,
  margin: '0 auto',

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    gap: 0,
  },
});

export const previewStyling = css({
  width: '80px',
  margin: 'auto 0',
});
