import { END_POINTS, HTTP_STATUS_CODE } from '@constant/api';
import { HttpResponse, http } from 'msw';

import { geolocation } from '@mock/data/geolocation';

export const geolocationHandlers = [
  http.get(`${END_POINTS.GEOLOCATION}`, ({ request }) => {
    return HttpResponse.json(geolocation, { status: HTTP_STATUS_CODE.SUCCESS });
  }),
];
