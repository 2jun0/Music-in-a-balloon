declare module '*.svg' {
  import type React from 'react';

  const SVG: React.FC<React.SVGProps<SVGSVGElement>>;
  export default SVG;
}

declare module '*.svg?url' {
  const value: string;
  export default value;
}

declare module '*.png';

declare module '*.jpg';
