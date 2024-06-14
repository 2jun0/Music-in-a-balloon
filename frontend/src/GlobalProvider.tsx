import { Global, ThemeProvider } from '@emotion/react';
import type { PropsWithChildren } from 'react';

import ToastContainer from '@component/ToastContainer/ToastContainer';

import { GlobalStyle } from '@style/GlobalStyle';
import { Theme } from '@style/Theme';

type GlobalProviderProps = PropsWithChildren;

const GlobalProvider = ({ children }: GlobalProviderProps) => (
  <ThemeProvider theme={Theme}>
    <Global styles={GlobalStyle} />
    {children}
    <ToastContainer />
  </ThemeProvider>
);

export default GlobalProvider;
