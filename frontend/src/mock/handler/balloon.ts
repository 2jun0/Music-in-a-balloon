import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { balloon } from '@mock/data/balloon';

export const balloonListHandlers = [
  http.post(`${END_POINTS.BALLOON}`, ({ request }) => {
    return HttpResponse.json(balloon, { status: HTTP_STATUS_CODE.CREATED });
  }),
];
