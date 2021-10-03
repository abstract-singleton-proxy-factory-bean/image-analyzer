package utils;

import java.awt.Color;

public class CompareColor {
  private static int[] getRGB(Color color) {
    return new int[] { color.getRed(), color.getGreen(), color.getBlue() };
  }

  private static int getDifference(int source, int target) {
    return Math.abs(target - source);
  }

  public static boolean compareRange(Color source, Color target, int sensitivity) {
    var sourceColors = getRGB(source);
    var targetColors = getRGB(target);

    var redDifference = getDifference(sourceColors[0], targetColors[0]);
    var greenDifference = getDifference(sourceColors[1], targetColors[1]);
    var blueDifference = getDifference(sourceColors[2], targetColors[2]);

    if (redDifference <= sensitivity && greenDifference <= sensitivity && blueDifference <= sensitivity) {
      return true;
    }

    return false;
  }
}