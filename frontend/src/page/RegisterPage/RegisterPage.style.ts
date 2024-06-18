import { css } from '@emotion/react';

import { Theme } from '@style/Theme';

export const containerStyling = css({
  position: 'relative',
  height: 'calc(100vh - 81px)',
  padding: Theme.spacer.spacing4,

  '@media screen and (max-width: 600px)': {
    height: 'calc(100vh - 65px)',
    padding: ` 56px ${Theme.spacer.spacing4} 0 ${Theme.spacer.spacing4}`,
  },
});

export const headingStyling = css({
  marginTop: Theme.spacer.spacing5,
});

export const buttonContainerStyling = css({
  width: '350px',
  maxWidth: '100%',
  gap: '10px',
  marginTop: Theme.spacer.spacing6,
});

export const buttonStyling = css({
  width: '100%',
});
