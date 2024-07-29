import type { ReactionNotificationEvent } from '@/type/notification';

import { balloons } from './balloon';

export const reactionNotificationEvents: ReactionNotificationEvent[] = [
  {
    id: '2024-07-28T07:04:57.238624Z',
    event: 'Reaction-Notification',
    data: {
      id: 1,
      receiverId: 1,
      balloon: {
        id: 1,
        title: 'Super Shy',
        colorCode: balloons[0].colorCode,
      },
      reactionType: 'BALLOON',
      createdAt: '1722150297.238624000',
      responder: {
        id: 2,
        name: 'hanyrrrrrang',
      },
    },
  },
  {
    id: '2024-07-28T07:04:57.238624Z',
    event: 'Reaction-Notification',
    data: {
      id: 2,
      receiverId: 1,
      balloon: {
        id: 2,
        title: '밤양갱',
        colorCode: balloons[1].colorCode,
      },
      reactionType: 'SMILE',
      createdAt: '1722150297.238624000',
      responder: {
        id: 3,
        name: '비비',
      },
    },
  },
  {
    id: '2024-07-28T07:04:57.238624Z',
    event: 'Reaction-Notification',
    data: {
      id: 3,
      receiverId: 1,
      balloon: {
        id: 2,
        title: '밤양갱',
        colorCode: balloons[1].colorCode,
      },
      reactionType: 'HEART',
      createdAt: '1722150297.238624000',
      responder: {
        id: 2,
        name: 'hanyrrrrrang',
      },
    },
  },
];
