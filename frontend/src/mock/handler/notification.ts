import { END_POINTS } from '@constant/api';
import { HttpResponse, delay, http } from 'msw';

import { reactionNotificationEvents } from '../data/notification';

const encoder = new TextEncoder();

export const notificationHandlers = [
  http.get(`${END_POINTS.REACTION_NOTIFICATION_SUBSCRIBE}`, () => {
    const stream = new ReadableStream({
      async start(controller) {
        controller.enqueue(encoder.encode('id: 0\nevent: Dummy\ndata: Dummy\n\n'));
        // eslint-disable-next-line no-restricted-syntax
        for (const event of reactionNotificationEvents) {
          // eslint-disable-next-line no-await-in-loop
          await delay();
          const message = `id: ${event.id}\nevent: ${event.event}\ndata: ${JSON.stringify(event.data)}\n\n`;
          controller.enqueue(encoder.encode(message));
        }
      },
    });

    return new HttpResponse(stream, {
      headers: {
        'Content-Type': 'text/event-stream',
      },
    });
  }),
];
