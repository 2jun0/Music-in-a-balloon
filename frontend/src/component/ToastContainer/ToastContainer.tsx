import { useCallback } from 'react';
import { useRecoilState } from 'recoil';

import Toast from '@component/Toast/Toast';

import { toastListState } from '@store/toast';

const ToastContainer = () => {
  const [toastList, setToastList] = useRecoilState(toastListState);

  const removeToast = useCallback(
    (id: number) => () => {
      setToastList((prevToastList) => prevToastList.filter((toast) => toast.id !== id));
    },
    [setToastList],
  );

  return toastList.length > 0 ? (
    <>
      {toastList.map(({ id, message, ...attributes }) => (
        <Toast key={id} onClose={removeToast(id)} {...attributes}>
          {message}
        </Toast>
      ))}
    </>
  ) : (
    // eslint-disable-next-line react/jsx-no-useless-fragment
    <></>
  );
};

export default ToastContainer;
