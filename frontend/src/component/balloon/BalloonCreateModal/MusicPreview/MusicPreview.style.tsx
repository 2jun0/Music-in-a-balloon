import { css } from '@emotion/react';

import { Theme } from '@/style/Theme';

export const containerStyling = css({
  width: '100%',
  padding: '0 20px',
  align: 'center',
  gap: Theme.spacer.spacing4,
});

export const albumImageStyling = css({
  width: '128px',
  filter: 'drop-shadow(2px 2px 2px #c3c3c3)',
});

export const detailStyling = css({
  flexDirection: 'column',
  flexGrow: 1,
  margin: 'auto 0',
});
