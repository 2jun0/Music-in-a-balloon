import type { PropsWithChildren } from 'react';
import { useLayoutEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';

import { PATH } from '@/constant/path';

import { getMe } from '@api/user/getMe';

import { isRegisteredState, meState } from '@store/user';

type RegisterProps = PropsWithChildren;

const Register = ({ children }: RegisterProps) => {
  const setIsRegistered = useSetRecoilState(isRegisteredState);
  const setMe = useSetRecoilState(meState);
  const navigate = useNavigate();

  useLayoutEffect(() => {
    getMe()
      .then((me) => {
        setIsRegistered(true);
        setMe(me);
      })
      .catch(() => {
        setIsRegistered(false);
        navigate(PATH.REGISTER);
      });
  }, [setIsRegistered]);

  // eslint-disable-next-line react/jsx-no-useless-fragment
  return <>{children}</>;
};

export default Register;
