import { useCreateBalloonMutation } from '@hook/api/useCreateBalloonMutation';
import useGeolocation from '@hook/common/useGeolocation';
import type { ChangeEvent, FormEvent } from 'react';
import { useEffect, useState } from 'react';

import type { BalloonFormData } from '@type/balloon';

const defaultBalloonFormData: BalloonFormData = {
  streamingMusicUrl: null,
  latitude: null,
  longitude: null,
};

export const useBalloonForm = (initialBalloonFormData?: BalloonFormData) => {
  const [balloonInfo, setBalloonInfo] = useState<BalloonFormData>(
    initialBalloonFormData ?? defaultBalloonFormData,
  );
  const [isValidated, setIsValidated] = useState<boolean>(false);
  const createBalloonMutation = useCreateBalloonMutation();
  const { coordinates } = useGeolocation();

  useEffect(() => {
    setBalloonInfo((prevBalloonInfo) => {
      return { ...prevBalloonInfo, latitude: coordinates?.lat, longitude: coordinates?.lon };
    });
  }, [coordinates]);

  useEffect(() => {
    const { streamingMusicUrl, latitude, longitude } = balloonInfo;

    setIsValidated(!!streamingMusicUrl && !!latitude && !!longitude);
  }, [balloonInfo]);

  const updateMusicUrl = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const musicUrl = event.target.value;

    setBalloonInfo((prev) => ({ ...prev, streamingMusicUrl: musicUrl }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!isValidated) return;

    createBalloonMutation.mutate(balloonInfo);
  };

  return { balloonInfo, updateMusicUrl, handleSubmit, isValidated };
};
