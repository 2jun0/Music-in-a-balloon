import { HTTP_STATUS_CODE } from '@constant/api';
import type { AxiosError } from 'axios';

import { HTTPError } from '@api/HTTPError';

export interface ErrorResponseData {
  statusCode?: number;
  message?: string;
}

export const handleAPIError = (error: AxiosError<ErrorResponseData>) => {
  if (!error.response) throw error;

  const { data, status } = error.response;

  if (status >= HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR) {
    throw new HTTPError(HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR, data.message);
  }

  throw new HTTPError(status, data.message);
};
