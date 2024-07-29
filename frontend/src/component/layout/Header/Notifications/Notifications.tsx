import NotificationsIcon from '@mui/icons-material/Notifications';
import {
  Avatar,
  Badge,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Popover,
} from '@mui/material';
import { grey } from '@mui/material/colors';
import { useEffect, useState } from 'react';
import { useSetRecoilState } from 'recoil';

import { subscribeReactionNotification } from '@/api/notification/subscribeReactionNotification';
import { selectedBalloonIdState } from '@/store/balloon';
import type { ReactionNotificationData } from '@/type/notification';
import { createBalloonIconImage } from '@/util/balloon';

const Notifications = () => {
  const [anchorEl, setAnchorEl] = useState<HTMLButtonElement | null>(null);
  const setSelectedBalloonId = useSetRecoilState(selectedBalloonIdState);
  const [reactionNofitications, setReactionNofitications] = useState<ReactionNotificationData[]>(
    [],
  );

  const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const open = !!anchorEl;
  const id = open ? 'simple-popover' : undefined;

  useEffect(() => {
    const reactionEventSource = subscribeReactionNotification();

    reactionEventSource.addEventListener('Reaction-Notification', (event: MessageEvent<string>) => {
      const reactionNotification = JSON.parse(event.data) as ReactionNotificationData;
      setReactionNofitications((prev) => [reactionNotification, ...prev]);
    });

    return () => {
      reactionEventSource.close();
    };
  }, []);

  return (
    <>
      <IconButton aria-describedby={id} aria-label="Notification" onClick={handleClick}>
        <Badge badgeContent={reactionNofitications.length} color="primary">
          <NotificationsIcon />
        </Badge>
      </IconButton>
      <Popover
        id={id}
        open={open}
        anchorEl={anchorEl}
        onClose={handleClose}
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'right',
        }}
      >
        <List sx={{ width: '100%', maxWidth: 360, maxHeight: 250, bgcolor: 'background.paper' }}>
          {reactionNofitications.map((notification) => (
            <ListItem>
              <ListItemAvatar>
                <Badge
                  overlap="circular"
                  anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
                  badgeContent={
                    <img
                      src={createBalloonIconImage(notification.balloon.colorCode)}
                      alt="balloon"
                    />
                  }
                  sx={{ cursor: 'pointer' }}
                  onClick={() => {
                    setSelectedBalloonId(notification.balloon.id);
                  }}
                >
                  <Avatar sx={{ bgcolor: grey[300] }}>👍</Avatar>
                </Badge>
              </ListItemAvatar>
              <ListItemText
                primary={`${notification.responder.name} gave a 👍`}
                secondary={`to ${notification.balloon.title} balloon`}
              />
            </ListItem>
          ))}
        </List>
      </Popover>
    </>
  );
};

export default Notifications;
