import { PATH } from '@constant/path';
import NotFoundPage from '@page/NotFoundPage/NotFoundPage';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import App from '@/App';

import { mediaQueryMobileState } from '@store/mediaQuery';

const AppRouter = () => {
  const isMobile = useRecoilValue(mediaQueryMobileState);

  const router = createBrowserRouter([
    {
      path: PATH.ROOT,
      element: <App />,
      errorElement: <NotFoundPage />,
    },
  ]);

  return <RouterProvider router={router} />;
};

export default AppRouter;
