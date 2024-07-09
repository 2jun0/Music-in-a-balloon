import { HTTP_ERROR_MESSAGE, HTTP_STATUS_CODE } from '@constant/api';
import { useRecoilValue } from 'recoil';

import { hasKeyInObject } from '@/util/typeGuard';

import Box from '@component/Box/Box';
import Button from '@component/Button/Button';
import Flex from '@component/Flex/Flex';
import Heading from '@component/Heading/Heading';
import Text from '@component/Text/Text';
import {
  buttonStyling,
  containerStyling,
  headingStyling,
  textStyling,
} from '@component/common/Error/Error.style';

import ErrorImage from '@asset/svg/error-image.svg';

import { mediaQueryMobileState } from '@store/mediaQuery';

export interface ErrorProps {
  statusCode?: number;
  resetError?: () => void;
}

const Error = ({ statusCode = HTTP_STATUS_CODE.NOT_FOUND, resetError }: ErrorProps) => {
  const currentStatusCode =
    statusCode === HTTP_STATUS_CODE.CONTENT_TOO_LARGE ? HTTP_STATUS_CODE.BAD_REQUEST : statusCode;

  const isHTTPError = hasKeyInObject(HTTP_ERROR_MESSAGE, currentStatusCode);
  const isMobile = useRecoilValue(mediaQueryMobileState);

  if (!isHTTPError) return null;

  return (
    <Box>
      <Flex styles={{ direction: 'column', align: 'center' }} css={containerStyling}>
        <ErrorImage width={isMobile ? '80%' : '476px'} aria-label="An error image" />
        <Heading css={headingStyling} size="small">
          {HTTP_ERROR_MESSAGE[currentStatusCode].HEADING}
        </Heading>
        <Text css={textStyling}>{HTTP_ERROR_MESSAGE[currentStatusCode].BODY}</Text>
        <Button css={buttonStyling} variant="primary" onClick={resetError}>
          {HTTP_ERROR_MESSAGE[currentStatusCode].BUTTON}
        </Button>
      </Flex>
    </Box>
  );
};

export default Error;
