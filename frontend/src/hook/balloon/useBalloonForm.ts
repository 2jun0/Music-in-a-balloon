import useGeolocation from '@hook/common/useGeolocation';
import type { ChangeEvent } from 'react';
import { useEffect, useState } from 'react';

import { HTTP_STATUS_CODE } from '@/constant/api';

import type { BalloonFormData } from '@type/balloon';
import type { SpotifyMusicData, YoutubeMusicData } from '@type/music';

import { HTTPError } from '@api/HTTPError';
import { getMusic } from '@api/music/getMusic';

const defaultBalloonFormData: BalloonFormData = {
  streamingMusicUrl: null,
  latitude: null,
  longitude: null,
  message: null,
  colorCode: null,
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
    const { streamingMusicUrl, latitude, longitude, message, colorCode } = balloonInfo;

    setCanSubmit(
      !!streamingMusicUrl && !!latitude && !!longitude && !!message && !!musicData && !!colorCode,
    );
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
      try {
        const musicData = await getMusic(musicUrl);
        setMusicData(musicData);
      } catch (e) {
        if (
          !(e instanceof HTTPError) ||
          (!(e.statusCode === HTTP_STATUS_CODE.BAD_REQUEST) &&
            !(e.statusCode === HTTP_STATUS_CODE.NOT_FOUND))
        ) {
          throw e;
        }
      }
    }

    setBalloonInfo((prev) => ({ ...prev, streamingMusicUrl: musicUrl }));
  };

  const updateMessage = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const message = event.target.value;

    setBalloonInfo((prev) => ({ ...prev, message }));
  };

  const updateColorCode = (colorCode: string) => {
    setBalloonInfo((prev) => ({ ...prev, colorCode }));
  };

  return {
    balloonInfo,
    updateMusicUrl,
    updateMessage,
    updateColorCode,
    canSumitMusicUrl,
    canSubmitBalloon,
    musicData,
  };
};
