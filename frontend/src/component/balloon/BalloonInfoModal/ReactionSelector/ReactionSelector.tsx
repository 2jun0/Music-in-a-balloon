import type { Reaction as _Reaction } from '@charkour/react-reactions';
import { ReactionBarSelector } from '@charkour/react-reactions';
import { useEffect, useState } from 'react';

import Flex from '@component/Flex/Flex';

import type { ReactionKeyType } from '@type/reaction';

import { growRight } from '@style/animation';

import ReactionIconImage from '@asset/svg/reaction-icon.svg';

interface ReactionSelectorProps {
  selectedKey: ReactionKeyType | null;
  onSelect: (reaction: ReactionKeyType | null) => void;
}

interface Reaction extends _Reaction {
  key: ReactionKeyType;
}

const ReactionSelector = ({ selectedKey, onSelect }: ReactionSelectorProps) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selected, setSelected] = useState<Reaction | null>(null);

  const reactions: Reaction[] = [
    {
      label: 'ThumbsUp',
      node: <Flex>👍</Flex>,
      key: 'THUMBS_UP',
    },
    {
      label: 'ThumbsDown',
      node: <Flex>👎</Flex>,
      key: 'THUMBS_DOWN',
    },
    {
      label: 'Balloon',
      node: <Flex>🎈</Flex>,
      key: 'BALLOON',
    },
    {
      label: 'Smile',
      node: <Flex>😄</Flex>,
      key: 'SMILE',
    },
    {
      label: 'Heart',
      node: <Flex>❤️</Flex>,
      key: 'HEART',
    },
    {
      label: 'Eyes',
      node: <Flex>👀</Flex>,
      key: 'EYES',
    },
  ];

  const getReactionByKey = (key: string | null) => {
    return reactions.find((reaction) => reaction.key === key) ?? null;
  };

  const toggleReaction = {
    label: isOpen ? 'Undo' : 'Reaction',
    node: (
      <Flex style={{ alignItems: 'center' }}>
        <ReactionIconImage />
      </Flex>
    ),
    key: 'TOGGLE',
  };

  const onSelectReaction = (key: string) => {
    setIsOpen((isOpen) => !isOpen);
    if (key === 'TOGGLE') {
      if (isOpen) onSelect(null);
    } else {
      onSelect(key as ReactionKeyType);
    }
  };

  useEffect(() => {
    setSelected(getReactionByKey(selectedKey));
  }, [selectedKey]);

  return isOpen ? (
    <ReactionBarSelector
      style={{ animation: `${growRight} 0.2s infinite` }}
      reactions={[toggleReaction, ...reactions]}
      iconSize={18}
      onSelect={onSelectReaction}
    />
  ) : (
    <ReactionBarSelector
      reactions={[selected ?? toggleReaction]}
      iconSize={18}
      onSelect={onSelectReaction}
    />
  );
};

export default ReactionSelector;
