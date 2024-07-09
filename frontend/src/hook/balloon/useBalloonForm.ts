import useGeolocation from '@hook/common/useGeolocation';
import type { ChangeEvent } from 'react';
import { useEffect, useState } from 'react';

import type { BalloonFormData } from '@type/balloon';
import type { SpotifyMusicData, YoutubeMusicData } from '@type/music';

import { getMusic } from '@api/music/getMusic';

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
  const [canSumitMusicUrl, setCanPressNext] = useState<boolean>(false);
  const [canSubmitBalloon, setCanSubmit] = useState<boolean>(false);
  const { coordinates } = useGeolocation();
  const [musicData, setMusicData] = useState<SpotifyMusicData | YoutubeMusicData | null>(null);

  useEffect(() => {
    setBalloonInfo((prevBalloonInfo) => {
      return { ...prevBalloonInfo, latitude: coordinates?.lat, longitude: coordinates?.lon };
    });
  }, [coordinates]);

  useEffect(() => {
    const { streamingMusicUrl, latitude, longitude } = balloonInfo;

    setCanPressNext(!!streamingMusicUrl && !!latitude && !!longitude && !!musicData);
  }, [balloonInfo]);

  useEffect(() => {
    const { streamingMusicUrl, latitude, longitude, message } = balloonInfo;

    setCanSubmit(!!streamingMusicUrl && !!latitude && !!longitude && !!message && !!musicData);
  }, [balloonInfo]);

  const YOUTUBE_URL_REGEX =
    /(?:youtube\.com\/(?:[^/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?/\s]{11})/gi;
  const SPOTIFY_URL_REGEX = /^https?:\/\/open\.spotify\.com\/track\/.*$/i;

  const validateMusicUrl = (musicUrl: string) => {
    return !!musicUrl.match(SPOTIFY_URL_REGEX) || !!musicUrl.match(YOUTUBE_URL_REGEX);
  };

  const updateMusicUrl = async (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const musicUrl = event.target.value;

    if (!validateMusicUrl(musicUrl)) {
      setMusicData(null);
    } else {
      const musicData = await getMusic(musicUrl);
      setMusicData(musicData);
    }

    setBalloonInfo((prev) => ({ ...prev, streamingMusicUrl: musicUrl }));
  };

  const updateMessage = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const message = event.target.value;

    setBalloonInfo((prev) => ({ ...prev, message }));
  };

  return {
    balloonInfo,
    updateMusicUrl,
    updateMessage,
    canSumitMusicUrl,
    canSubmitBalloon,
    musicData,
  };
};
