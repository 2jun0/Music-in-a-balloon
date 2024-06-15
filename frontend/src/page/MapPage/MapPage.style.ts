import { css } from '@emotion/react';

export const mapContainerStyling = css({
  position: 'sticky',
  top: '81px',
  left: '50vw',

  width: '100vw',
  height: 'calc(100vh - 81px)',

  '@media screen and (max-width: 600px)': {
    height: 'calc(100vh - 65px)',
  },
});
