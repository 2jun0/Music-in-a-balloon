import { atom } from 'recoil';

export const pickedBalloonIdState = atom<number>({
  key: 'pickedBalloonId',
  default: 0,
});
