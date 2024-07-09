import { HTTP_STATUS_CODE } from '@constant/api';
import { useToast } from '@hook/common/useToast';
import { useCookieError } from '@hook/user/useCookieError';
import { useMutation } from '@tanstack/react-query';

import { deleteReactBalloon } from '@/api/balloon/deleteReactBalloon';

import type { ErrorResponseData } from '@api/interceptor';

export const useUndoReactBalloonMutation = () => {
  const { createToast } = useToast();
  const { handleCookieError } = useCookieError();

  const undoReactBalloonMutation = useMutation({
    mutationFn: deleteReactBalloon,
    onError: (error: ErrorResponseData) => {
      if (error.statusCode && error.statusCode === HTTP_STATUS_CODE.UNAUTHORIZED) {
        handleCookieError();
        return;
      }

      createToast('Failed to undo a balloon reaction. Please try again later.');
    },
  });

  return undoReactBalloonMutation;
};
