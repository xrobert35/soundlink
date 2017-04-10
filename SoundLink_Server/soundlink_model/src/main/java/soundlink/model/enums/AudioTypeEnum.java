package soundlink.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum AudioTypeEnum {

    FLAC(".flac"), MP3(".mp3"), WAVE(".wav"), M4A(".m4a"), OGG(".ogg"), WMA(".wma");

    private String code;

    private AudioTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static List<String> getAudioExtensions() {
        List<String> extensions = new ArrayList<String>();
        AudioTypeEnum[] values = AudioTypeEnum.values();
        for (AudioTypeEnum audioType : values) {
            extensions.add(audioType.getCode());
        }
        return extensions;
    }

    public static AudioTypeEnum getAudioTypeFromCode(String code) {
        AudioTypeEnum[] values = AudioTypeEnum.values();
        for (AudioTypeEnum audioType : values) {
            if (audioType.getCode().equals(code)) {
                return audioType;
            }
        }

        return null;
    }
}
