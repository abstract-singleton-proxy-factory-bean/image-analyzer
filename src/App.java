import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.FileWriter;

import lib.Analyzer;
import lib.ImageAnalyzer;
import utils.Pair;

public class App {
  public static void main(String[] args) throws Exception {
    var files = Files.walk(Paths.get("./images")).filter(Files::isRegularFile).sorted(App::sortFile)
        .collect(Collectors.toList());

    handleImageManipulation(files);
  }

  private static void handleImageManipulation(List<Path> files) throws Exception {
    var percentages = new ArrayList<Pair<Integer, Double>>();
    var colorSensitivity = 20;
    var baseColor = Color.decode("#3b5130");
    var highlightColor = Color.decode("#ff0000");

    for (var file : files) {
      var image = ImageAnalyzer.readImageFile(file.toString());
      var imageAnalyzer = new ImageAnalyzer(colorSensitivity, baseColor, highlightColor);
      var analyzedImage = imageAnalyzer.highlightBaseColor(image);
      var percentage = imageAnalyzer.getPercentageOfBaseColor(image);

      percentages.add(new Pair<>(getYear(file.getFileName().toString()), percentage));
      Analyzer.saveImage(analyzedImage, "./analyzed/" + file.getFileName().toString());
    }

    var bufferedWriter = new BufferedWriter(new FileWriter("statistics.json"));
    var json = convertResultToJSON(percentages);

    bufferedWriter.write(json);
    bufferedWriter.close();
  }

  private static String convertResultToJSON(ArrayList<Pair<Integer, Double>> results) {
    var json = "[";

    for (var result : results) {
      json += String.format("{\"year\":%d,\"percentage\":%f},", result.first, result.second);
    }

    json = json.substring(0, json.length() - 1) + "]";

    return json;
  }

  private static int getYear(String filename) {
    return Integer.parseInt(filename.replace(".jpeg", ""));
  }

  private static int sortFile(Path a, Path b) {
    var aYear = getYear(a.getFileName().toString());
    var bYear = getYear(b.getFileName().toString());

    return aYear > bYear ? 1 : -1;
  }
}
