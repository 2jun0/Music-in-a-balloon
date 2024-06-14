import { useNavigate } from 'react-router-dom';

import { PATH } from '@/constant/path';

import Error from '@component/common/Error/Error';

const NotFoundPage = () => {
  const navigate = useNavigate();

  return <Error resetError={() => navigate(PATH.ROOT)} />;
};

export default NotFoundPage;
