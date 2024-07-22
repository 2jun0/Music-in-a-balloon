import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { balloonList, getBalloonPicked } from '@mock/data/balloonList';

export const balloonListHandlers = [
  http.get(`${END_POINTS.BALLOON_LIST}`, () => {
    return HttpResponse.json(balloonList, { status: HTTP_STATUS_CODE.SUCCESS });
  }),

  http.get('/balloon/picked', ({ request }) => {
    const url = new URL(request.url);
    const page = url.searchParams.get('page') ?? 0;
    console.log(page, getBalloonPicked(Number(page)));
    return HttpResponse.json(getBalloonPicked(Number(page)), {
      status: HTTP_STATUS_CODE.SUCCESS,
    });
  }),
];
