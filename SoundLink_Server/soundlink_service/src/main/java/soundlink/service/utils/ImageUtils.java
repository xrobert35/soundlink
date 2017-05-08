package soundlink.service.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

public final class ImageUtils {

    public static byte[] reduceAndCreateJpgImage(String base64, int maxWidth, int maxHeight, boolean keepRatio)
            throws IOException {
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
        BufferedImage bufferedImage = ImageIO.read(in);
        return reduceAndCreateJpgImage(bufferedImage, maxWidth, maxHeight, keepRatio);
    }

    public static byte[] reduceAndCreateJpgImage(BufferedImage image, int maxWidth, int maxHeight, boolean keepRatio)
            throws IOException {
        image.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        image = Scalr.resize(image, Method.ULTRA_QUALITY, keepRatio ? Mode.AUTOMATIC : Mode.FIT_EXACT, maxWidth,
                maxHeight);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }
}
