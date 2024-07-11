export function blendColors(colorA: string, colorB: string, amount: number) {
  const mA = colorA.match(/\w\w/g);
  const mB = colorB.match(/\w\w/g);

  if (!mA || !mB) {
    return colorA;
  }

  const [rA, gA, bA] = mA.map((c) => parseInt(c, 16));
  const [rB, gB, bB] = mB.map((c) => parseInt(c, 16));
  const r = Math.round(rA + (rB - rA) * amount)
    .toString(16)
    .padStart(2, '0');
  const g = Math.round(gA + (gB - gA) * amount)
    .toString(16)
    .padStart(2, '0');
  const b = Math.round(bA + (bB - bA) * amount)
    .toString(16)
    .padStart(2, '0');
  return `#${r}${g}${b}`;
}
