import type { ComponentPropsWithoutRef } from 'react';

import * as S from '@component/Text/Text.style';

import type { Size } from '@type/index';

export interface TextProps extends ComponentPropsWithoutRef<'p'> {
  size?: Extract<Size, 'xSmall' | 'small' | 'medium' | 'large'>;
}

const Text = ({ size = 'medium', children, ...attributes }: TextProps) => (
  <p css={S.getSizeStyling(size)} {...attributes}>
    {children}
  </p>
);

export default Text;
