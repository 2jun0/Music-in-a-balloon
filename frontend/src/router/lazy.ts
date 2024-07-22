import { lazy } from 'react';

export const MapPage = lazy(
  () => import(/* webpackChunkName: "MapPage" */ '@page/MapPage/MapPage'),
);

export const RegisterPage = lazy(
  () => import(/* webpackChunkName: "RegisterPage" */ '@page/RegisterPage/RegisterPage'),
);

export const HistoryPage = lazy(
  () => import(/* webpackChunkName: "HistoryPage" */ '@page/HistoryPage/HistoryPage'),
);
