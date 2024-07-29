import { atom } from 'recoil';

import type { UserData } from '@/type/user';

export const isRegisteredState = atom<boolean>({
  key: 'isRegistered',
  default: false,
});

export const meState = atom<UserData>({
  key: 'me',
  default: {
    id: 0,
    name: '',
  },
});
