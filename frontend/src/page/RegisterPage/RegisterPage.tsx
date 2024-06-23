import { PATH } from '@constant/path';
import { USER_NAME_MAX_LENGTH } from '@constant/ui';
import { useNewUserMutation } from '@hook/api/useRegisterMutation';
import { useState } from 'react';
import type { ChangeEvent, FormEvent } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import {
  buttonContainerStyling,
  buttonStyling,
  containerStyling,
  headingStyling,
} from '@/page/RegisterPage/RegisterPage.style';

import Button from '@component/Button/Button';
import Flex from '@component/Flex/Flex';
import Heading from '@component/Heading/Heading';
import Input from '@component/Input/Input';

import type { RegisterData } from '@type/user';

import { Theme } from '@style/Theme';

import NewUserImage from '@asset/svg/new-user-image.svg';

import { isRegisteredState } from '@store/user';

const initUserData = {
  username: '',
};

export const NewUserPage = () => {
  const navigate = useNavigate();
  const isRegistered = useRecoilValue(isRegisteredState);
  const [registerData, setRegisterData] = useState<RegisterData>(initUserData);
  const newUserMutation = useNewUserMutation();

  if (isRegistered) return <Navigate to={PATH.MAP} />;

  const updateUsername = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const username = event.target.value;

    setRegisterData((prev) => ({ ...prev, username }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    newUserMutation.mutate(registerData, {
      onSuccess: goToMap,
    });
  };

  const goToMap = () => {
    navigate(PATH.MAP);
  };

  return (
    <Flex
      styles={{ direction: 'column', justify: 'center', align: 'center' }}
      css={containerStyling}
    >
      <NewUserImage />
      <Heading size="small" css={headingStyling}>
        Introduce yourself!
      </Heading>
      <form onSubmit={handleSubmit}>
        <Flex
          styles={{ direction: 'column', gap: Theme.spacer.spacing1, align: 'stretch' }}
          css={buttonContainerStyling}
        >
          <Input
            required
            id="username"
            maxLength={USER_NAME_MAX_LENGTH}
            onChange={updateUsername}
          />
          <Button variant="primary" disabled={!registerData.username} css={buttonStyling}>
            Go to fly a balloon
          </Button>
        </Flex>
      </form>
    </Flex>
  );
};

export default NewUserPage;
