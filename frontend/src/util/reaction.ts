import type { ReactionKeyType } from '@/type/reaction';

export function reactionKeyTypeToEmoji(reaction: ReactionKeyType) {
  switch (reaction) {
    case 'BALLOON':
      return '🎈';
    case 'EYES':
      return '👀';
    case 'HEART':
      return '❤️';
    case 'SMILE':
      return '😄';
    case 'THUMBS_DOWN':
      return '👎';
    case 'THUMBS_UP':
    default:
      return '👍';
  }
}
