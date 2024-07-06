import type { WaveData } from '@type/wave';

import { toRadians } from '@util/math';

function func(wave: WaveData, lonDegree: number) {
  return wave.amplitude * 90 * Math.sin(wave.period * toRadians(lonDegree + wave.offsetLongitude));
}

export function calcCoordinate(
  wave: WaveData,
  baseLat: number,
  baseLon: number,
  timeDeltaSec: number,
) {
  const lon = baseLon + wave.velocity * timeDeltaSec;
  const lat = func(wave, lon) + baseLat - func(wave, baseLon);

  return { lon, lat };
}
