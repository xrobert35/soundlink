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
  id SERIAL PRIMARY KEY,
  login VARCHAR(255) UNIQUE,
  password VARCHAR(255),
  email VARCHAR(255),
  role VARCHAR(255),
  picture text
);

COMMENT ON COLUMN users.id is 'User id';
COMMENT ON COLUMN users.login is 'User login';
COMMENT ON COLUMN users.password is 'User password';
COMMENT ON COLUMN users.email is 'User email';
COMMENT ON COLUMN users.role is 'User role';

DROP TABLE IF EXISTS artiste;

CREATE TABLE artiste (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) UNIQUE,
  cover text
);

CREATE INDEX index_artiste_name ON artiste (name);

COMMENT ON COLUMN artiste.id is 'Artiste id';
COMMENT ON COLUMN artiste.name is 'Artiste name';
COMMENT ON COLUMN artiste.cover is 'Artiste cover image';

-- ALBUM TABLE
CREATE TABLE album (
  id SERIAL PRIMARY KEY,
  artiste_id SERIAL,
  name VARCHAR(255),
  bit_rate VARCHAR(255),
  extension VARCHAR(255),
  cover_general_color VARCHAR(255),
  cover text,
  album_directory VARCHAR(1000),
  FOREIGN KEY (artiste_id) REFERENCES artiste (id)
);

CREATE INDEX index_album_name_artiste ON album (name, artiste_id);

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
  id SERIAL PRIMARY KEY,
  album_id SERIAL,
  title VARCHAR(255),
  track_number INT,
  disc_number INT,
  duration_in_seconde INT,
  bit_rate VARCHAR(255),
  extension VARCHAR(255),
  music_file_path VARCHAR(1000),
  music_file_size BIGINT,
  FOREIGN KEY (album_id) REFERENCES album (id)
);

CREATE INDEX index_music_title_album ON music (title, album_id);

COMMENT ON COLUMN music.id is 'Music id';
COMMENT ON COLUMN music.album_id is 'Music album';
COMMENT ON COLUMN music.title is 'Music title';
COMMENT ON COLUMN music.track_number is 'Music track number';
COMMENT ON COLUMN music.duration_in_seconde is 'Music duration in seconde';
COMMENT ON COLUMN music.bit_rate is 'Music bit rate : bit/s';
COMMENT ON COLUMN music.extension is 'Music extension : flac, mp3';
COMMENT ON COLUMN music.music_file_path is 'Music file path';

-- PLAYLIST TABLE
CREATE TABLE playlist(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255)
);

COMMENT ON COLUMN playlist.id is 'Playlist id';
COMMENT ON COLUMN playlist.name is 'Playlist name';

-- USERS FAVORITE ARTISTE TABLE
CREATE TABLE music_playlists (
	playlist_id SERIAL,
	music_id SERIAL,
	UNIQUE(playlist_id, music_id),
	FOREIGN KEY (playlist_id) REFERENCES playlist (id),
	FOREIGN KEY (music_id) REFERENCES music (id)
);

-- USERS FAVORITE ARTISTE TABLE
CREATE TABLE user_fav_artistes (
	user_id SERIAL,
	artiste_id SERIAL,
	UNIQUE(user_id, artiste_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (artiste_id) REFERENCES artiste (id)
);

-- USERS FAVORITE ALBUM TABLE
CREATE TABLE user_fav_albums (
	user_id SERIAL,
	album_id SERIAL,
	UNIQUE(user_id, album_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (album_id) REFERENCES album (id)
);

-- USERS PLAYLIST TABLE
CREATE TABLE user_fav_playlists (
	user_id SERIAL,
	playlist_id SERIAL,
	UNIQUE(user_id, playlist_id),
	FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (playlist_id) REFERENCES playlist (id)
);

DROP SEQUENCE IF EXISTS users_id_seq;
DROP SEQUENCE IF EXISTS music_id_seq;
DROP SEQUENCE IF EXISTS album_id_seq;
DROP SEQUENCE IF EXISTS artiste_id_seq;
DROP SEQUENCE IF EXISTS playlist_id_seq;

CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE music_id_seq;
CREATE SEQUENCE album_id_seq;
CREATE SEQUENCE artiste_id_seq;
CREATE SEQUENCE playlist_id_seq;