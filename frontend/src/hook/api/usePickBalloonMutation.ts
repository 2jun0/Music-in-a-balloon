import { HTTP_STATUS_CODE } from '@constant/api';
import { useToast } from '@hook/common/useToast';
import { useCookieError } from '@hook/user/useCookieError';
import { useMutation } from '@tanstack/react-query';

import { postPickBalloon } from '@api/balloon/postPickBalloon';
import type { ErrorResponseData } from '@api/interceptor';

export const usePickBalloonMutation = () => {
  const { createToast } = useToast();
  const { handleCookieError } = useCookieError();

  const pickBalloonMutation = useMutation({
    mutationFn: postPickBalloon,
    onError: (error: ErrorResponseData) => {
      if (error.statusCode && error.statusCode === HTTP_STATUS_CODE.UNAUTHORIZED) {
        handleCookieError();
        return;
      }

      createToast('Failed to pick a balloon. Please try again later.');
    },
  });

  return pickBalloonMutation;
};
