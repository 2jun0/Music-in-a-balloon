import { useMapPageQueries } from '@hook/api/useMapPageQueries';
import { mapContainerStyling } from '@page/MapPage/MapPage.style';

import Flex from '@component/Flex/Flex';
import BalloonMap from '@component/common/BalloonMap/BalloonMap';
import GoogleMapWrapper from '@component/common/GoogleMapWrapper/GoogleMapWrapper';

const MapPage = () => {
  const {
    balloonListData: { balloons },
    waveData,
  } = useMapPageQueries();

  return (
    <Flex>
      <section css={mapContainerStyling}>
        <GoogleMapWrapper>
          <BalloonMap
            centerLat={balloons[0].baseLat}
            centerLon={balloons[0].baseLon}
            balloons={balloons}
            wave={waveData}
          />
        </GoogleMapWrapper>
      </section>
    </Flex>
  );
};

export default MapPage;
