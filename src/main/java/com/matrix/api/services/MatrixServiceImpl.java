package com.matrix.api.services;

import com.matrix.api.dto.Commands;
import com.matrix.api.dto.MatcherInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class MatrixServiceImpl implements MatrixService<List<String>, Commands> {

  private static final List<String> patterns = Arrays.asList("(^(\\d+(\\d+)*)$)",
      "^((\\d+(\\d+)*) (\\d+(\\d+)*)$)",
      "((UPDATE) ((\\d+(\\d+)*) (\\d+(\\d+)*) (\\d+(\\d+)*)) (\\d+(\\d+)*))",
      "((QUERY) ((\\d+(\\d+)*) (\\d+(\\d+)*) (\\d+(\\d+)*)) ((\\d+(\\d+)*) (\\d+(\\d+)*) (\\d+(\\d+)*)))");

  private Long[][][] matrix;

  @Override
  public List<String> calculate(Commands commands) {
    List<String> results = new ArrayList<>();

    commands.getCommandName().stream().forEach(p -> {
      MatcherInfo matcherInfo = getMatch(p, patterns, 0);
      switch (matcherInfo.getIndex()) {
        case 0:
          break;
        case 1:
          int dimensionN = Integer.parseInt(matcherInfo.getMatcher().group(2));
          matrix = new Long[dimensionN][dimensionN][dimensionN];
          for (int i = 0; i < dimensionN; i++)
            for (int j = 0; j < dimensionN; j++)
              for (int k = 0; k < dimensionN; k++)
                matrix[i][j][k] = 0L;
          break;
        case 2:
          updateMatrix(matrix, matcherInfo.getMatcher());
          break;
        case 3:
          results.add(queryMatrix(matrix, matcherInfo.getMatcher()));
          break;
        default:
          break;
      }
    });

    return results;
  }

  private MatcherInfo getMatch(String line, List<String> patterns, int index) {
    Pattern patterIndex = Pattern.compile(patterns.get(index));
    Matcher matcher = patterIndex.matcher(line);
    if (matcher.find()) {
      return new MatcherInfo(index, matcher);
    } else {
      return getMatch(line, patterns, index + 1);
    }
  }

  private void updateMatrix(Long[][][] matrix, Matcher matcher) {
    matrix[Integer.valueOf(matcher.group(4)) - 1][Integer.valueOf(matcher.group(6)) - 1][Integer
        .valueOf(matcher.group(8)) - 1] = Long.valueOf(matcher.group(10));
  }

  private String queryMatrix(Long[][][] matrix, Matcher matcher) {
    int x1 = Integer.valueOf(matcher.group(4)) - 1;
    int x2 = Integer.valueOf(matcher.group(11)) - 1;
    int y1 = Integer.valueOf(matcher.group(6)) - 1;
    int y2 = Integer.valueOf(matcher.group(13)) - 1;
    int z1 = Integer.valueOf(matcher.group(8)) - 1;
    int z2 = Integer.valueOf(matcher.group(15)) - 1;
    Long sum = 0L;
    for (int i = x1; i <= x2; i++)
      for (int j = y1; j <= y2; j++)
        for (int k = z1; k <= z2; k++)
          sum = sum + matrix[i][j][k];
    return String.valueOf(sum);
  }

}
