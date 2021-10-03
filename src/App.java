import java.awt.Color;

import lib.Analyzer;
import lib.ImageAnalyzer;

public class App {
  public static void main(String[] args) throws Exception {
    var image = ImageAnalyzer.readImageFile("images/analyze.jpg");
    var imageSensitivity = 20;
    var highlightColor = new Color(255, 0, 0);
    var imageBaseColor = Color.decode("#233918");

    Analyzer.run(image, imageSensitivity, imageBaseColor, highlightColor);
  }
}
