import { MAP_INITIAL_ZOOM_SIZE, MAP_PICK_REACH_LIMIT, MAP_ZOOM_IN_LIMIT } from '@constant/map';
import L from 'leaflet';
import { useEffect, useState } from 'react';
import { useMapEvents } from 'react-leaflet';

import BalloonMarker from '@component/common/BalloonMarker/BalloonMarker';

import type { BalloonPosition } from '@type/balloon';

interface BalloonMarkerContainerProps {
  positions: BalloonPosition[];
  centerLat: number;
  centerLon: number;
}

const BalloonMarkerContainer = ({
  positions,
  centerLat,
  centerLon,
}: BalloonMarkerContainerProps) => {
  const [isZoomedIn, setIsZoomedIn] = useState(MAP_INITIAL_ZOOM_SIZE >= MAP_ZOOM_IN_LIMIT);
  const [bounds, setBounds] = useState<L.LatLngBounds | null>(null);
  const [boundedPositions, setBoundedPositions] = useState<BalloonPosition[]>([]);

  const map = useMapEvents({
    zoomend() {
      const zoom = map.getZoom();
      const isZoomedIn = zoom >= MAP_ZOOM_IN_LIMIT;
      setIsZoomedIn(isZoomedIn);
    },
    move() {
      const bounds = map.getBounds();
      setBounds(bounds);
    },
  });

  useEffect(() => {
    const bounds = map.getBounds();
    setBounds(bounds);
  }, [map]);

  useEffect(() => {
    if (bounds) {
      const boundedPositions = positions.filter((p) => bounds.contains([p.lat, p.lon]));
      setBoundedPositions(boundedPositions);
    }
  }, [bounds, positions]);

  const distanceFromCenter = (lat: number, lon: number) => {
    return L.latLng(centerLat, centerLon).distanceTo([lat, lon]);
  };

  return (
    <>
      {boundedPositions.map((position) => (
        <BalloonMarker
          key={position.id}
          id={position.id}
          isZoomedIn={isZoomedIn}
          name={position.name}
          colorCode={position.color}
          creatorId={position.creatorId}
          lat={position.lat}
          lon={position.lon}
          isInRange={distanceFromCenter(position.lat, position.lon) <= MAP_PICK_REACH_LIMIT}
          userLat={centerLat}
          userLon={centerLon}
        />
      ))}
    </>
  );
};

export default BalloonMarkerContainer;
