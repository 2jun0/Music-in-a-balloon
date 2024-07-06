import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { balloon } from '@mock/data/balloon';

export const balloonHandlers = [
  http.post(`${END_POINTS.BALLOON_CREATE}`, ({ request }) => {
    return HttpResponse.json(balloon, { status: HTTP_STATUS_CODE.CREATED });
  }),

  http.post('/balloon/:balloonId/pick', ({ request }) => {
    return HttpResponse.json(balloon, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  http.put('/balloon/:balloonId/reaction', ({ request }) => {
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  http.delete('/balloon/:balloonId/reaction', ({ request }) => {
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  http.get('/balloon/:balloonId', ({ request }) => {
    return HttpResponse.json(balloon, { status: HTTP_STATUS_CODE.SUCCESS });
  }),
];
