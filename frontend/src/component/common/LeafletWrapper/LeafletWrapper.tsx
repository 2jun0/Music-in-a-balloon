import 'leaflet/dist/leaflet.css';
import type { PropsWithChildren } from 'react';

import Flex from '@component/Flex/Flex';
import Spinner from '@component/Spinner/Spinner';

interface LeafletWrapperProps extends PropsWithChildren {
  isReady?: boolean;
}

const LeafletWrapper = ({ isReady, children }: LeafletWrapperProps) => {
  return isReady ? (
    // eslint-disable-next-line react/jsx-no-useless-fragment
    <>{children}</>
  ) : (
    <Flex
      styles={{
        width: '100%',
        height: '100%',
        justify: 'center',
        align: 'center',
      }}
    >
      <Spinner />
    </Flex>
  );
};

export default LeafletWrapper;
