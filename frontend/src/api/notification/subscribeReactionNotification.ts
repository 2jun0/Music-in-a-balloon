import { END_POINTS } from '@constant/api';

export const subscribeReactionNotification = () => {
  const eventSource = new EventSource(END_POINTS.REACTION_NOTIFICATION_SUBSCRIBE, {
    withCredentials: true,
  });

  return eventSource;
};
