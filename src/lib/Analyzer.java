package lib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Color;
import javax.imageio.ImageIO;

public class Analyzer {
  public static void run(BufferedImage image, int imageSensitivity, Color imageBaseColor, Color highlightColor)
      throws Exception {
    var imageAnalyzer = new ImageAnalyzer(imageSensitivity, imageBaseColor, highlightColor);

    var highlightedImage = imageAnalyzer.highlightBaseColor(image);
    var percentageOfBaseColor = imageAnalyzer.getPercentageOfBaseColor(image);

    System.out.printf("Percentage of selected color: %.2f%s\n", percentageOfBaseColor, "%");

    var outputFile = new File("images/analyzed_image.jpg");
    ImageIO.write(highlightedImage, "jpg", outputFile);
  }
}
