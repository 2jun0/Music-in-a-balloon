import { lazy } from 'react';

export const MapPage = lazy(
  () => import(/* webpackChunkName: "MapPage" */ '@page/MapPage/MapPage'),
);
