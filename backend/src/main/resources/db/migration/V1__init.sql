create TABLE IF NOT EXISTS
    users (
        created_at TIMESTAMP(6) NOT NULL,
        id BIGINT NOT NULL AUTO_INCREMENT,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        name VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS
    wave (
        amplitude FLOAT(53) NOT NULL,
        offset_longitude FLOAT(53) NOT NULL,
        period FLOAT(53) NOT NULL,
        velocity FLOAT(53) NOT NULL,
        created_at TIMESTAMP(6) NOT NULL,
        id BIGINT NOT NULL AUTO_INCREMENT,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS
    spotify_music (
        created_at TIMESTAMP(6) NOT NULL,
        id BIGINT NOT NULL AUTO_INCREMENT,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        album_image_url VARCHAR(255),
        spotify_id VARCHAR(255) NOT NULL UNIQUE,
        title VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS
    youtube_music (
        created_at TIMESTAMP(6) NOT NULL,
        id BIGINT NOT NULL AUTO_INCREMENT,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        thumbnail_url VARCHAR(255),
        title VARCHAR(255) NOT NULL,
        youtube_id VARCHAR(255) NOT NULL UNIQUE,
        PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS
    balloon (
        id BIGINT NOT NULL AUTO_INCREMENT,
        base_latitude DECIMAL(16, 13) NOT NULL,
        base_longitude DECIMAL(16, 13) NOT NULL,
        based_at TIMESTAMP(6) NOT NULL,
        created_at TIMESTAMP(6) NOT NULL,
        creator_id BIGINT NOT NULL,
        spotify_music_id BIGINT,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        youtube_music_id BIGINT,
        color_code VARCHAR(255) NOT NULL,
        message VARCHAR(255) NOT NULL,
        uploaded_streaming_music_type ENUM('YOUTUBE_MUSIC', 'SPOTIFY_MUSIC'),
        PRIMARY KEY (id),
        FOREIGN KEY (creator_id) REFERENCES users (id),
        FOREIGN KEY (spotify_music_id) REFERENCES spotify_music (id),
        FOREIGN KEY (youtube_music_id) REFERENCES youtube_music (id)
    );

create TABLE IF NOT EXISTS
    balloon_picked (
        id BIGINT NOT NULL AUTO_INCREMENT,
        balloon_id BIGINT NOT NULL,
        created_at TIMESTAMP(6) NOT NULL,
        picker_id BIGINT NOT NULL,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (balloon_id) REFERENCES balloon (id),
        FOREIGN KEY (picker_id) REFERENCES users (id)
    );

create TABLE IF NOT EXISTS
    balloon_reaction (
        id BIGINT NOT NULL AUTO_INCREMENT,
        balloon_id BIGINT NOT NULL,
        created_at TIMESTAMP(6) NOT NULL,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        user_id BIGINT NOT NULL,
        type ENUM(
            'THUMBS_UP',
            'THUMBS_DOWN',
            'BALLOON',
            'SMILE',
            'LOVE',
            'EYES'
        ) NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT index__balloon_id__user_id UNIQUE (balloon_id, user_id),
        FOREIGN KEY (balloon_id) REFERENCES balloon (id),
        FOREIGN KEY (user_id) REFERENCES users (id)
    );

create TABLE IF NOT EXISTS
    notify (
        balloon_reaction BIGINT NOT NULL,
        created_at TIMESTAMP(6) NOT NULL,
        id BIGINT NOT NULL AUTO_INCREMENT,
        receiver_id BIGINT NOT NULL,
        test_at TIMESTAMP(6) NOT NULL,
        updated_at TIMESTAMP(6) NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (balloon_reaction) REFERENCES balloon_reaction (id) ON delete CASCADE,
        FOREIGN KEY (receiver_id) REFERENCES users (id) ON delete CASCADE
    );