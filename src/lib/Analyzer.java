package lib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import javax.imageio.ImageIO;

public class Analyzer {
  public static BufferedImage run(BufferedImage image, int imageSensitivity, Color imageBaseColor,
      Color highlightColor) {
    var imageAnalyzer = new ImageAnalyzer(imageSensitivity, imageBaseColor, highlightColor);
    var highlightedImage = imageAnalyzer.highlightBaseColor(image);

    return highlightedImage;
  }

  public static void saveImage(BufferedImage image, String filename) throws Exception {
    var outputFile = new File(filename);
    ImageIO.write(image, "jpg", outputFile);
  }
}
