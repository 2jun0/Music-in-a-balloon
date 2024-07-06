import useGeolocation from '@hook/common/useGeolocation';
import type { ChangeEvent } from 'react';
import { useEffect, useState } from 'react';

import type { BalloonFormData } from '@type/balloon';

const defaultBalloonFormData: BalloonFormData = {
  streamingMusicUrl: null,
  latitude: null,
  longitude: null,
  message: null,
};

export const useBalloonForm = (initialBalloonFormData?: BalloonFormData) => {
  const [balloonInfo, setBalloonInfo] = useState<BalloonFormData>(
    initialBalloonFormData ?? defaultBalloonFormData,
  );
  const [canPressNext, setCanPressNext] = useState<boolean>(false);
  const [canSubmit, setCanSubmit] = useState<boolean>(false);
  const { coordinates } = useGeolocation();

  useEffect(() => {
    setBalloonInfo((prevBalloonInfo) => {
      return { ...prevBalloonInfo, latitude: coordinates?.lat, longitude: coordinates?.lon };
    });
  }, [coordinates]);

  useEffect(() => {
    const { streamingMusicUrl, latitude, longitude } = balloonInfo;

    setCanPressNext(!!streamingMusicUrl && !!latitude && !!longitude);
  }, [balloonInfo]);

  useEffect(() => {
    const { streamingMusicUrl, latitude, longitude, message } = balloonInfo;

    setCanSubmit(!!streamingMusicUrl && !!latitude && !!longitude && !!message);
  }, [balloonInfo]);

  const updateMusicUrl = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const musicUrl = event.target.value;

    setBalloonInfo((prev) => ({ ...prev, streamingMusicUrl: musicUrl }));
  };

  const updateMessage = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const message = event.target.value;

    setBalloonInfo((prev) => ({ ...prev, message }));
  };

  return { balloonInfo, updateMusicUrl, updateMessage, canPressNext, canSubmit };
};
