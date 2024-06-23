export function toRadians(degree: number) {
  return (degree * Math.PI) / 180;
}

export function normalizeLongitude(lon: number) {
  return ((lon + 180 + 360000) % 360) - 180;
}

export function normalizeLatitude(lat: number) {
  if (lat > 90) {
    return 90 - ((lat + 90) % 360);
  }
  if (lat <= -90) {
    return -270 - ((lat + 90) % 360);
  }
  return lat;
}
