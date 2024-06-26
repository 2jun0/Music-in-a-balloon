import { SMALL_MAX_WIDTH_PX } from '@constant/ui';
import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const headerStyling = css({
  position: 'absolute',
  top: 0,

  zIndex: Theme.zIndex.overlayMiddle,
  width: '100%',
  padding: `0 ${Theme.spacer.spacing9}`,

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    padding: `0 ${Theme.spacer.spacing7}`,
  },
});

export const containerStyling = css({
  width: 'fit-content',
  justify: 'space-between',
  align: 'center',
  justifyContent: 'center',
  alignItems: 'center',
  backgroundColor: Theme.color.whiteOpaque,
  borderRadius: '0 0 12px 12px',
  padding: '5px 15px',
  boxShadow: Theme.boxShadow.shadow3,
});

export const tapNavigateLogoButtonStyling = css({
  display: 'block',

  height: '32px',

  // [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
  //   height: `calc(${HEADER_HEIGHT_SMALL_PX} - 2* ${Theme.spacer.spacing2})`,
  // },

  cursor: 'pointer',
});

export const titleStyling = css({ color: Theme.color.pink300, fontWeight: 550 });
