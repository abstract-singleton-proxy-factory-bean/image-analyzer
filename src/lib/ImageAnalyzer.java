package lib;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import utils.CompareColor;

public class ImageAnalyzer {
  private int colorSensitivity;
  private Color imageBaseColor;
  private Color highlightColor;

  public ImageAnalyzer(int colorSensitivity, Color imageBaseColor, Color highlightColor) {
    this.colorSensitivity = colorSensitivity;
    this.imageBaseColor = imageBaseColor;
    this.highlightColor = highlightColor;
  }

  public BufferedImage highlightBaseColor(BufferedImage sourceImage) {
    var image = cloneBufferedImage(sourceImage);

    for (var y = 0; y < image.getHeight(); y++) {
      for (var x = 0; x < image.getWidth(); x++) {
        var pixel = image.getRGB(x, y);
        var color = new Color(pixel, true);

        if (CompareColor.compareRange(color, this.imageBaseColor, this.colorSensitivity)) {
          image.setRGB(x, y, this.highlightColor.getRGB());
        }
      }
    }

    return image;
  }

  public double getPercentageOfBaseColor(BufferedImage sourceImage) {
    var totalBaseColorPixels = 0;

    for (var y = 0; y < sourceImage.getHeight(); y++) {
      for (var x = 0; x < sourceImage.getWidth(); x++) {
        var pixel = sourceImage.getRGB(x, y);
        var color = new Color(pixel, true);

        if (CompareColor.compareRange(color, this.imageBaseColor, this.colorSensitivity)) {
          totalBaseColorPixels++;
        }
      }
    }

    var totalImagePixels = sourceImage.getHeight() * sourceImage.getWidth();
    var percentage = ((double) totalBaseColorPixels / totalImagePixels) * 100;

    return percentage;
  }

  private static BufferedImage cloneBufferedImage(BufferedImage image) {
    ColorModel colorModel = image.getColorModel();
    boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
    WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());
    return new BufferedImage(colorModel, raster, isAlphaPremultiplied, null);
  }

  public static BufferedImage readImageFile(String imageFilePath) throws IOException {
    var imageFile = new File(imageFilePath);
    var image = ImageIO.read(imageFile);

    return image;
  }
}
