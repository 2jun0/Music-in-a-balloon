import balloonPinIconSvg from '@asset/svg/balloon-pin-icon.svg?raw';

export const createBalloonIconImage = (colorCode: string) => {
  return `data:image/svg+xml;utf8,${encodeURIComponent(balloonPinIconSvg.replace('{color}', colorCode))}`;
};
