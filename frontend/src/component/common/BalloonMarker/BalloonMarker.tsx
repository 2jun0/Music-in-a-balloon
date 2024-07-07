import type { Marker as LeafletMarker } from 'leaflet';
import { icon } from 'leaflet';
import { useEffect, useRef, useState } from 'react';
import { Marker } from 'react-leaflet';
import { useSetRecoilState } from 'recoil';

import { usePickBalloonMutation } from '@/hook/api/usePickBalloonMutation';
import { pickedBalloonIdState } from '@/store/balloon';

import balloonPinIconImage1 from '@asset/svg/balloon-pin-icon-1.svg?url';
import balloonPinIconImage2 from '@asset/svg/balloon-pin-icon-2.svg?url';
import balloonPinIconImage3 from '@asset/svg/balloon-pin-icon-3.svg?url';
import outRangedBalloonPinIconImage from '@asset/svg/outranged-balloon-pin-icon.svg?url';

const balloonPinIconImages = [balloonPinIconImage1, balloonPinIconImage2, balloonPinIconImage3];

interface BalloonMarkerProps {
  id: number;
  name: string;
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
    iconUrl: isInRange
      ? balloonPinIconImages[id % balloonPinIconImages.length]
      : outRangedBalloonPinIconImage,
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
    >
      {/* {isSelected ? <Fireworks css={fireworksStyling} autorun={{ speed: 3 }} /> : <></>} */}
    </Marker>
  );
};

export default BalloonMarker;
