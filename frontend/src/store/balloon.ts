import { atom } from 'recoil';

export const selectedBalloonIdState = atom<number>({
  key: 'selectedBalloonId',
  default: 0,
});
