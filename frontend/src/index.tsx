import { Global } from '@emotion/react';
import { queryClient } from '@hook/api/queryClient';
import { QueryClientProvider } from '@tanstack/react-query';
import { StrictMode } from 'react';
import ReactDOM from 'react-dom/client';
import { RecoilRoot } from 'recoil';

import GlobalProvider from '@/GlobalProvider';

import { GlobalStyle } from '@style/GlobalStyle';

import { worker } from '@mock/browser';

import AppRouter from '@router/AppRouter';

const main = async () => {
  if (process.env.NODE_ENV === 'development') {
    await worker.start({
      serviceWorker: {
        url: '/mockServiceWorker.js',
      },
      onUnhandledRequest: 'bypass',
    });
  }

  const root = ReactDOM.createRoot(document.getElementById('root') as Element);

  root.render(
    <StrictMode>
      <QueryClientProvider client={queryClient}>
        <RecoilRoot>
          <GlobalProvider>
            <Global styles={GlobalStyle} />
            <AppRouter />
          </GlobalProvider>
        </RecoilRoot>
      </QueryClientProvider>
    </StrictMode>,
  );
};

main();
