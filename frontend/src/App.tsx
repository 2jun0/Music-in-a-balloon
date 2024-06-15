import { useResetError } from '@hook/common/useResetError';
import { Outlet } from 'react-router-dom';

import ErrorBoundary from '@/component/common/ErrorBoundary/ErrorBoundary';

import Error from '@component/common/Error/Error';

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
