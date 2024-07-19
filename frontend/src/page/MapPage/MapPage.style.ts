import { css } from '@emotion/react';

import { Theme } from '@/style/Theme';

export const containerStyling = css({
  position: 'relative',
});

export const mapContainerStyling = css({
  zIndex: Theme.zIndex.overlayMiddle,
  overflow: 'hidden',
  width: '100%',
  maxHeight: '100vh',
});

export const addButtonStyling = css({
  position: 'absolute',
  right: '50px',
  bottom: '50px',
  zIndex: 400,
});
