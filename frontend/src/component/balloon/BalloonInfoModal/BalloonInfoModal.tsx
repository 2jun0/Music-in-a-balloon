import { useBalloonQuery } from '@hook/api/useBalloonQuery';
import JSConfetti from 'js-confetti';
import { FormEvent, useEffect } from 'react';
import { useRecoilValue } from 'recoil';

import Flex from '@component/Flex/Flex';
import Heading from '@component/Heading/Heading';
import Input from '@component/Input/Input';
import Modal from '@component/Modal/Modal';
import Text from '@component/Text/Text';
import {
  albumImageStyling,
  containerStyling,
  inputStyling,
  linkContainerStyling,
  listenMusicContainerStyling,
  modalStyling,
  replyFormStyling,
} from '@component/balloon/BalloonInfoModal/BalloonInfoModal.style';
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
  const pickedBalloonId = useRecoilValue(pickedBalloonIdState);
  const { balloonData } = useBalloonQuery(pickedBalloonId);

  const confettiCanvas = document.getElementById('confetti-canvas');
  const jsConfetti = new JSConfetti({ canvas: confettiCanvas as unknown as HTMLCanvasElement });

  // const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
  //   e.preventDefault();
  // };

  // 색종이 커스터마이징
  useEffect(() => {
    switch (pickedBalloonId % 3) {
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
        <img
          css={albumImageStyling}
          src={balloonData.albumImageUrl}
          alt={`${balloonData.title} album`}
        />
        <Flex css={listenMusicContainerStyling}>
          <Text size="large">🎧 Go to listen this music 🎧</Text>
          <Flex css={linkContainerStyling}>
            <YouTubeButton videoUrl="https://www.youtube.com/watch?v=Y25LDO6OLzQ" />
            <YTMusicButton musicUrl="https://music.youtube.com/watch?v=MDNzHG4DDhE&si=_fjhW7PsBzpiAjY-" />
            <SpotifyButton musicUrl="https://music.youtube.com/watch?v=MDNzHG4DDhE&si=_fjhW7PsBzpiAjY-" />
          </Flex>
        </Flex>
        <Text size="small">From. username</Text>
        <Text>{balloonData.message}</Text>
        {/* <form css={replyFormStyling} onSubmit={handleSubmit} noValidate>
          <Heading size="xSmall">Reply</Heading>
          <Input css={inputStyling} placeholder="Enter the reply." />
        </form> */}
      </Flex>
    </Modal>
  );
};

export default BalloonInfoModal;
