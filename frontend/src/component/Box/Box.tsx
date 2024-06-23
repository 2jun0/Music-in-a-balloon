import type { ComponentPropsWithoutRef, ElementType } from 'react';

import type { BoxStylingProps } from '@component/Box/Box.style';
import { getBoxStyling } from '@component/Box/Box.style';

export interface BoxProps extends ComponentPropsWithoutRef<'div'> {
  /**
   * @default 'div'
   */
  tag?: ElementType;
  styles?: BoxStylingProps;
}

const Box = ({ tag = 'div', styles = {}, children, ...attributes }: BoxProps) => {
  const Tag = tag;

  return (
    <Tag css={getBoxStyling(styles)} {...attributes}>
      {children}
    </Tag>
  );
};

export default Box;
