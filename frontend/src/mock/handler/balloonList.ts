import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { balloonList } from '@mock/data/balloonList';

export const balloonListHandlers = [
  http.get(`${END_POINTS.BALLOON_LIST}`, ({ request }) => {
    return HttpResponse.json(balloonList, { status: HTTP_STATUS_CODE.SUCCESS });
  }),
];
