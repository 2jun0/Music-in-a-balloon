import { balloonHandlers } from '@mock/handler/balloon';
import { balloonListHandlers } from '@mock/handler/balloonList';
import { geolocationHandlers } from '@mock/handler/geolocation';
import { musicHandlers } from '@mock/handler/music';
import { userHandlers } from '@mock/handler/user';
import { waveHandlers } from '@mock/handler/wave';

export const handlers = [
  ...musicHandlers,
  ...balloonListHandlers,
  ...waveHandlers,
  ...userHandlers,
  ...balloonHandlers,
  ...geolocationHandlers,
];
