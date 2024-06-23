import { css } from '@emotion/react';

import { SMALL_MAX_WIDTH_PX } from '@/constant/ui';

import { Theme } from '@style/Theme';

export const wrapperStyling = css({
  width: '500px',
  // minHeight: '528px',

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    width: `calc(100vw - ${Theme.spacer.spacing4})`,
    // height: `80%`,
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

  [`@media screen and (max-width: ${SMALL_MAX_WIDTH_PX})`]: {
    position: 'absolute',
    width: '89%',
    bottom: Theme.spacer.spacing3,
  },
});

export const listStyle = css({
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
