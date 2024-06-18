import { css } from '@emotion/react';

import type { SupportingTextProps } from '@component/SupportingText/SupportingText';

import { Theme } from '@style/Theme';

export const getTextStyling = (isError: Required<SupportingTextProps>['isError']) =>
  css({
    fontSize: Theme.text.small.fontSize,
    lineHeight: Theme.text.small.lineHeight,
    color: isError ? Theme.color.red300 : Theme.color.gray600,
  });
