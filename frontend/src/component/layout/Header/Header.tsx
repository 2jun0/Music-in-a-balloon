import { PATH } from '@constant/path';
import { Link } from 'react-router-dom';

import Flex from '@component/Flex/Flex';
import {
  centerStyling,
  headerStyling,
  iconStyling,
  menuContainerStyling,
} from '@component/layout/Header/Header.style';

import HistoryIconImage from '@asset/svg/history-icon.svg';
import LogoImage from '@asset/svg/logo-horizontal.svg';

const Header = () => {
  return (
    <header css={headerStyling}>
      <Flex>
        <Flex css={menuContainerStyling}>
          <Link to={PATH.HISTORY}>
            <HistoryIconImage css={iconStyling} aria-label="History" />
          </Link>
          <Flex css={centerStyling}>
            <Link to={PATH.ROOT}>
              <LogoImage css={iconStyling} aria-label="Logo" />
            </Link>
          </Flex>
          <HistoryIconImage css={iconStyling} fill="transparent" aria-label="History" />
        </Flex>
      </Flex>
    </header>
  );
};

export default Header;
