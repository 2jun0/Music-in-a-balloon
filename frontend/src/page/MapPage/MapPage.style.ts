import { css } from '@emotion/react';

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
