import { useEffect, useState } from 'react';

export const useCurrentMs = (delayMs: number) => {
  const [currentMs, setCurrentTimeDelta] = useState<number>(Date.now());

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentTimeDelta(Date.now());
    }, delayMs);

    return () => clearInterval(interval);
  }, [delayMs]);

  return { current: currentMs };
};
