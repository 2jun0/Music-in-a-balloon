import { PATH } from '@constant/path';
import type { PropsWithChildren } from 'react';
import { useLayoutEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { isRegisteredState } from '@store/user';

type RegisterRequiredProps = PropsWithChildren;

const RegisterRequired = ({ children }: RegisterRequiredProps) => {
  const isRegistered = useRecoilValue(isRegisteredState);
  const navigate = useNavigate();

  useLayoutEffect(() => {
    if (!isRegistered) navigate(PATH.REGISTER);
  }, [isRegistered, navigate]);

  // eslint-disable-next-line react/jsx-no-useless-fragment
  return <>{children}</>;
};

export default RegisterRequired;
