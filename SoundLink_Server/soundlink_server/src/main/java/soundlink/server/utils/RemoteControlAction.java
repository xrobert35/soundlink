package soundlink.server.utils;

public enum RemoteControlAction {

    // Action used to be registered a listener
    REGISTER_LISTENER,
    // Action used to be deregistrered as a listener
    DEREGISTER_LISTENER,
    // Play music
    PLAY,
    // Pause music,
    PAUSE,
    // Add song in playlist,
    ADD_SONG,
    // Set song to play,
    SET_SONG,
    // Next song
    NEXT_SONG,
    // Previous song,
    PREVIOUS_SONG,
    // Set the volume
    SET_VOLUME
}
