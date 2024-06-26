import { useMapPageQueries } from '@hook/api/useMapPageQueries';
import useGeolocation from '@hook/common/useGeolocation';
import { useOverlay } from '@hook/common/useOverlay';
import {
  addButtonStyling,
  containerStyling,
  mapContainerStyling,
} from '@page/MapPage/MapPage.style';
import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import Flex from '@component/Flex/Flex';
import FloatingButton from '@component/FloatingButton/FloatingButton';
import BalloonCreateModal from '@component/balloon/BalloonCreateModal/BalloonCreateModal';
import BalloonInfoModal from '@component/balloon/BalloonInfoModal/BalloonInfoModal';
import BalloonMap from '@component/common/BalloonMap/BalloonMap';
import LeafletWrapper from '@component/common/LeafletWrapper/LeafletWrapper';

import { pickedBalloonIdState } from '@store/balloon';

const MapPage = () => {
  const {
    balloonListData: { balloons },
    waveData,
  } = useMapPageQueries();
  const { coordinates } = useGeolocation();
  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();
  const { isOpen: isInfoModalOpen, open: openInfoModal, close: closeInfoModal } = useOverlay();
  const pickedBalloonId = useRecoilValue(pickedBalloonIdState);
  const setPickedBalloonId = useSetRecoilState(pickedBalloonIdState);

  useEffect(() => {
    if (pickedBalloonId !== 0) openInfoModal();
  }, [openInfoModal, pickedBalloonId]);

  const closeInfoModalProxy = () => {
    setPickedBalloonId(0);
    closeInfoModal();
  };

  return (
    <Flex css={containerStyling}>
      <section css={mapContainerStyling}>
        <LeafletWrapper isReady={!!coordinates.lat && !!coordinates.lon}>
          <BalloonMap
            centerLat={Number(coordinates.lat)}
            centerLon={Number(coordinates.lon)}
            balloons={balloons}
            wave={waveData}
          />
        </LeafletWrapper>
        <FloatingButton
          css={addButtonStyling}
          aria-label="Fly a music balloon"
          onClick={openAddModal}
        />
        {isAddModalOpen && <BalloonCreateModal onClose={closeAddModal} />}
        {isInfoModalOpen && <BalloonInfoModal onClose={closeInfoModalProxy} />}
      </section>
    </Flex>
  );
};

export default MapPage;
