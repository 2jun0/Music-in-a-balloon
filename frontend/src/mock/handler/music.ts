import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { spotifyMusic } from '@mock/data/music';

export const musicHandlers = [
  http.get(END_POINTS.MUSIC, ({ request }) => {
    return HttpResponse.json(spotifyMusic, { status: HTTP_STATUS_CODE.SUCCESS });
  }),
];
