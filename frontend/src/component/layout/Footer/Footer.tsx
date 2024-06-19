import Text from '@component/Text/Text';
import { containerStyling } from '@component/layout/Footer/Footer.style';

const Footer = () => {
  return (
    <footer css={containerStyling}>
      <Text>©️ 2024 Music in a balloon All rights reserved</Text>
    </footer>
  );
};
export default Footer;
