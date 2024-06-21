export interface GeolocationType {
  loaded: boolean;
  coordinates: { lat: number | null; lon: number | null };
}

export interface GeolocationData {
  latitude: number | null;
  longitude: number | null;
}
