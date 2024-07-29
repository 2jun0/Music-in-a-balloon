import { ThemeProvider } from '@emotion/react';
import type { PropsWithChildren } from 'react';

import ToastProvider from '@component/ToastProvider/ToastProvider';

import { theme } from './style/Theme';

type GlobalProviderProps = PropsWithChildren;

const GlobalProvider = ({ children }: GlobalProviderProps) => (
  <ThemeProvider theme={theme}>
    {children}
    <ToastProvider />
  </ThemeProvider>
);

export default GlobalProvider;
