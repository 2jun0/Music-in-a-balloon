import { Global } from '@emotion/react';
import { StrictMode } from 'react';
import ReactDOM from 'react-dom/client';
import { RecoilRoot } from 'recoil';

import GlobalProvider from '@/GlobalProvider';

import { GlobalStyle } from '@style/GlobalStyle';

import AppRouter from '@router/AppRouter';

const root = ReactDOM.createRoot(document.getElementById('root') as Element);
root.render(
  <StrictMode>
    <RecoilRoot>
      <GlobalProvider>
        <Global styles={GlobalStyle} />
        <AppRouter />
      </GlobalProvider>
    </RecoilRoot>
  </StrictMode>,
);
