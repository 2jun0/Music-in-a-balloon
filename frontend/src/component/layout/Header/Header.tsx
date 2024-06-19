import { useLocation, useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { PATH } from '@/constant/path';

import Flex from '@component/Flex/Flex';
import { headerStyling, tapNavigateLogoButtonStyling } from '@component/layout/Header/Header.style';

import { Theme } from '@style/Theme';

import LogoImage from '@asset/svg/logo-horizontal.svg';

import { isRegisteredState } from '@store/user';

const Header = () => {
  const navigate = useNavigate();
  const location = useLocation().pathname;

  const isRegistered = useRecoilValue(isRegisteredState);

  return (
    <header css={headerStyling}>
      <Flex styles={{ justify: 'space-between', align: 'center' }}>
        <Flex styles={{ align: 'center', gap: Theme.spacer.spacing4 }}>
          <LogoImage
            css={tapNavigateLogoButtonStyling}
            tabIndex={0}
            aria-label="Logo"
            onClick={() => navigate(PATH.ROOT)}
          />
        </Flex>
      </Flex>
    </header>
  );
};

export default Header;
