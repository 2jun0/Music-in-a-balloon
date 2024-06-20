import { atom } from 'recoil';

import type { GeolocationType } from '@type/geolocation';

export const geolocationState = atom<GeolocationType>({
  key: 'geolocation',
  default: {
    loaded: false,
    coordinates: {
      lat: null,
      lon: null,
    },
  },
});
