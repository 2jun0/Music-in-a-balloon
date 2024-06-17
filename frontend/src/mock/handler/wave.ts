import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { wave } from '@mock/data/wave';

export const waveHandlers = [
  http.get(`${END_POINTS.WAVE}`, ({ request }) => {
    return HttpResponse.json(wave, { status: HTTP_STATUS_CODE.SUCCESS });
  }),
];
