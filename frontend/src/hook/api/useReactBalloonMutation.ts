import { HTTP_STATUS_CODE } from '@constant/api';
import { useToast } from '@hook/common/useToast';
import { useCookieError } from '@hook/user/useCookieError';
import { useMutation } from '@tanstack/react-query';

import { putReactBalloon } from '@/api/balloon/putReactBalloon';

import type { ErrorResponseData } from '@api/interceptor';

export const useReactBalloonMutation = () => {
  const { createToast } = useToast();
  const { handleCookieError } = useCookieError();

  const reactBalloonMutation = useMutation({
    mutationFn: putReactBalloon,
    onError: (error: ErrorResponseData) => {
      if (error.statusCode && error.statusCode === HTTP_STATUS_CODE.UNAUTHORIZED) {
        handleCookieError();
        return;
      }

      createToast('Failed to react a balloon. Please try again later.');
    },
  });

  return reactBalloonMutation;
};
