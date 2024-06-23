import type { Marker as LeafletMarker } from 'leaflet';
import { icon } from 'leaflet';
import { useEffect, useRef } from 'react';
import { Marker } from 'react-leaflet';
import { useSetRecoilState } from 'recoil';

import balloonPinIcons from '@asset/balloonPinIcons';
import selectedBalloonPinIcon from '@asset/svg/selected-balloon-pin-icon.svg?url';

import { normalizeLatitude, normalizeLongitude } from '@util/math';

import { clickedMarkerIdState } from '@store/scrollFocus';

export const balloonIcons = balloonPinIcons.map((url) => icon({ iconUrl: url }));
export const selectedBalloonIcon = icon({ iconUrl: selectedBalloonPinIcon });

interface BalloonMarkerProps {
  id: number;
  name: string;
  lat: number;
  lon: number;
  isSelected: boolean;
  isZoomedOut: boolean;
}
const BalloonMarker = ({ id, name, lat, lon, isSelected, isZoomedOut }: BalloonMarkerProps) => {
  const setClickedMarkerId = useSetRecoilState(clickedMarkerIdState);
  const markerRef = useRef<LeafletMarker | null>(null);

  useEffect(() => {
    if (markerRef.current) {
      markerRef.current.setLatLng([normalizeLatitude(lat), normalizeLongitude(lon)]);
    }
  }, [lat, lon]);

  return (
    <Marker
      ref={markerRef}
      icon={isSelected ? selectedBalloonIcon : balloonIcons[id % balloonIcons.length]}
      position={[normalizeLatitude(lat), normalizeLongitude(lon)]}
      eventHandlers={{
        click: () => {
          setClickedMarkerId(id);
        },
      }}
    />
  );
};

export default BalloonMarker;
