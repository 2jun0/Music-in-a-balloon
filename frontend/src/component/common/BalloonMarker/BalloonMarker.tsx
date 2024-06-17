import { useEffect, useRef } from 'react';
import type { Root } from 'react-dom/client';
import { createRoot } from 'react-dom/client';
import { useSetRecoilState } from 'recoil';

import Flex from '@component/Flex/Flex';
import Text from '@component/Text/Text';
import {
  getBalloonMarkerContainerStyling,
  getLabelStyling,
} from '@component/common/BalloonMarker/BalloonMarker.style';

import BalloonPinIcon from '@asset/svg/balloon-pin-icon.svg';
import SelectedBalloonPinIcon from '@asset/svg/selected-balloon-pin-icon.svg';

import { clickedMarkerIdState } from '@store/scrollFocus';

interface BalloonMarkerProps {
  map: google.maps.Map;
  id: number;
  name: string;
  lat: number;
  lon: number;
  isSelected: boolean;
  isZoomedOut: boolean;
}
const BalloonMarker = ({
  map,
  id,
  name,
  lat,
  lon,
  isSelected,
  isZoomedOut,
}: BalloonMarkerProps) => {
  const setClickedMarkerId = useSetRecoilState(clickedMarkerIdState);

  const markerRef = useRef<google.maps.marker.AdvancedMarkerElement | null>(null);
  const rootRef = useRef<Root | null>(null);

  useEffect(() => {
    if (!rootRef.current) {
      const container = document.createElement('div');
      rootRef.current = createRoot(container);

      markerRef.current = new google.maps.marker.AdvancedMarkerElement({
        position: { lat, lng: lon },
        map,
        content: container,
      });
    }

    return () => {
      if (markerRef.current) markerRef.current.map = null;
    };
  }, [id, lat, lon, map]);

  useEffect(() => {
    if (rootRef.current && markerRef.current) {
      rootRef.current.render(
        <Flex
          styles={{ direction: 'column', align: 'center', gap: '2px' }}
          css={getBalloonMarkerContainerStyling(isZoomedOut)}
        >
          {isSelected ? <SelectedBalloonPinIcon /> : <BalloonPinIcon />}
          {isZoomedOut ? (
            <Text data-text={name} css={getLabelStyling(isSelected)}>
              {name}
            </Text>
          ) : null}
        </Flex>,
      );

      markerRef.current.position = { lat, lng: lon };
      markerRef.current.map = map;

      markerRef.current.addListener('click', () => {
        setClickedMarkerId(id);
      });
    }
  }, [id, isSelected, isZoomedOut, lat, lon, map, name, setClickedMarkerId]);

  return <></>;
};

export default BalloonMarker;
