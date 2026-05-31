package highlighting.regex;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaTokens;
import java.util.ArrayList;
import java.util.List;

public class RegexHighlighter extends SyntaxHighlighter {

  @Override
  public List<HighlightRegion> collectMatches(String text) {
    return MiniJavaTokens.defaultTokens().stream()
        .flatMap(token -> token.test(text).stream())
        .toList();
  }

  @Override
  public List<HighlightRegion> resolveConflicts(List<HighlightRegion> regions) {
    List<HighlightRegion> result = new ArrayList<>();

    for (HighlightRegion region : regions) {
      boolean overlapsWithKeptRegion = result.stream().anyMatch(kept -> overlaps(kept, region));

      if (!overlapsWithKeptRegion) {
        result.add(region);
      }
    }

    return result;
  }

  private boolean overlaps(HighlightRegion first, HighlightRegion second) {
    return first.start() < second.end() && second.start() < first.end();
  }
}
