import { HTTP_STATUS_CODE } from '@constant/api';
import * as Sentry from '@sentry/react';
import type { AxiosError } from 'axios';

import { HTTPError } from '@api/HTTPError';

export interface ErrorResponseData {
  statusCode?: number;
  message?: string;
  code?: number;
}

export const handleAPIError = (error: AxiosError<ErrorResponseData>) => {
  Sentry.withScope((scope) => {
    scope.setLevel('error');
    Sentry.captureMessage(`[APIError] ${window.location.href} \n ${error.response?.data}`);
  });

  if (!error.response) throw error;

  const { data, status } = error.response;

  if (status >= HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR) {
    throw new HTTPError(HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR, data.message);
  }

  throw new HTTPError(status, data.message, data.code);
};
