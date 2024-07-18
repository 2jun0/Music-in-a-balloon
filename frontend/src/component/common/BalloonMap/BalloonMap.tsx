import {
  MAP_INITIAL_ZOOM_SIZE,
  MAP_MAX_ZOOM_SIZE,
  MAP_MIN_ZOOM_SIZE,
  MAP_PICK_REACH_LIMIT,
} from '@constant/map';
import { useCurrentMs } from '@hook/common/useCurrentMs';
import type L from 'leaflet';
import { useEffect, useRef, useState } from 'react';
import { Circle, MapContainer, TileLayer } from 'react-leaflet';

import BalloonMarkerContainer from '@component/common/BalloonMarkerContainer/BalloonMarkerContainer';

import type { BalloonPosition } from '@type/balloon';
import type { BalloonListItemData } from '@type/balloonList';
import type { WaveData } from '@type/wave';

import { Theme } from '@style/Theme';

import { normalizeLatitude, normalizeLongitude } from '@util/math';
import { calcCoordinate } from '@util/wave';

interface BalloonMapProps {
  centerLat: number;
  centerLon: number;
  balloons: BalloonListItemData[];
  wave: WaveData;
}

const BalloonMap = ({ centerLat, centerLon, balloons, wave }: BalloonMapProps) => {
  const [positions, setPositions] = useState<BalloonPosition[] | null>();
  const { current } = useCurrentMs(100);
  const mapRef = useRef<L.Map | null>(null);

  useEffect(() => {
    setPositions(
      balloons.map((balloon) => {
        const deltaSec = (current - Date.parse(balloon.createdAt)) / 1000;
        const { lat, lon } = calcCoordinate(wave, balloon.baseLat, balloon.baseLon, deltaSec);

        return {
          id: balloon.id,
          name: balloon.title,
          color: balloon.colorCode,
          creatorId: balloon.creatorId,
          lat: normalizeLatitude(lat),
          lon: normalizeLongitude(lon),
        };
      }),
    );
  }, [current]);

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
      style={{ height: '100vh' }}
      maxZoom={MAP_MAX_ZOOM_SIZE}
      minZoom={MAP_MIN_ZOOM_SIZE}
      zoomControl={false}
      worldCopyJump
    >
      <TileLayer
        attribution='&copy; <a href="https://maps.google.com">GoogleMaps</a>'
        url="http://{s}.google.com/vt/lyrs=m&hl=en&x={x}&y={y}&z={z}"
        subdomains={['mt0', 'mt1', 'mt2', 'mt3']}
        maxZoom={20}
      />
      {positions && (
        <BalloonMarkerContainer positions={positions} centerLat={centerLat} centerLon={centerLon} />
      )}
      <Circle
        radius={MAP_PICK_REACH_LIMIT}
        center={[centerLat, centerLon]}
        color={Theme.color.white}
      />
    </MapContainer>
  );
};

export default BalloonMap;
