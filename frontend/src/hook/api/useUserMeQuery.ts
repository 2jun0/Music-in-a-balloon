import { useQuery } from '@tanstack/react-query';
import type { AxiosError } from 'axios';

import type { UserData } from '@type/user';

import { getMe } from '@api/user/getMe';

export const useUserMeQuery = () => {
  const { data, error } = useQuery<UserData, AxiosError>({
    queryKey: ['userMe'],
    queryFn: getMe,
  });

  return error ? null : data;
};
