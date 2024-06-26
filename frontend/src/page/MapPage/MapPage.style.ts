import { SMALL_MAX_WIDTH_PX } from '@constant/ui';
import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const containerStyling = css({
  position: 'relative',
});

export const mapContainerStyling = css({
  position: 'sticky',
  // borderRadius: '16px',
  overflow: 'hidden',
  flexGrow: 1,
});

export const addButtonStyling = css({
  position: 'absolute',
  right: '50px',
  bottom: '50px',
  zIndex: 400,
});
