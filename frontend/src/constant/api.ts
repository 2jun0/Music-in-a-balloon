export const PROD = process.env.NODE_ENV === 'production';

export const PROD_BASE_URL = 'api.musicinaballoon.site';

export const BASE_URL = PROD
  ? `${window.location.protocol}//${PROD_BASE_URL}`
  : 'http://localhost:3000';

export const AXIOS_BASE_URL = PROD ? `${window.location.protocol}//${PROD_BASE_URL}` : '/';

export const END_POINTS = {
  BALLOON_LIST: '/balloon/list',
  BALLOON_PICK: (balloonId: number) => `/balloon/${balloonId}/pick`,
  BALLOON_PICKED: '/balloon/picked',
  BALLOON_REACTION: (balloonId: number) => `/balloon/${balloonId}/reaction`,
  BALLOON_CREATE: '/balloon',
  BALLOON: (balloonId: number) => `/balloon/${balloonId}`,
  MUSIC: '/music',
  WAVE: '/wave',
  USER: '/user',
  USER_ME: '/user/me',
  GEOLOCATION: '/geolocation',
  REACTION_NOTIFICATION_SUBSCRIBE: '/notification/reaction/subscribe',
} as const;

export const NETWORK = {
  RETRY_COUNT: 2,
  TIMEOUT: 10000,
} as const;

export const HTTP_STATUS_CODE = {
  SUCCESS: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  NOT_FOUND: 404,
  CONTENT_TOO_LARGE: 413,
  INTERNAL_SERVER_ERROR: 500,
} as const;

export const HTTP_ERROR_MESSAGE = {
  [HTTP_STATUS_CODE.NOT_FOUND]: {
    HEADING: "This isn't the web page you are looking for.",
    BODY: 'Please go home.',
    BUTTON: 'Go home',
  },
  [HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR]: {
    HEADING: "This page can't be displayed.",
    BODY: 'Please try again later.',
    BUTTON: 'Refresh',
  },
  [HTTP_STATUS_CODE.BAD_REQUEST]: {
    HEADING: 'The request was invalid.',
    BODY: 'Please check and try again.',
    BUTTON: 'Go home',
  },
} as const;

export const ERROR_MESSAGE = 'An error occured. Please try again later.';
