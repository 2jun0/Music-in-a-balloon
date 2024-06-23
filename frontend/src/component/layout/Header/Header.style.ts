import { HEADER_HEIGHT_PX, HEADER_HEIGHT_SMALL_PX, SMALL_MAX_WIDTH_PX } from '@constant/ui';
import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const headerStyling = css({
  position: 'sticky',
  top: 0,

  height: HEADER_HEIGHT_PX,
  zIndex: Theme.zIndex.overlayMiddle,

  backgroundColor: Theme.color.white,
  padding: `${Theme.spacer.spacing2} 20px`,

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    height: HEADER_HEIGHT_SMALL_PX,
    padding: `${Theme.spacer.spacing2} ${Theme.spacer.spacing4}`,
  },
});

export const getItemStyling = (isRegistered: boolean) => {
  return css({
    position: 'relative',
    top: !isRegistered ? '-2px' : 'undefined',

    cursor: 'pointer',
  });
};

export const getTapNavigateButtonStyling = (isRegistered: boolean, isSelected: boolean) => {
  return css({
    display: isRegistered ? 'block' : 'none',

    color: isSelected ? Theme.color.pink300 : Theme.color.gray600,
    fontWeight: 700,
    fontSize: Theme.text.small.fontSize,

    cursor: 'pointer',
  });
};

export const tapNavigateLogoButtonStyling = css({
  display: 'block',

  height: `calc(${HEADER_HEIGHT_PX} - 2* ${Theme.spacer.spacing2})`,

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    height: `calc(${HEADER_HEIGHT_SMALL_PX} - 2* ${Theme.spacer.spacing2})`,
  },

  cursor: 'pointer',
});
