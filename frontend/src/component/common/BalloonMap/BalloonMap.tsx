import { MAP_INITIAL_ZOOM_SIZE, MAP_MAX_ZOOM_SIZE, MAP_MIN_ZOOM_SIZE } from '@constant/map';
import { useEffect, useRef, useState } from 'react';

interface BalloonMapProps {
  centerLat: number;
  centerLon: number;
}

const BalloonMap = ({ centerLat, centerLon }: BalloonMapProps) => {
  const [map, setMap] = useState<google.maps.Map | null>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (wrapperRef.current) {
      const initialMap = new google.maps.Map(wrapperRef.current, {
        center: { lat: centerLat, lng: centerLon },
        zoom: MAP_INITIAL_ZOOM_SIZE,
        minZoom: MAP_MIN_ZOOM_SIZE,
        maxZoom: MAP_MAX_ZOOM_SIZE,
        disableDefaultUI: true,
        gestureHandling: 'greedy',
        clickableIcons: false,
      });

      setMap(initialMap);
    }
  }, [centerLat, centerLon]);

  useEffect(() => {
    map?.panTo({ lat: centerLat, lng: centerLon });
    map?.setZoom(MAP_INITIAL_ZOOM_SIZE);
  }, [map, centerLat, centerLon]);

  return (
    <>
      <div id="map" ref={wrapperRef} css={{ height: 'calc(100vh - 81px)' }}>
        {map && <></>}
      </div>
    </>
  );
};

export default BalloonMap;
