import { AXIOS_BASE_URL, NETWORK } from '@constant/api';
import axios from 'axios';

import { handleAPIError } from '@/api/interceptor';

export const axiosInstance = axios.create({
  baseURL: AXIOS_BASE_URL,
  timeout: NETWORK.TIMEOUT,
  withCredentials: true,
});

axiosInstance.interceptors.response.use((response) => response, handleAPIError);
