import { mapContainerStyling } from '@page/MapPage/MapPage.style';

import Flex from '@component/Flex/Flex';
import BalloonMap from '@component/common/BalloonMap/BalloonMap';
import GoogleMapWrapper from '@component/common/GoogleMapWrapper/GoogleMapWrapper';

const MapPage = () => {
  return (
    <Flex>
      <section css={mapContainerStyling}>
        <GoogleMapWrapper>
          <BalloonMap centerLat={29.977407697733234} centerLon={31.132474039004347}></BalloonMap>
        </GoogleMapWrapper>
      </section>
    </Flex>
  );
};

export default MapPage;
