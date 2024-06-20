import { PATH } from '@constant/path';
import { useToast } from '@hook/common/useToast';
import { useQueryClient } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { isRegisteredState } from '@store/user';

export const useCookieError = () => {
  const navigate = useNavigate();

  const queryClient = useQueryClient();

  const { createToast } = useToast();

  const setIsRegistered = useSetRecoilState(isRegisteredState);

  const handleCookieError = () => {
    queryClient.clear();
    setIsRegistered(false);
    navigate(PATH.REGISTER);

    createToast("Sorry, Can't found your name. please introduce yourself.");
  };

  return { handleCookieError };
};
