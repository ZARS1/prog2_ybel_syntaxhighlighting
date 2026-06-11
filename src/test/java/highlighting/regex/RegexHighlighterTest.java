package highlighting.regex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import highlighting.core.HighlightRegion;
import highlighting.presets.MiniJavaColours;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegexHighlighterTest {

  @Test
  void givenSimpleKeyword_whenComputeRegions_thenFindsKeyword() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions = highlighter.computeRegions("public class Test");

    assertEquals(2, regions.size());
    assertEquals(MiniJavaColours.KEYWORD_COLOUR, regions.get(0).colour());
    assertEquals(MiniJavaColours.KEYWORD_COLOUR, regions.get(1).colour());
  }

  @Test
  void givenCommentWithKeyword_whenComputeRegions_thenKeepsOnlyCommentRegion() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions = highlighter.computeRegions("// public class");

    assertEquals(1, regions.size());
    assertEquals(MiniJavaColours.LINE_COMMENT_COLOUR, regions.getFirst().colour());
  }

  @Test
  void givenJavadocComment_whenComputeRegions_thenKeepsJavadocInsteadOfBlockComment() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions = highlighter.computeRegions("/** public class */");

    assertEquals(1, regions.size());
    assertEquals(MiniJavaColours.JAVADOC_COMMENT_COLOUR, regions.getFirst().colour());
  }

  @Test
  void givenStringWithCommentText_whenComputeRegions_thenKeepsOnlyStringRegion() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions = highlighter.computeRegions("\"not // comment\"");

    assertEquals(1, regions.size());
    assertEquals(MiniJavaColours.STRING_LITERAL_COLOUR, regions.getFirst().colour());
  }

  @Test
  void givenSeparatedRegions_whenComputeRegions_thenKeepsAllRegions() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions = highlighter.computeRegions("public \"text\" @Override");

    assertEquals(3, regions.size());
  }

  @Test
  void givenTextWithoutMatches_whenComputeRegions_thenReturnsEmptyList() {
    RegexHighlighter highlighter = new RegexHighlighter();

    List<HighlightRegion> regions = highlighter.computeRegions("abc xyz");

    assertTrue(regions.isEmpty());
  }
}
