import type { ComponentPropsWithoutRef, ElementType } from 'react';

import type { FlexStylingProps } from '@component/Flex/Flex.style';
import { getFlexStyling } from '@component/Flex/Flex.style';

export interface FlexProps extends ComponentPropsWithoutRef<'div'> {
  /**
   * @default 'div'
   */
  tag?: ElementType;
  styles?: FlexStylingProps;
}

const Flex = ({ tag = 'div', styles = {}, children, ...attributes }: FlexProps) => {
  const Tag = tag;

  return (
    <Tag css={getFlexStyling(styles)} {...attributes}>
      {children}
    </Tag>
  );
};

export default Flex;
