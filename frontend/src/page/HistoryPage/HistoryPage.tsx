import { useCallback, useEffect, useState } from 'react';
import { useSetRecoilState } from 'recoil';

import { getBalloonPicked } from '@/api/balloon/getBalloonPicked';
import Box from '@/component/Box/Box';
import Flex from '@/component/Flex/Flex';
import Heading from '@/component/Heading/Heading';
import Text from '@/component/Text/Text';
import BalloonInfoModal from '@/component/balloon/BalloonInfoModal/BalloonInfoModal';
import MenuItem from '@/component/common/MenuItem/MenuItem';
import MenuList from '@/component/common/MenuList/MenuList';
import { useBalloonPickedQuery } from '@/hook/api/useBalloonPickedQuery';
import { useOverlay } from '@/hook/common/useOverlay';
import { selectedBalloonIdState } from '@/store/balloon';
import type { BalloonListItemData } from '@/type/balloonList';
import { createBalloonIconImage } from '@/util/balloon';

import {
  containerStyling,
  detailStyling,
  headingStyling,
  historyItemStyling,
  historyItemsConatinerStyling,
} from './HistoryPage.style';

const HistoryPage = () => {
  const { isOpen: isInfoModalOpen, open: openInfoModal, close: closeInfoModal } = useOverlay();
  const setSelectedBalloonId = useSetRecoilState(selectedBalloonIdState);
  const [balloons, setBalloons] = useState<BalloonListItemData[]>([]);
  const [lastPage, setLastPage] = useState<number>(0);

  useEffect(() => {
    setSelectedBalloonId(0);
  }, []);

  const openBalloon = (balloonId: number) => {
    setSelectedBalloonId(balloonId);
    openInfoModal();
  };

  const onScrollToBottom = () => {
    setLastPage((lastPage) => lastPage + 1);
  };

  const loadMoreBalloons = async () => {
    const balloonPicked = await getBalloonPicked(lastPage);
    setBalloons([...balloons, ...balloonPicked.balloons]);
  };

  useEffect(() => {
    loadMoreBalloons();
  }, [lastPage]);

  return (
    <Flex css={containerStyling}>
      <Heading size="small" css={headingStyling}>
        Balloon Picked History
      </Heading>
      <MenuList css={historyItemsConatinerStyling} onScrollToBottom={onScrollToBottom}>
        {balloons.map((balloon) => (
          <MenuItem key={balloon.id} onClick={() => openBalloon(balloon.id)}>
            <Flex css={historyItemStyling}>
              <img src={createBalloonIconImage(balloon.colorCode)} alt="balloon" />
              <Flex css={detailStyling}>
                <Text>
                  <b>Title</b>: {balloon.title}
                </Text>
              </Flex>
            </Flex>
          </MenuItem>
        ))}
      </MenuList>
      {isInfoModalOpen && <BalloonInfoModal onClose={closeInfoModal} />}
    </Flex>
  );
};

export default HistoryPage;
