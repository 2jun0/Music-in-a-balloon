import { mapContainerStyling } from '@page/MapPage/MapPage.style';

import { useBalloonListQuery } from '@/hook/api/useBalloonListQuery';

import Flex from '@component/Flex/Flex';
import BalloonMap from '@component/common/BalloonMap/BalloonMap';
import GoogleMapWrapper from '@component/common/GoogleMapWrapper/GoogleMapWrapper';

const MapPage = () => {
  const {
    balloonListData: { balloons },
  } = useBalloonListQuery();

  return (
    <Flex>
      <section css={mapContainerStyling}>
        <GoogleMapWrapper>
          <BalloonMap
            centerLat={balloons[0].baseLat}
            centerLon={balloons[0].baseLon}
            balloons={balloons}
          />
        </GoogleMapWrapper>
      </section>
    </Flex>
  );
};

export default MapPage;
