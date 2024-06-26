import { useBalloonQuery } from '@hook/api/useBalloonQuery';
import type { FormEvent } from 'react';
import { useRecoilValue } from 'recoil';

import Flex from '@/component/Flex/Flex';
import Input from '@/component/Input/Input';

import Heading from '@component/Heading/Heading';
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

import { pickedBalloonIdState } from '@store/balloon';

interface BalloonInfoModalProps {
  isOpen?: boolean;
  onClose: () => void;
}

const BalloonInfoModal = ({ isOpen = true, onClose }: BalloonInfoModalProps) => {
  const pickedBalloonId = useRecoilValue(pickedBalloonIdState);
  const { balloonData } = useBalloonQuery(pickedBalloonId);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

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
