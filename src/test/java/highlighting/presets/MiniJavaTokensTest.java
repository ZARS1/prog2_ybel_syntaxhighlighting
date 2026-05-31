package highlighting.presets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import highlighting.core.HighlightRegion;
import highlighting.regex.Token;
import java.awt.Color;
import java.util.List;
import org.junit.jupiter.api.Test;

class MiniJavaTokensTest {

  @Test
  void givenStringAtBeginning_whenTestStringToken_thenFindsRegion() {
    Token token = tokenByColour(MiniJavaColours.STRING_LITERAL_COLOUR);

    List<HighlightRegion> regions = token.test("\"hello\" text");

    assertEquals(1, regions.size());
    assertEquals(0, regions.getFirst().start());
    assertEquals(7, regions.getFirst().end());
  }

  @Test
  void givenStringWithLineCommentTextInside_whenTestStringToken_thenFindsWholeString() {
    Token token = tokenByColour(MiniJavaColours.STRING_LITERAL_COLOUR);

    List<HighlightRegion> regions = token.test("\"not // comment\"");

    assertEquals(1, regions.size());
    assertEquals(0, regions.getFirst().start());
    assertEquals(16, regions.getFirst().end());
  }

  @Test
  void givenCharLiteral_whenTestCharToken_thenFindsRegion() {
    Token token = tokenByColour(MiniJavaColours.CHAR_LITERAL_COLOUR);

    List<HighlightRegion> regions = token.test("char c = 'x';");

    assertEquals(1, regions.size());
    assertEquals(9, regions.getFirst().start());
    assertEquals(12, regions.getFirst().end());
  }

  @Test
  void givenKeywordAtMiddle_whenTestKeywordToken_thenFindsKeyword() {
    Token token = tokenByColour(MiniJavaColours.KEYWORD_COLOUR);

    List<HighlightRegion> regions = token.test("public class Test");

    assertEquals(2, regions.size());
    assertEquals(
        "public", "public class Test".substring(regions.get(0).start(), regions.get(0).end()));
    assertEquals(
        "class", "public class Test".substring(regions.get(1).start(), regions.get(1).end()));
  }

  @Test
  void givenKeywordInsideIdentifier_whenTestKeywordToken_thenDoesNotMatch() {
    Token token = tokenByColour(MiniJavaColours.KEYWORD_COLOUR);

    List<HighlightRegion> regions = token.test("myclass publicValue");

    assertEquals(0, regions.size());
  }

  @Test
  void givenAnnotation_whenTestAnnotationToken_thenFindsRegion() {
    Token token = tokenByColour(MiniJavaColours.ANNOTATION_COLOUR);

    List<HighlightRegion> regions = token.test("@Override public String toString()");

    assertEquals(1, regions.size());
    assertEquals(0, regions.getFirst().start());
    assertEquals(9, regions.getFirst().end());
  }

  @Test
  void givenLineComment_whenTestLineCommentToken_thenFindsUntilLineEnd() {
    Token token = tokenByColour(MiniJavaColours.LINE_COMMENT_COLOUR);

    List<HighlightRegion> regions = token.test("int x; // comment\nint y;");

    assertEquals(1, regions.size());
    assertEquals(
        "// comment",
        "int x; // comment\nint y;"
            .substring(regions.getFirst().start(), regions.getFirst().end()));
  }

  @Test
  void givenBlockComment_whenTestBlockCommentToken_thenFindsWholeComment() {
    Token token = tokenByColour(MiniJavaColours.BLOCK_COMMENT_COLOUR);

    List<HighlightRegion> regions = token.test("code /* block comment */ code");

    assertEquals(1, regions.size());
    assertEquals(
        "/* block comment */",
        "code /* block comment */ code"
            .substring(regions.getFirst().start(), regions.getFirst().end()));
  }

  @Test
  void givenJavadocComment_whenTestJavadocToken_thenFindsWholeComment() {
    Token token = tokenByColour(MiniJavaColours.JAVADOC_COMMENT_COLOUR);

    List<HighlightRegion> regions = token.test("/** documentation */");

    assertEquals(1, regions.size());
    assertEquals(0, regions.getFirst().start());
    assertEquals("/** documentation */".length(), regions.getFirst().end());
  }

  @Test
  void givenTextWithoutAnnotation_whenTestAnnotationToken_thenFindsNoRegion() {
    Token token = tokenByColour(MiniJavaColours.ANNOTATION_COLOUR);

    List<HighlightRegion> regions = token.test("Override without at sign");

    assertTrue(regions.isEmpty());
  }

  private static Token tokenByColour(Color colour) {
    return MiniJavaTokens.defaultTokens().stream()
        .filter(token -> token.colour().equals(colour))
        .findFirst()
        .orElseThrow();
  }
}
