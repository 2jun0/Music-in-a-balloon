import type { ComponentPropsWithRef, ForwardedRef } from 'react';
import { forwardRef } from 'react';

import { menuListStyling } from '@component/common/MenuList/MenuList.style';

interface MenuListProps extends ComponentPropsWithRef<'ul'> {
  onScrollToBottom?: () => void;
}

const MenuList = (
  { children, onScrollToBottom, ...attributes }: MenuListProps,
  ref: ForwardedRef<HTMLUListElement>,
) => {
  const onScroll = (event: React.UIEvent<HTMLUListElement>) => {
    const target = event.currentTarget as HTMLUListElement;
    if (target.scrollHeight - target.scrollTop === target.clientHeight) {
      if (onScrollToBottom) {
        onScrollToBottom();
      }
    }
  };

  // if (scrollTop + ref..innerHeight() >= this.scrollHeight)

  return (
    <ul css={menuListStyling} ref={ref} onScroll={onScroll} {...attributes}>
      {children}
    </ul>
  );
};

export default forwardRef(MenuList);
