import { Status, Wrapper } from '@googlemaps/react-wrapper';
import type { PropsWithChildren } from 'react';

import Flex from '@component/Flex/Flex';
import Spinner from '@component/Spinner/Spinner';

type GoogleMapWrapperProps = PropsWithChildren;

const render = (status: Status) => {
  if (status === Status.FAILURE) throw new Error('An error was occurred.');

  return (
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

const GoogleMapWrapper = ({ children }: GoogleMapWrapperProps) => {
  if (!process.env.GOOGLE_API_KEY) throw new Error('Google api key not valid.');

  return (
    <Wrapper apiKey={process.env.GOOGLE_API_KEY} render={render} libraries={['marker']}>
      {children}
    </Wrapper>
  );
};

export default GoogleMapWrapper;
