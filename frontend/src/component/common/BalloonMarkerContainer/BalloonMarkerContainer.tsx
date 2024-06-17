import { MAP_ZOOM_OUT_LABEL_LIMIT } from '@constant/map';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import BalloonMarker from '@component/common/BalloonMarker/BalloonMarker';

import { BalloonData } from '@type/balloon';

import { focusedIdState } from '@store/scrollFocus';

interface BalloonMarkerContainerProps {
  map: google.maps.Map;
  balloons: BalloonData[];
}

const BalloonMarkerContainer = ({ map, balloons }: BalloonMarkerContainerProps) => {
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
      {balloons.map((balloon) => (
        <BalloonMarker
          key={balloon.id}
          id={balloon.id}
          map={map}
          isZoomedOut={isZoomedOut}
          name={balloon.title}
          lat={balloon.baseLat}
          lon={balloon.baseLon}
          isSelected={balloon.id == selectedId}
        />
      ))}
    </>
  );
};

export default BalloonMarkerContainer;
