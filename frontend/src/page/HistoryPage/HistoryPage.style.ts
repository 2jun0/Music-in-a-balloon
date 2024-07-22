import { css } from '@emotion/react';

import { Theme } from '@/style/Theme';

export const containerStyling = css({
  position: 'relative',
  padding: Theme.spacer.spacing4,
  flexDirection: 'column',
  justifyItems: 'center',
  alignItems: 'center',
});

export const headingStyling = css({
  margin: Theme.spacer.spacing5,
});

export const historyItemsConatinerStyling = css({
  width: '400px',
  maxHeight: 'calc(100vh - 200px)',

  overflowY: 'auto',
  overflowX: 'hidden',
  margin: '50px',

  '@media screen and (max-width: 600px)': {
    width: 'calc(100% - 48px)',
  },
});

export const historyItemStyling = css({
  gap: Theme.spacer.spacing1,
});

export const detailStyling = css({
  flexDirection: 'column',
  flexGrow: 1,
  margin: 'auto 0',
  flexShrink: 'initial',
});
