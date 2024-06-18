import { useToast } from '@hook/common/useToast';
import { useMutation } from '@tanstack/react-query';
import { useSetRecoilState } from 'recoil';

import { postUser } from '@api/user/postUser';

import { isRegisteredState } from '@store/user';

export const useNewUserMutation = () => {
  const { createToast } = useToast();

  const setIsRegistered = useSetRecoilState(isRegisteredState);

  const registerMutation = useMutation({
    mutationFn: postUser,
    onSuccess() {
      setIsRegistered(true);
    },
    onError: () => {
      createToast("Can't register user infomation. please try again later.");
    },
  });

  return registerMutation;
};
