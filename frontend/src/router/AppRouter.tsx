import { PATH } from '@constant/path';
import NotFoundPage from '@page/NotFoundPage/NotFoundPage';
import { Suspense } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import App from '@/App';

import * as Lazy from '@router/lazy';

import { mediaQueryMobileState } from '@store/mediaQuery';

const AppRouter = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const router = createBrowserRouter([
    {
      path: PATH.ROOT,
      element: <App />,
      errorElement: <NotFoundPage />,
      children: [
        {
          path: PATH.MAP,
          element: <Lazy.MapPage />,
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;
