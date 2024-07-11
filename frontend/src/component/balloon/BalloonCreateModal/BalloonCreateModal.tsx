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
  getListStyle,
  inputStyling,
  wrapperStyling,
} from '@component/balloon/BalloonCreateModal/BalloonCreateModal.style';

import BalloonColorPicker from './BalloonColorPicker/BalloonColorPicker';
import MusicPreview from './MusicPreview/MusicPreview';

interface BalloonCreateModalProps {
  isOpen?: boolean;
  onClose: () => void;
}

const BalloonCreateModal = ({ isOpen = true, onClose }: BalloonCreateModalProps) => {
  const {
    balloonInfo,
    updateMusicUrl,
    canSumitMusicUrl,
    updateMessage,
    updateColorCode,
    canSubmitBalloon,
    musicData,
  } = useBalloonForm();
  const createBalloonMutation = useCreateBalloonMutation();
  const [page, setPage] = useState<0 | 1>(0);

  const submitMusicUrl = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!canSumitMusicUrl) return;

    setPage(1);
  };

  const submitBalloon = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!canSubmitBalloon) return;

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
      <Heading size="small">Fly Your Music Balloon</Heading>

      {page === 0 ? (
        <form onSubmit={submitMusicUrl} noValidate>
          <ol css={getListStyle(0)}>
            <li>
              Copy the URL of your favorite music from streaming sites like YouTube Music or Spotify
            </li>
            <li>
              Paste the URL into the input box and click the <b>Next</b> button.
            </li>
          </ol>
          {musicData ? (
            <MusicPreview musicData={musicData} />
          ) : (
            // eslint-disable-next-line react/jsx-no-useless-fragment
            <></>
          )}
          <Input
            key="musicUrl"
            required
            id="musicUrl"
            onChange={updateMusicUrl}
            css={inputStyling}
            value={balloonInfo.streamingMusicUrl ?? ''}
            placeholder="Enter a YouTube or Spotify music URL"
          />
          <Button
            variant={!canSumitMusicUrl ? 'default' : 'primary'}
            disabled={!canSumitMusicUrl}
            css={buttonStyling}
          >
            Next
          </Button>
        </form>
      ) : (
        <form onSubmit={submitBalloon} noValidate>
          <ol css={getListStyle(2)}>
            <li>
              Enter the message you want to say into the input box and click the{' '}
              <b>Fly a Music Balloon</b> button.
            </li>
          </ol>
          <Button size="small" css={backButtonStyling} onClick={() => setPage(0)}>
            <Text size="xSmall">{'<'} Back</Text>
          </Button>
          <BalloonColorPicker onSelect={updateColorCode} />
          <Input
            key="message"
            required
            id="message"
            onChange={updateMessage}
            css={inputStyling}
            value={balloonInfo.message ?? ''}
            placeholder="Enter the message."
          />
          <Button
            variant={!canSubmitBalloon ? 'default' : 'primary'}
            disabled={!canSubmitBalloon}
            css={buttonStyling}
          >
            Fly a Music Balloon
          </Button>
        </form>
      )}
    </Modal>
  );
};

export default BalloonCreateModal;
