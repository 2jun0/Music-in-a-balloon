import { useUserMeQuery } from '@hook/api/useUserMeQuery';
import type { PropsWithChildren } from 'react';
import { useLayoutEffect } from 'react';
import { useSetRecoilState } from 'recoil';

import { isRegisteredState } from '@store/user';

type RegisterProps = PropsWithChildren;

const Register = ({ children }: RegisterProps) => {
  const setIsRegistered = useSetRecoilState(isRegisteredState);
  const me = useUserMeQuery();

  useLayoutEffect(() => {
    if (me) {
      setIsRegistered(true);
    }
  }, [me, setIsRegistered]);

  // eslint-disable-next-line react/jsx-no-useless-fragment
  return <>{children}</>;
};

export default Register;
