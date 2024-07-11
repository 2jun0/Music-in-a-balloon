import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { balloons } from '@mock/data/balloon';

export const balloonHandlers = [
  http.post(`${END_POINTS.BALLOON_CREATE}`, () => {
    return HttpResponse.json(balloons[0], { status: HTTP_STATUS_CODE.CREATED });
  }),

  http.post('/balloon/:balloonId/pick', ({ params }) => {
    return HttpResponse.json(balloons[Number(params.balloonId) - 1], {
      status: HTTP_STATUS_CODE.SUCCESS,
    });
  }),

  http.put('/balloon/:balloonId/reaction', () => {
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  http.delete('/balloon/:balloonId/reaction', () => {
    return new HttpResponse(null, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  http.get('/balloon/:balloonId', ({ params }) => {
    return HttpResponse.json(balloons[Number(params.balloonId) - 1], {
      status: HTTP_STATUS_CODE.SUCCESS,
    });
  }),
];
