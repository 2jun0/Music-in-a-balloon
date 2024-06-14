import { PATH } from '@constant/path';
import NotFoundPage from '@page/NotFoundPage/NotFoundPage';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

import App from '@/App';

const AppRouter = () => {
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
