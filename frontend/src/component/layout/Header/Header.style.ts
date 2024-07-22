import { SMALL_MAX_WIDTH_PX } from '@constant/ui';
import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const headerStyling = css({
  position: 'absolute',
  top: 0,

  zIndex: Theme.zIndex.overlayTop,
  width: '100%',

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    padding: `0 ${Theme.spacer.spacing7}`,
  },
});

export const menuContainerStyling = css({
  // width: 'fit-content',
  width: '100%',
  justify: 'space-between',
  align: 'center',
  justifyContent: 'center',
  alignItems: 'center',
  backgroundColor: Theme.color.whiteOpaque,
  boxShadow: Theme.boxShadow.shadow3,
  padding: '10px 10px',
  gap: '10px',
});

export const iconStyling = css({
  cursor: 'pointer',
});

export const centerStyling = css({
  flexGrow: 1,
  align: 'center',
  justifyContent: 'center',
  alignItems: 'center',
});
