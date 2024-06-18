export function getTimeDeltaSecFrom(timeDeltaMilli: number) {
  const deltaMilli = Date.now() - timeDeltaMilli;
  return deltaMilli / 1000;
}
