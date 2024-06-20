import { useToast } from '@hook/common/useToast';
import { useCookieError } from '@hook/user/useCookieError';
import { useMutation } from '@tanstack/react-query';

import { HTTP_STATUS_CODE } from '@/constant/api';

import { postBalloon } from '@api/balloon/postBalloon';
import type { ErrorResponseData } from '@api/interceptor';

export const useCreateBalloonMutation = () => {
  const { createToast } = useToast();
  const { handleCookieError } = useCookieError();

  const createBalloonMutation = useMutation({
    mutationFn: postBalloon,
    onError: (error: ErrorResponseData) => {
      if (error.code && error.code === HTTP_STATUS_CODE.UNAUTHORIZED) {
        handleCookieError();
        return;
      }

      createToast('Failed to create a balloon. Please try again later.');
    },
  });

  return createBalloonMutation;
};
