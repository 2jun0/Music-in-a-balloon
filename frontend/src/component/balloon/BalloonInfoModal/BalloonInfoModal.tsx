import { useBalloonQuery } from '@hook/api/useBalloonQuery';
import type { FormEvent } from 'react';
import { useRecoilValue } from 'recoil';

import Box from '@component/Box/Box';
import Heading from '@component/Heading/Heading';
import Modal from '@component/Modal/Modal';
import {
  formStyling,
  linkContainerStyling,
  wrapperStyling,
} from '@component/balloon/BalloonInfoModal/BalloonInfoModal.style';
import SpotifyButton from '@component/common/SpotifyButton/SpotifyButton';
import YTMusicButton from '@component/common/YTMusicButton/YTMusicButton';

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
      css={wrapperStyling}
      isOpen={isOpen}
      closeModal={onClose}
      isBackdropClosable={false}
      hasCloseButton
    >
      <form css={formStyling} onSubmit={handleSubmit} noValidate>
        <Heading size="small">{balloonData.title}</Heading>
        <img src={balloonData.albumImageUrl} alt={`${balloonData.title} album`} />
        <Box css={linkContainerStyling}>
          <YTMusicButton musicUrl="https://music.youtube.com/watch?v=MDNzHG4DDhE&si=_fjhW7PsBzpiAjY-" />
          <SpotifyButton musicUrl="https://music.youtube.com/watch?v=MDNzHG4DDhE&si=_fjhW7PsBzpiAjY-" />
        </Box>
      </form>
    </Modal>
  );
};

export default BalloonInfoModal;
