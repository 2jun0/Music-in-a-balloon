import { useBalloonQuery } from '@hook/api/useBalloonQuery';
import JSConfetti from 'js-confetti';
import { useEffect, useState } from 'react';
import { useRecoilValue } from 'recoil';

import { useReactBalloonMutation } from '@/hook/api/useReactBalloonMutation';
import { useUndoReactBalloonMutation } from '@/hook/api/useUndoReactBalloonMutation';
import type { ReactionKeyType } from '@/type/reaction';

import Flex from '@component/Flex/Flex';
import Heading from '@component/Heading/Heading';
import Modal from '@component/Modal/Modal';
import Text from '@component/Text/Text';
import {
  albumImageStyling,
  bottomContainerStyling,
  containerStyling,
  creatorNameStyling,
  linkContainerStyling,
  listenMusicContainerStyling,
  modalStyling,
} from '@component/balloon/BalloonInfoModal/BalloonInfoModal.style';
import ReactionSelector from '@component/balloon/BalloonInfoModal/ReactionSelector/ReactionSelector';
import SpotifyButton from '@component/common/SpotifyButton/SpotifyButton';
import YTMusicButton from '@component/common/YTMusicButton/YTMusicButton';
import YouTubeButton from '@component/common/YouTubeButton/YouTubeButton';

import { Theme } from '@style/Theme';

import { pickedBalloonIdState } from '@store/balloon';

interface BalloonInfoModalProps {
  isOpen?: boolean;
  onClose: () => void;
}

const BalloonInfoModal = ({ isOpen = true, onClose }: BalloonInfoModalProps) => {
  const balloonId = useRecoilValue(pickedBalloonIdState);
  const { balloonData } = useBalloonQuery(balloonId);
  const reactBalloonMutation = useReactBalloonMutation();
  const undoReactBalloonMutation = useUndoReactBalloonMutation();
  const [selectedReactionKey, setSelectedReactionKey] = useState<ReactionKeyType | null>(null);

  const confettiCanvas = document.getElementById('confetti-canvas');
  const jsConfetti = new JSConfetti({ canvas: confettiCanvas as unknown as HTMLCanvasElement });

  const onReact = (reactionKey: ReactionKeyType | null) => {
    if (reactionKey) {
      reactBalloonMutation.mutate(
        { balloonId, data: { balloonReactionType: reactionKey } },
        {
          onSuccess: () => {
            setSelectedReactionKey(reactionKey);
          },
        },
      );
    } else {
      undoReactBalloonMutation.mutate(balloonId, {
        onSuccess: () => {
          setSelectedReactionKey(null);
        },
      });
    }
  };

  // 색종이 커스터마이징
  useEffect(() => {
    switch (balloonId % 3) {
      case 0:
        jsConfetti.addConfetti({
          confettiColors: [
            Theme.color.pink100,
            Theme.color.pink200,
            Theme.color.pink300,
            Theme.color.pink400,
          ],
          confettiRadius: 5,
          confettiNumber: 500,
        });
        break;
      case 1:
        jsConfetti.addConfetti({
          confettiColors: [
            Theme.color.blue100,
            Theme.color.blue200,
            Theme.color.blue300,
            Theme.color.blue400,
          ],
          confettiRadius: 5,
          confettiNumber: 500,
        });
        break;
      case 2:
        jsConfetti.addConfetti({
          confettiColors: [
            Theme.color.orange100,
            Theme.color.orange200,
            Theme.color.orange300,
            Theme.color.orange400,
          ],
          confettiRadius: 5,
          confettiNumber: 500,
        });
        break;
      default:
        break;
    }
  }, []);

  return (
    <Modal
      css={modalStyling}
      isOpen={isOpen}
      closeModal={onClose}
      isBackdropClosable={false}
      hasCloseButton
    >
      <Flex css={containerStyling}>
        <Heading size="small">{balloonData.title}</Heading>
        <Text>{balloonData.message}</Text>
        <img
          css={albumImageStyling}
          src={balloonData.albumImageUrl}
          alt={`${balloonData.title} album`}
        />
        <Flex css={listenMusicContainerStyling}>
          <Text size="large">🎧 Go to listen this music 🎧</Text>
          <Flex css={linkContainerStyling}>
            <YouTubeButton videoUrl="https://www.youtube.com/watch?v=Y25LDO6OLzQ" />
            {balloonData.youtubeMusic ? (
              <YTMusicButton musicUrl={balloonData.youtubeMusic.url} />
            ) : (
              // eslint-disable-next-line react/jsx-no-useless-fragment
              <></>
            )}
            {balloonData.spotifyeMusic ? (
              <SpotifyButton musicUrl={balloonData.spotifyeMusic.url} />
            ) : (
              // eslint-disable-next-line react/jsx-no-useless-fragment
              <></>
            )}
          </Flex>
        </Flex>
        <Flex css={bottomContainerStyling}>
          <ReactionSelector selectedKey={selectedReactionKey} onSelect={onReact} />
          <Text css={creatorNameStyling} size="small">
            From. username
          </Text>
        </Flex>
      </Flex>
    </Modal>
  );
};

export default BalloonInfoModal;
