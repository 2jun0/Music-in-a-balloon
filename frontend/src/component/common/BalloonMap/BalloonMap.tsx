import { MAP_INITIAL_ZOOM_SIZE, MAP_MAX_ZOOM_SIZE, MAP_MIN_ZOOM_SIZE } from '@constant/map';
import type { Map as LeafletMap } from 'leaflet';
import { useEffect, useRef, useState } from 'react';
import { MapContainer, TileLayer } from 'react-leaflet';

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
  const initPositions = balloons.map((balloon) => {
    const deltaMilli = getTimeDeltaSecFrom(Date.parse(balloon.createdAt));
    const { lat, lon } = calcCoordinate(wave, balloon.baseLat, balloon.baseLon, deltaMilli);

    return { id: balloon.id, name: balloon.title, lat, lon };
  });

  const [positions, setPositions] = useState<BalloonPosition[]>(initPositions);
  const mapRef = useRef<LeafletMap | null>(null);

  useEffect(() => {
    const interval = setInterval(() => {
      setPositions(
        balloons.map((balloon) => {
          const deltaMilli = getTimeDeltaSecFrom(Date.parse(balloon.createdAt));
          const { lat, lon } = calcCoordinate(wave, balloon.baseLat, balloon.baseLon, deltaMilli);

          return { id: balloon.id, name: balloon.title, lat, lon };
        }),
      );
    }, 100);

    return () => clearInterval(interval);
  });

  useEffect(() => {
    if (mapRef.current) {
      mapRef.current.panTo([centerLat, centerLon]);
    }
  }, [centerLat, centerLon]);

  return (
    <MapContainer
      ref={mapRef}
      center={[centerLat, centerLon]}
      zoom={MAP_INITIAL_ZOOM_SIZE}
      style={{ height: 'calc(100vh - 81px)' }}
      maxZoom={MAP_MAX_ZOOM_SIZE}
      minZoom={MAP_MIN_ZOOM_SIZE}
      worldCopyJump
    >
      <TileLayer
        attribution='&copy; <a href="https://maps.google.com">GoogleMaps</a>'
        url="http://{s}.google.com/vt/lyrs=m&hl=en&x={x}&y={y}&z={z}"
        subdomains={['mt0', 'mt1', 'mt2', 'mt3']}
        maxZoom={20}
      />
      {positions && <BalloonMarkerContainer positions={positions} />}
    </MapContainer>
  );
};

export default BalloonMap;
