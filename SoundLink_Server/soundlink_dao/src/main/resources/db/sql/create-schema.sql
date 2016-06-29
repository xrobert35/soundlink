DROP TABLE IF EXISTS music_playlists;
DROP TABLE IF EXISTS user_fav_artistes;
DROP TABLE IF EXISTS user_fav_albums;
DROP TABLE IF EXISTS user_fav_playlists;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS music;
DROP TABLE IF EXISTS album;
DROP TABLE IF EXISTS artiste;
DROP TABLE IF EXISTS playlist;

-- USERS TABLE
CREATE TABLE users (
  id BIGINT PRIMARY KEY,
  login VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  email VARCHAR(255),
  role VARCHAR(255)
);

COMMENT ON COLUMN users.id is 'User id';
COMMENT ON COLUMN users.login is 'User login';
COMMENT ON COLUMN users.password is 'User password';
COMMENT ON COLUMN users.email is 'User email';
COMMENT ON COLUMN users.role is 'User role';

DROP TABLE IF EXISTS artiste;

CREATE TABLE artiste (
  id BIGINT PRIMARY KEY,
  name VARCHAR(255) UNIQUE,
  cover bytea
);

COMMENT ON COLUMN artiste.id is 'Artiste id';
COMMENT ON COLUMN artiste.name is 'Artiste name';
COMMENT ON COLUMN artiste.cover is 'Artiste cover image';

-- ALBUM TABLE
CREATE TABLE album (
  id BIGINT PRIMARY KEY,
  artiste_id BIGINT,
  name VARCHAR(255),
  bit_rate VARCHAR(255),
  extension VARCHAR(255),
  cover_general_color VARCHAR(255),
  cover bytea,
  album_directory VARCHAR(1000),
  FOREIGN KEY (artiste_id) REFERENCES artiste (id)
);

COMMENT ON COLUMN album.id is 'Album id';
COMMENT ON COLUMN album.artiste_id is 'Album artiste';
COMMENT ON COLUMN album.name is 'Album name';
COMMENT ON COLUMN album.bit_rate is 'Album bit rate : bit/s';
COMMENT ON COLUMN album.extension is 'Album extension : flac, mp3';
COMMENT ON COLUMN album.cover_general_color is 'Cover image color : #6e6e6e';
COMMENT ON COLUMN album.cover is 'Cover image';
COMMENT ON COLUMN album.album_directory is 'Album directory path';

DROP TABLE IF EXISTS music;

-- MUSIC TABLE
CREATE TABLE music (
  id BIGINT PRIMARY KEY,
  album_id BIGINT,
  name VARCHAR(255),
  track_number INT,
  duration_in_seconde INT,
  bit_rate VARCHAR(255),
  extension VARCHAR(255),
  music_file_path VARCHAR(1000),
  FOREIGN KEY (album_id) REFERENCES album (id)
);

COMMENT ON COLUMN music.id is 'Music id';
COMMENT ON COLUMN music.album_id is 'Music album';
COMMENT ON COLUMN music.name is 'Music name';
COMMENT ON COLUMN music.track_number is 'Music track number';
COMMENT ON COLUMN music.duration_in_seconde is 'Music duration in seconde';
COMMENT ON COLUMN music.bit_rate is 'Music bit rate : bit/s';
COMMENT ON COLUMN music.extension is 'Music extension : flac, mp3';
COMMENT ON COLUMN music.music_file_path is 'Music file path';

-- PLAYLIST TABLE
CREATE TABLE playlist(
	id BIGINT PRIMARY KEY,
	name VARCHAR(255)
);

COMMENT ON COLUMN playlist.id is 'Playlist id';
COMMENT ON COLUMN playlist.name is 'Playlist name';

-- USERS FAVORITE ARTISTE TABLE
CREATE TABLE music_playlists (
	playlist_id BIGINT,
	music_id BIGINT,
	UNIQUE(playlist_id, music_id),
	FOREIGN KEY (playlist_id) REFERENCES playlist (id),
	FOREIGN KEY (music_id) REFERENCES music (id)
);

-- USERS FAVORITE ARTISTE TABLE
CREATE TABLE user_fav_artistes (
	user_id BIGINT,
	artiste_id BIGINT,
	UNIQUE(user_id, artiste_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (artiste_id) REFERENCES artiste (id)
);

-- USERS FAVORITE ALBUM TABLE
CREATE TABLE user_fav_albums (
	user_id BIGINT,
	album_id BIGINT,
	UNIQUE(user_id, album_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (album_id) REFERENCES album (id)
);

-- USERS PLAYLIST TABLE
CREATE TABLE user_fav_playlists (
	user_id BIGINT,
	playlist_id BIGINT,
	UNIQUE(user_id, playlist_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (playlist_id) REFERENCES playlist (id)
);