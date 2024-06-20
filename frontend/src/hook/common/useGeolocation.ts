import { useToast } from '@hook/common/useToast';
import { useEffect, useState } from 'react';

import type { GeolocationType } from '@type/geolocation';

const useGeolocation = () => {
  const { createToast } = useToast();

  const [location, setLocation] = useState<GeolocationType>({
    loaded: false,
    coordinates: { lat: 0, lon: 0 },
  });

  const onSuccess = (location: { coords: { latitude: number; longitude: number } }) => {
    setLocation({
      loaded: true,
      coordinates: {
        lat: location.coords.latitude,
        lon: location.coords.longitude,
      },
    });
  };

  const onError = () => {
    setLocation((location) => ({
      ...location,
      loaded: true,
    }));

    createToast("Can't load Geolocation", 'error');
  };

  useEffect(() => {
    if (!('geolocation' in navigator)) {
      createToast('Geolocation not supported', 'error');
    }
    navigator.geolocation.getCurrentPosition(onSuccess, onError);
  }, []);

  return location;
};

export default useGeolocation;
