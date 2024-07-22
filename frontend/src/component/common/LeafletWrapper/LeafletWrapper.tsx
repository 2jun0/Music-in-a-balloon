import 'leaflet/dist/leaflet.css';
import type { PropsWithChildren } from 'react';

import Flex from '@component/Flex/Flex';
import Spinner from '@component/Spinner/Spinner';

import { loadingConatinerStyling } from './LeafletWrapper.style';

interface LeafletWrapperProps extends PropsWithChildren {
  isReady?: boolean;
}

const LeafletWrapper = ({ isReady, children }: LeafletWrapperProps) => {
  return isReady ? (
    // eslint-disable-next-line react/jsx-no-useless-fragment
    <>{children}</>
  ) : (
    <Flex css={loadingConatinerStyling}>
      <Spinner />
    </Flex>
  );
};

export default LeafletWrapper;
