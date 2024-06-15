import { css } from '@emotion/react';

import type { SpinnerProps } from '@component/Spinner/Spinner';

import { Theme } from '@style/Theme';
import { spinnerRotation } from '@style/animation';

export const getSpinnerStyling = ({ timing, size, width, disabled }: Required<SpinnerProps>) =>
  css({
    display: 'inline-block',

    width: `${size}px`,
    height: `${size}px`,
    border: `${width}px solid ${Theme.color.gray200}`,
    borderBottomColor: disabled ? Theme.color.gray600 : Theme.color.blue500,
    borderRadius: '50%',

    animation: `${spinnerRotation} ${timing}s linear infinite`,
  });
