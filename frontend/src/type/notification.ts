import type { ReactionKeyType } from './reaction';
import type { UserData } from './user';

interface ReactionNotificationBalloonData {
  id: number;
  title: string;
  colorCode: string;
}

export interface ReactionNotificationData {
  id: number;
  receiverId: number;
  balloon: ReactionNotificationBalloonData;
  reactionType: ReactionKeyType;
  createdAt: string;
  responder: UserData;
}

export interface ReactionNotificationEvent {
  id: string;
  event: 'Reaction-Notification';
  data: ReactionNotificationData;
}
