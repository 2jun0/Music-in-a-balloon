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
    HEADING: 'This is not the web page you are looking for.',
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
