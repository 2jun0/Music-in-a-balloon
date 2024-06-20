import { useMapPageQueries } from '@hook/api/useMapPageQueries';
import useGeolocation from '@hook/common/useGeolocation';

import Flex from '@component/Flex/Flex';
import BalloonMap from '@component/common/BalloonMap/BalloonMap';
import GoogleMapWrapper from '@component/common/GoogleMapWrapper/GoogleMapWrapper';

const MapPage = () => {
  const {
    balloonListData: { balloons },
    waveData,
  } = useMapPageQueries();
  const { coordinates } = useGeolocation();

  return (
    <Flex css={containerStyling}>
      <section css={mapContainerStyling}>
        <GoogleMapWrapper>
          <BalloonMap
            centerLat={coordinates.lat ?? balloons[0].baseLat}
            centerLon={coordinates.lon ?? balloons[0].baseLon}
            balloons={balloons}
            wave={waveData}
          />
        </GoogleMapWrapper>
      </section>
    </Flex>
  );
};

export default MapPage;
