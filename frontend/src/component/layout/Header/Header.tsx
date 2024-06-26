import { PATH } from '@constant/path';
import { Link } from 'react-router-dom';

import Flex from '@component/Flex/Flex';
import Text from '@component/Text/Text';
import {
  containerStyling,
  headerStyling,
  tapNavigateLogoButtonStyling,
  titleStyling,
} from '@component/layout/Header/Header.style';

import { Theme } from '@style/Theme';

import LogoImage from '@asset/svg/logo-horizontal.svg';

const Header = () => {
  return (
    <header css={headerStyling}>
      <Flex css={containerStyling}>
        <Link to={PATH.ROOT}>
          <Flex style={{ gap: Theme.spacer.spacing3 }}>
            <LogoImage css={tapNavigateLogoButtonStyling} aria-label="Logo" />
            <Text size="large" css={titleStyling}>
              Music in a balloon
            </Text>
          </Flex>
        </Link>
      </Flex>
    </header>
  );
};

export default Header;
