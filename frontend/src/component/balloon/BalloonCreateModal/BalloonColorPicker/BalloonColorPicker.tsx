import { useState } from 'react';
import { CirclePicker } from 'react-color';

import Flex from '@/component/Flex/Flex';
import { BalloonColorCodes } from '@/style/BalloonColorCode';
import { createBalloonIconImage } from '@/util/balloon';

import outRangedBalloonPinIconImage from '@asset/svg/outranged-balloon-pin-icon.svg?url';

import { containerStyling, previewStyling } from './BalloonColorPicker.style';

interface BalloonColorPickerProps {
  onSelect: (colorCode: string) => void;
}

const BalloonColorPicker = ({ onSelect }: BalloonColorPickerProps) => {
  const [color, setColor] = useState<string | null>(null);

  return (
    <Flex css={containerStyling}>
      <CirclePicker
        width="200px"
        colors={BalloonColorCodes}
        onChange={(color) => {
          setColor(color.hex);
          onSelect(color.hex);
        }}
      />
      <img
        css={previewStyling}
        src={color ? createBalloonIconImage(color) : outRangedBalloonPinIconImage}
        alt="preivew"
      />
    </Flex>
  );
};

export default BalloonColorPicker;
