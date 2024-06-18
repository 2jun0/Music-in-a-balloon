import { balloonListHandlers } from '@mock/handler/balloonList';
import { userHandlers } from '@mock/handler/user';
import { waveHandlers } from '@mock/handler/wave';

export const handlers = [...balloonListHandlers, ...waveHandlers, ...userHandlers];
