import { useResetError } from '@hook/common/useResetError';
import { Outlet } from 'react-router-dom';

import Error from '@component/common/Error/Error';
import ErrorBoundary from '@component/common/ErrorBoundary';

import AppRouter from './router/AppRouter';

const App = () => {
  const { handleErrorReset } = useResetError();

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <main>
        <Outlet />
      </main>
    </ErrorBoundary>
  );
};

export default App;
