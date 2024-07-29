import { PATH } from '@constant/path';
import HistoryIcon from '@mui/icons-material/History';
import { AppBar, Box, Button, IconButton, Toolbar } from '@mui/material';

import { headerStyling, toolbarStyling } from '@component/layout/Header/Header.style';

import LogoImage from '@asset/svg/logo-horizontal.svg';

import Notifications from './Notifications/Notifications';

const Header = () => {
  return (
    <AppBar position="static" css={headerStyling}>
      <Toolbar css={toolbarStyling} sx={{ minHeight: 56, height: 56 }}>
        <Button aria-label="Logo" href={PATH.ROOT}>
          <LogoImage aria-label="Logo" />
        </Button>
        <Box sx={{ marginLeft: 'auto' }} />
        <IconButton aria-label="History" href={PATH.HISTORY}>
          <HistoryIcon />
        </IconButton>
        <Notifications />
      </Toolbar>
    </AppBar>
  );
};

export default Header;
