import { useCreateBalloonMutation } from '@hook/api/useCreateBalloonMutation';
import { useBalloonForm } from '@hook/balloon/useBalloonForm';
import type { FormEvent } from 'react';
import { useState } from 'react';

import Button from '@component/Button/Button';
import Heading from '@component/Heading/Heading';
import Input from '@component/Input/Input';
import Modal from '@component/Modal/Modal';
import Text from '@component/Text/Text';
import {
  backButtonStyling,
  buttonStyling,
  formStyling,
  getListStyle,
  inputStyling,
  wrapperStyling,
} from '@component/balloon/BalloonCreateModal/BalloonCreateModal.style';

interface BalloonCreateModalProps {
  isOpen?: boolean;
  onClose: () => void;
}

const BalloonCreateModal = ({ isOpen = true, onClose }: BalloonCreateModalProps) => {
  const { balloonInfo, updateMusicUrl, canPressNext, canSubmit, updateMessage } = useBalloonForm();
  const createBalloonMutation = useCreateBalloonMutation();
  const [page, setPage] = useState<0 | 1>(0);

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!canSubmit) return;

    createBalloonMutation.mutate(balloonInfo, {
      onSuccess: onClose,
    });
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
        <Heading size="small">Fly Your Music Balloon</Heading>
        {page === 0 ? (
          <>
            <ol css={getListStyle(0)}>
              <li>
                Copy the URL of your favorite music from streaming sites like YouTube Music or
                Spotify
              </li>
              <li>
                Paste the URL into the input box and click the <b>Next</b> button.
              </li>
            </ol>
            <Input
              key="musicUrl"
              required
              id="musicUrl"
              onChange={updateMusicUrl}
              css={inputStyling}
              placeholder="Enter a YouTube or Spotify music URL"
            />
            <Button
              variant={!canPressNext ? 'default' : 'primary'}
              disabled={!canPressNext}
              css={buttonStyling}
              onClick={() => setPage(1)}
            >
              Next
            </Button>
          </>
        ) : (
          <>
            <ol css={getListStyle(2)}>
              <li>
                Enter the message you want to say into the input box and click the{' '}
                <b>Fly a Music Balloon</b> button.
              </li>
            </ol>
            <Button size="small" css={backButtonStyling} onClick={() => setPage(0)}>
              <Text size="xSmall">{'<'} Back</Text>
            </Button>
            <Input
              key="message"
              required
              id="message"
              onChange={updateMessage}
              css={inputStyling}
              placeholder="Enter the message."
            />
            <Button
              variant={!canSubmit ? 'default' : 'primary'}
              disabled={!canSubmit}
              css={buttonStyling}
            >
              Fly a Music Balloon
            </Button>
          </>
        )}
      </form>
    </Modal>
  );
};

export default BalloonCreateModal;
