import { useResetError } from '@hook/common/useResetError';
import { Outlet } from 'react-router-dom';

import ToastContainer from '@component/ToastContainer/ToastContainer';
import Error from '@component/common/Error/Error';
import ErrorBoundary from '@component/common/ErrorBoundary/ErrorBoundary';
import Geolocation from '@component/common/Geolocation/Geolocation';
import Register from '@component/common/Register/Register';
import Footer from '@component/layout/Footer/Footer';
import Header from '@component/layout/Header/Header';

const App = () => {
  const { handleErrorReset } = useResetError();

  return (
    <ErrorBoundary Fallback={Error} onReset={handleErrorReset}>
      <Geolocation>
        <Register>
          <Header />
          <main>
            <Outlet />
          </main>
          <Footer />
        </Register>
      </Geolocation>
      <ToastContainer />
    </ErrorBoundary>
  );
};

export default App;
