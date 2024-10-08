import { TOAST_CLOSE_ANIMATION_DURATION, TOAST_SHOW_DURATION } from '@constant/index';
import type { ComponentPropsWithoutRef } from 'react';
import { useCallback, useEffect, useRef, useState } from 'react';
import { createPortal } from 'react-dom';

import {
  closeIconStyling,
  contentStyling,
  getToastStyling,
  getVariantStyling,
} from '@component/Toast/Toast.style';

import CloseIcon from '@asset/svg/close-icon.svg';

export interface ToastProps extends ComponentPropsWithoutRef<'div'> {
  variant?: 'default' | 'success' | 'error';
  hasCloseButton?: boolean;
  showDuration?: number;
  onClose: () => void;
}

const Toast = ({
  variant = 'default',
  hasCloseButton = false,
  showDuration = TOAST_SHOW_DURATION,
  onClose,
  children,
  ...attributes
}: ToastProps) => {
  const [isAdded, setIsAdded] = useState(true);
  const [isVisible, setIsVisible] = useState(true);

  const showAnimationRef = useRef<NodeJS.Timeout>();
  const hideAnimationRef = useRef<NodeJS.Timeout>();

  const handleClose = useCallback(() => {
    hideAnimationRef.current = setTimeout(() => {
      setIsAdded(false);
      onClose?.();
      clearTimeout(showAnimationRef.current);
    }, TOAST_CLOSE_ANIMATION_DURATION);
  }, [onClose]);

  useEffect(() => {
    showAnimationRef.current = setTimeout(() => {
      setIsVisible(false);
      handleClose();
    }, showDuration);

    return () => clearTimeout(hideAnimationRef.current);
  }, [handleClose, showDuration]);

  return isAdded
    ? createPortal(
        <div
          css={[getToastStyling(isVisible), getVariantStyling(variant)]}
          role="alert"
          aria-live="assertive"
          {...attributes}
        >
          <span css={contentStyling}>{children}</span>
          {hasCloseButton && <CloseIcon css={closeIconStyling} onClick={handleClose} />}
        </div>,
        document.getElementById('toast-container') as Element,
      )
    : createPortal(<div />, document.getElementById('toast-container') as Element);
};

export default Toast;
