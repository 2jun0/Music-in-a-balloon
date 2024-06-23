import { atom } from 'recoil';

export const clickedMarkerIdState = atom<number>({
  key: 'clickedMarkerId',
  default: 0,
});
