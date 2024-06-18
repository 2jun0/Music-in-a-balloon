import { useResetError } from '@hook/common/useResetError';
import { Outlet } from 'react-router-dom';

import Error from '@component/common/Error/Error';
import ErrorBoundary from '@component/common/ErrorBoundary/ErrorBoundary';
import Register from '@component/common/Register/Register';

const App = () => {
  const { handleErrorReset } = useResetError();

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <Register>
        <main>
          <Outlet />
        </main>
      </Register>
    </ErrorBoundary>
  );
};

export default App;
