import { css } from '@emotion/react';

import { SMALL_MAX_WIDTH_PX } from '@/constant/ui';

import { Theme } from '@style/Theme';

export const wrapperStyling = css({
  width: '500px',

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    width: `calc(100vw - ${Theme.spacer.spacing4})`,
  },
});

export const formStyling = css({
  display: 'flex',
  flexDirection: 'column',
  gap: Theme.spacer.spacing4,
  width: '100%',
});

export const buttonStyling = css({
  width: '100%',
});

export const inputStyling = css({
  width: '100%',
});

export const getListStyle = (startNumber: number) =>
  css({
    counterReset: `li ${startNumber}`,
    '> li': {
      counterIncrement: 'li',
      paddingLeft: '30px',
      marginBottom: '10px',
    },
    '> li:before': {
      content: "counter(li) '.'",
      width: '1.2em',
      display: 'inline-block',
      marginLeft: '-20px',
    },
  });

export const backButtonStyling = css({
  width: 'fit-content',
  borderRadius: Theme.borderRadius.large,
  margin: '-10px auto',
});
