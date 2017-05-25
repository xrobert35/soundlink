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

    public static BufferedImage createBufferredJpgImage(String base64) throws IOException {
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(base64));
        BufferedImage bufferedImg = ImageIO.read(in);
        BufferedImage bufferedJpgImage = new BufferedImage(bufferedImg.getWidth(), bufferedImg.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        bufferedJpgImage.createGraphics().drawImage(bufferedImg, 0, 0, Color.WHITE, null);
        return bufferedJpgImage;
    }

    public static BufferedImage createBufferredJpgImage(BufferedImage image) throws IOException {
        BufferedImage bufferedJpgImage = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        bufferedJpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return bufferedJpgImage;
    }

    public static BufferedImage reduceAndCreateJpgBufferedImage(BufferedImage image, int maxWidth, int maxHeight,
            boolean keepRatio) throws IOException {
        BufferedImage imageReduce = Scalr.resize(image, Method.ULTRA_QUALITY,
                keepRatio ? Mode.AUTOMATIC : Mode.FIT_EXACT, maxWidth, maxHeight);
        BufferedImage bufferedJpgImage = new BufferedImage(imageReduce.getWidth(), imageReduce.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        bufferedJpgImage.createGraphics().drawImage(imageReduce, 0, 0, Color.WHITE, null);
        return bufferedJpgImage;
    }

    public static byte[] reduceAndCreateJpgImage(BufferedImage image, int maxWidth, int maxHeight, boolean keepRatio)
            throws IOException {
        image = Scalr.resize(image, Method.ULTRA_QUALITY, keepRatio ? Mode.AUTOMATIC : Mode.FIT_EXACT, maxWidth,
                maxHeight);
        return toByte(image);
    }

    public static byte[] reduceAndCreateBlurrifiedJpgImage(BufferedImage image, int maxWidth, int maxHeight,
            boolean keepRatio) throws IOException {
        image = Scalr.resize(image, Method.ULTRA_QUALITY, keepRatio ? Mode.AUTOMATIC : Mode.FIT_EXACT, maxWidth,
                maxHeight);
        BufferedImage blurrifiedImage = new BufferedImage(maxWidth, maxHeight, image.getType());
        BoxBlurFilter boxBlurFilter = new BoxBlurFilter(5, 1);
        blurrifiedImage = boxBlurFilter.filter(image, blurrifiedImage);
        return toByte(blurrifiedImage);
    }

    public static byte[] createBlurrifiedJpgImage(BufferedImage image) throws IOException {
        BufferedImage blurrifiedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        BoxBlurFilter boxBlurFilter = new BoxBlurFilter(5, 1);
        blurrifiedImage = boxBlurFilter.filter(image, blurrifiedImage);
        return toByte(blurrifiedImage);
    }

    public static byte[] toByte(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }
}
