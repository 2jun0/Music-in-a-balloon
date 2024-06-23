import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { user } from '@mock/data/user';

export const userHandlers = [
  http.post(`${END_POINTS.USER}`, ({ request }) => {
    return HttpResponse.json(null, { status: HTTP_STATUS_CODE.CREATED });
  }),

  http.get(`${END_POINTS.USER_ME}`, ({ request }) => {
    return HttpResponse.json(user, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  // http.get(`${END_POINTS.USER_ME}`, ({ request }) => {
  //   return HttpResponse.json(null, { status: HTTP_STATUS_CODE.UNAUTHORIZED });
  // }),
];
