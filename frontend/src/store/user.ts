import { atom } from 'recoil';

export const isRegisteredState = atom({
  key: 'isRegistered',
  default: false,
});
