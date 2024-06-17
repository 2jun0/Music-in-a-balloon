import { MAP_INITIAL_ZOOM_SIZE, MAP_MAX_ZOOM_SIZE, MAP_MIN_ZOOM_SIZE } from '@constant/map';
import { useEffect, useRef, useState } from 'react';

import BalloonMarkerContainer from '@component/common/BalloonMarkerContainer/BalloonMarkerContainer';

import type { BalloonData, BalloonPosition } from '@type/balloon';
import type { WaveData } from '@type/wave';

import { getTimeDeltaSecFrom } from '@util/time';
import { calcCoordinate } from '@util/wave';

interface BalloonMapProps {
  centerLat: number;
  centerLon: number;
  balloons: BalloonData[];
  wave: WaveData;
}

const BalloonMap = ({ centerLat, centerLon, balloons, wave }: BalloonMapProps) => {
  const [map, setMap] = useState<google.maps.Map | null>(null);
  const wrapperRef = useRef<HTMLDivElement | null>(null);
  const [positions, setPositions] = useState<BalloonPosition[] | null>(null);

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
        mapId: process.env.GOOGLE_MAP_ID,
      });

      setMap(initialMap);
    }
  }, [centerLat, centerLon]);

  useEffect(() => {
    if (balloons.length > 0) {
      const bounds = new google.maps.LatLngBounds();
      balloons.forEach((balloon) =>
        bounds.extend(new google.maps.LatLng(balloon.baseLat, balloon.baseLon)),
      );
      const center = bounds.getCenter();

      map?.panTo(center);
      map?.fitBounds(bounds);
    } else {
      map?.panTo({ lat: centerLat, lng: centerLon });
      map?.setZoom(MAP_INITIAL_ZOOM_SIZE);
    }
  }, [map, centerLat, centerLon]);

  useEffect(() => {
    const interval = setInterval(() => {
      setPositions(
        balloons.map((balloon) => {
          const deltaMilli = getTimeDeltaSecFrom(Date.parse(balloon.createdAt));
          const { lat, lon } = calcCoordinate(wave, balloon.baseLat, balloon.baseLon, deltaMilli);

          return { id: balloon.id, name: balloon.title, lat, lon };
        }),
      );
    }, 1);

    return () => clearInterval(interval);
  });

  return (
    <>
      <div id="map" ref={wrapperRef} css={{ height: 'calc(100vh - 81px)' }}>
        {map && positions && (
          <>
            <BalloonMarkerContainer map={map} positions={positions} />
          </>
        )}
      </div>
    </>
  );
};

export default BalloonMap;
