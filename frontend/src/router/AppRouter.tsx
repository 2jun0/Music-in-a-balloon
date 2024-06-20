import { PATH } from '@constant/path';
import NotFoundPage from '@page/NotFoundPage/NotFoundPage';
import { Suspense } from 'react';
import { Navigate, RouterProvider, createBrowserRouter } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import App from '@/App';
import RegisterRequired from '@/component/common/RegisterRequired/RegisterRequired';

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
          path: '',
          element: <Navigate to={PATH.MAP} replace />,
        },
        {
          path: PATH.MAP,
          element: (
            <Suspense>
              <RegisterRequired>
                <Lazy.MapPage />
              </RegisterRequired>
            </Suspense>
          ),
        },
        {
          path: PATH.REGISTER,
          element: (
            <Suspense>
              <Lazy.RegisterPage />
            </Suspense>
          ),
        },
      ],
    },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;
