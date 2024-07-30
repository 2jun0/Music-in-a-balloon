import { BASE_URL, END_POINTS } from '@constant/api';

export const subscribeReactionNotification = () => {
  const eventSource = new EventSource(BASE_URL + END_POINTS.REACTION_NOTIFICATION_SUBSCRIBE, {
    withCredentials: true,
  });

  return eventSource;
};
