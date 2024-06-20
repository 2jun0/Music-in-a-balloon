import { useNavigate } from 'react-router-dom';

import { PATH } from '@/constant/path';

import Flex from '@component/Flex/Flex';
import { headerStyling, tapNavigateLogoButtonStyling } from '@component/layout/Header/Header.style';

import { Theme } from '@style/Theme';

import LogoImage from '@asset/svg/logo-horizontal.svg';

const Header = () => {
  const navigate = useNavigate();

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
