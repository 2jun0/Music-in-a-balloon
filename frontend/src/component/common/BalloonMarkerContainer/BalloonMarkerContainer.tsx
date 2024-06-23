import { MAP_ZOOM_OUT_LABEL_LIMIT } from '@constant/map';
import { useState } from 'react';
import { useMapEvents } from 'react-leaflet';
import { useRecoilValue } from 'recoil';

import BalloonMarker from '@component/common/BalloonMarker/BalloonMarker';

import type { BalloonPosition } from '@type/balloon';

import { clickedMarkerIdState } from '@store/scrollFocus';

interface BalloonMarkerContainerProps {
  positions: BalloonPosition[];
}

const BalloonMarkerContainer = ({ positions }: BalloonMarkerContainerProps) => {
  const selectedId = useRecoilValue(clickedMarkerIdState);
  const [isZoomedOut, setIsZoomedOut] = useState(false);

  const map = useMapEvents({
    zoomend() {
      const zoom = map.getZoom();
      const isZoomedOut = zoom > MAP_ZOOM_OUT_LABEL_LIMIT;
      setIsZoomedOut(isZoomedOut);
    },
  });

  return (
    <>
      {positions.map((position) => (
        <BalloonMarker
          key={position.id}
          id={position.id}
          isZoomedOut={isZoomedOut}
          name={position.name}
          lat={position.lat}
          lon={position.lon}
          isSelected={position.id === selectedId}
        />
      ))}
    </>
  );
};

export default BalloonMarkerContainer;
