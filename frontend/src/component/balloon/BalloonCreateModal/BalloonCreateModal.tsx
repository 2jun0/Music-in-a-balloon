import { useBalloonForm } from '@/hook/balloon/useBalloonForm';

import Button from '@component/Button/Button';
import Heading from '@component/Heading/Heading';
import Input from '@component/Input/Input';
import Modal from '@component/Modal/Modal';
import {
  buttonStyling,
  formStyling,
  inputStyling,
  listStyle,
  wrapperStyling,
} from '@component/balloon/BalloonCreateModal/BalloonCreateModal.style';

interface BalloonCreateModalProps {
  isOpen?: boolean;
  onClose: () => void;
}

const BalloonCreateModal = ({ isOpen = true, onClose }: BalloonCreateModalProps) => {
  const { updateMusicUrl, handleSubmit, isValidated } = useBalloonForm();

  return (
    <Modal
      css={wrapperStyling}
      isOpen={isOpen}
      closeModal={onClose}
      isBackdropClosable={false}
      hasCloseButton
    >
      <form css={formStyling} onSubmit={handleSubmit} noValidate>
        <Heading size="small">Fly Your Music Balloon</Heading>
        <ol css={listStyle}>
          <li>
            Copy the URL of your favorite music from streaming sites like YouTube Music or Spotify
          </li>
          <li>
            Pastes the URL into the input box and click the &apos;<b>fly a music balloon</b>&apos;
            button.
          </li>
        </ol>
        <Input
          required
          id="musicUrl"
          onChange={updateMusicUrl}
          css={inputStyling}
          placeholder="Enter a YouTube or Spotify music URL"
        />
        <Button
          variant={!isValidated ? 'default' : 'primary'}
          disabled={!isValidated}
          css={buttonStyling}
        >
          fly a music balloon
        </Button>
      </form>
    </Modal>
  );
};

export default BalloonCreateModal;
