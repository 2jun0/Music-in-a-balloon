import Flex from '@/component/Flex/Flex';

import Text from '@component/Text/Text';
import { containerStyling, footerStyling } from '@component/layout/Footer/Footer.style';

const Footer = () => {
  return (
    <footer css={footerStyling}>
      <Flex css={containerStyling}>
        <Text>©️ 2024 Music in a balloon All rights reserved</Text>
      </Flex>
    </footer>
  );
};
export default Footer;
