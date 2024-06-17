import { MAP_ZOOM_OUT_LABEL_LIMIT } from '@constant/map';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import BalloonMarker from '@component/common/BalloonMarker/BalloonMarker';

import type { BalloonPosition } from '@type/balloon';

import { focusedIdState } from '@store/scrollFocus';

interface BalloonMarkerContainerProps {
  map: google.maps.Map;
  positions: BalloonPosition[];
}

const BalloonMarkerContainer = ({ map, positions }: BalloonMarkerContainerProps) => {
  const selectedId = useRecoilValue(focusedIdState);
  const [isZoomedOut, setIsZoomedOut] = useState(false);

  useEffect(() => {
    map.addListener('zoom_changed', () => {
      const zoom = map.getZoom();

      const isZoomedOut = Number(zoom) > MAP_ZOOM_OUT_LABEL_LIMIT;

      setIsZoomedOut(isZoomedOut);
    });
  });

  return (
    <>
      {positions.map((position) => (
        <BalloonMarker
          key={position.id}
          id={position.id}
          map={map}
          isZoomedOut={isZoomedOut}
          name={position.name}
          lat={position.lat}
          lon={position.lon}
          isSelected={position.id == selectedId}
        />
      ))}
    </>
  );
};

export default BalloonMarkerContainer;
