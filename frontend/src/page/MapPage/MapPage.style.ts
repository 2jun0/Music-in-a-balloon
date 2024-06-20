import { HEADER_HEIGHT_PX, HEADER_HEIGHT_SMALL_PX, SMALL_MAX_WIDTH_PX } from '@constant/ui';
import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const containerStyling = css({
  position: 'relative',
  height: `calc(100vh - ${HEADER_HEIGHT_PX})`,
  padding: `0 ${Theme.spacer.spacing9}`,

  [`@media screen and (max-width: 1200px)`]: {
    padding: `0 ${Theme.spacer.spacing5}`,
  },

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    height: `calc(100vh - ${HEADER_HEIGHT_SMALL_PX})`,
    padding: `0 ${Theme.spacer.spacing4}`,
  },
});

export const mapContainerStyling = css({
  position: 'sticky',
  borderRadius: '16px',
  overflow: 'hidden',
  flexGrow: 1,
});
  },
});
