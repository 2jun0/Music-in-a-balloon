import { useToast } from '@hook/common/useToast';
import { useMutation } from '@tanstack/react-query';

import { postBalloon } from '@api/balloon/postBalloon';
import type { ErrorResponseData } from '@api/interceptor';

export const useCreateBalloonMutation = () => {
  const { createToast } = useToast();

  const createBalloonMutation = useMutation({
    mutationFn: postBalloon,
    onError: (error: ErrorResponseData) => {
      //   if (error.code && error.code > ERROR_CODE.TOKEN_ERROR_RANGE) {
      //     handleCookieError();

      //     return;
      //   }

      createToast('Failed to create a balloon. Please try again later.');
    },
  });

  return createBalloonMutation;
};
