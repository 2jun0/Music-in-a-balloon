import type { Marker as LeafletMarker } from 'leaflet';
import { icon } from 'leaflet';
import { useRef, useState } from 'react';
import { Marker } from 'react-leaflet';
import { useSetRecoilState } from 'recoil';

import { usePickBalloonMutation } from '@/hook/api/usePickBalloonMutation';
import { pickedBalloonIdState } from '@/store/balloon';
import { createBalloonIconImage } from '@/util/balloon';

import outRangedBalloonPinIconImage from '@asset/svg/outranged-balloon-pin-icon.svg?url';

interface BalloonMarkerProps {
  id: number;
  name: string;
  colorCode: string;
  lat: number;
  lon: number;
  isInRange: boolean;
  isZoomedIn: boolean;
  userLat: number;
  userLon: number;
}
const BalloonMarker = ({
  id,
  name,
  colorCode,
  lat,
  lon,
  isInRange,
  isZoomedIn,
  userLat,
  userLon,
}: BalloonMarkerProps) => {
  const setPickedBalloonId = useSetRecoilState(pickedBalloonIdState);
  const markerRef = useRef<LeafletMarker | null>(null);
  const pickBalloonMutation = usePickBalloonMutation();
  const [isPicked, setIsPicked] = useState<boolean>(false);

  if (isPicked) return null;

  const onClick = () => {
    if (!isInRange) return;

    pickBalloonMutation.mutate(
      { balloonId: id, data: { userLatitude: userLat, userLongitude: userLon } },
      {
        onSuccess: () => {
          setIsPicked(true);
          setPickedBalloonId(id);
        },
      },
    );
  };

  const markerIcon = icon({
    iconUrl: isInRange ? createBalloonIconImage(colorCode) : outRangedBalloonPinIconImage,
    iconSize: isZoomedIn ? [57, 75] : [19, 25],
  });

  return (
    <Marker
      ref={markerRef}
      title={name}
      riseOnHover
      icon={markerIcon}
      position={[lat, lon]}
      eventHandlers={{
        click: onClick,
      }}
    />
  );
};

export default BalloonMarker;
