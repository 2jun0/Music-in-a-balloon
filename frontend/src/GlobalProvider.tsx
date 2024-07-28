import { Global, ThemeProvider } from '@emotion/react';
import type { PropsWithChildren } from 'react';

import ToastProvider from '@component/ToastProvider/ToastProvider';

import { GlobalStyle } from '@style/GlobalStyle';

import { theme } from './style/Theme';

type GlobalProviderProps = PropsWithChildren;

const GlobalProvider = ({ children }: GlobalProviderProps) => (
  <ThemeProvider theme={theme}>
    <Global styles={GlobalStyle} />
    {children}
    <ToastProvider />
  </ThemeProvider>
);

export default GlobalProvider;
