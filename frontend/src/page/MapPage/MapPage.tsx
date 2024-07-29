import { useMapPageQueries } from '@hook/api/useMapPageQueries';
import useGeolocation from '@hook/common/useGeolocation';
import { useOverlay } from '@hook/common/useOverlay';
import AddIcon from '@mui/icons-material/Add';
import { Fab } from '@mui/material';
import {
  addButtonStyling,
  containerStyling,
  mapContainerStyling,
} from '@page/MapPage/MapPage.style';
import { useEffect } from 'react';
import { useRecoilValue, useSetRecoilState } from 'recoil';

import Box from '@/component/Box/Box';

import Flex from '@component/Flex/Flex';
import BalloonCreateModal from '@component/balloon/BalloonCreateModal/BalloonCreateModal';
import BalloonInfoModal from '@component/balloon/BalloonInfoModal/BalloonInfoModal';
import BalloonMap from '@component/common/BalloonMap/BalloonMap';
import LeafletWrapper from '@component/common/LeafletWrapper/LeafletWrapper';

import { selectedBalloonIdState } from '@store/balloon';

const MapPage = () => {
  const {
    balloonListData: { balloons },
    waveData,
  } = useMapPageQueries();
  const { coordinates } = useGeolocation();
  const { isOpen: isAddModalOpen, open: openAddModal, close: closeAddModal } = useOverlay();
  const { isOpen: isInfoModalOpen, open: openInfoModal, close: closeInfoModal } = useOverlay();
  const selectedBalloonId = useRecoilValue(selectedBalloonIdState);
  const setSelectedBalloonId = useSetRecoilState(selectedBalloonIdState);

  useEffect(() => {
    setSelectedBalloonId(0);
  }, []);

  useEffect(() => {
    if (selectedBalloonId !== 0) openInfoModal();
  }, [openInfoModal, selectedBalloonId]);

  const closeInfoModalProxy = () => {
    setSelectedBalloonId(0);
    closeInfoModal();
  };

  return (
    <Flex css={containerStyling}>
      <Box css={mapContainerStyling}>
        <LeafletWrapper isReady={!!coordinates.lat && !!coordinates.lon}>
          <BalloonMap
            centerLat={Number(coordinates.lat)}
            centerLon={Number(coordinates.lon)}
            balloons={balloons}
            wave={waveData}
          />
        </LeafletWrapper>
        {isAddModalOpen && <BalloonCreateModal onClose={closeAddModal} />}
        {isInfoModalOpen && <BalloonInfoModal onClose={closeInfoModalProxy} />}
      </Box>
      <Fab
        color="primary"
        css={addButtonStyling}
        aria-label="Fly a music balloon"
        onClick={openAddModal}
      >
        <AddIcon />
      </Fab>
    </Flex>
  );
};

export default MapPage;
