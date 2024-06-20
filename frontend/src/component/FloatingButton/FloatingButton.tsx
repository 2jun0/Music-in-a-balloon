import type { ComponentPropsWithoutRef } from 'react';

import { getVariantStyling } from '@component/Button/Button.style';
import {
  floatingButtonStyling,
  getIconSizeStyling,
  getIconVariantStyling,
  getSizeStyling,
} from '@component/FloatingButton/FloatingButton.style';

import type { Size } from '@type/index';

import AddIcon from '@asset/svg/add-icon.svg';

export interface FloatingButtonProps extends ComponentPropsWithoutRef<'button'> {
  /**
   * FloatingButton의 시이즈
   *
   * @default 'medium'
   */
  size?: Extract<Size, 'medium' | 'small'>;
  /**
   * FloatingButton의 색상
   *
   * @default 'primary'
   */
  variant?: 'primary' | 'default';
}

const FloatingButton = ({
  size = 'medium',
  variant = 'primary',
  ...attributes
}: FloatingButtonProps) => (
  <button
    type="button"
    css={[floatingButtonStyling, getSizeStyling(size), getVariantStyling(variant)]}
    {...attributes}
  >
    <AddIcon
      aria-label="add icon"
      css={[getIconSizeStyling(size), getIconVariantStyling(variant)]}
    />
  </button>
);

export default FloatingButton;
