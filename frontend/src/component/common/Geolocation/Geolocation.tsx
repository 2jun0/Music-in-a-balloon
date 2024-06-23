import useGeolocation from '@hook/common/useGeolocation';
import type { PropsWithChildren } from 'react';
import { useLayoutEffect } from 'react';
import { useSetRecoilState } from 'recoil';

import { geolocationState } from '@store/geolocation';

type GeolocationProps = PropsWithChildren;

const Geolocation = ({ children }: GeolocationProps) => {
  const setGeolocationState = useSetRecoilState(geolocationState);
  const geolocation = useGeolocation();

  useLayoutEffect(() => {
    if (geolocation) {
      setGeolocationState(geolocation);
    }
  }, [geolocation, setGeolocationState]);

  // eslint-disable-next-line react/jsx-no-useless-fragment
  return <>{children}</>;
};

export default Geolocation;
