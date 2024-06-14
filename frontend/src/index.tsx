import { Global } from '@emotion/react';
import React, { StrictMode } from 'react';
import ReactDOM from 'react-dom/client';

import GlobalProvider from '@/GlobalProvider';

import { GlobalStyle } from '@style/GlobalStyle';

import AppRouter from '@router/AppRouter';

const root = ReactDOM.createRoot(document.getElementById('root') as Element);
root.render(
  <StrictMode>
    <GlobalProvider>
      <Global styles={GlobalStyle} />
      <AppRouter />
    </GlobalProvider>
  </StrictMode>,
);
