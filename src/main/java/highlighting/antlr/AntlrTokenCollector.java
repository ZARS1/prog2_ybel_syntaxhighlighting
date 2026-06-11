package highlighting.antlr;

import highlighting.core.HighlightRegion;
import highlighting.core.SyntaxHighlighter;
import highlighting.presets.MiniJavaColours;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class AntlrTokenCollector extends SyntaxHighlighter {

  private static final Set<String> KEYWORDS =
      Set.of(
          "package",
          "import",
          "class",
          "public",
          "private",
          "protected",
          "static",
          "final",
          "return",
          "null",
          "new",
          "if",
          "else",
          "for",
          "while",
          "switch",
          "case",
          "default",
          "break",
          "continue",
          "void",
          "int",
          "boolean",
          "char",
          "String",
          "this",
          "super",
          "extends",
          "implements",
          "interface",
          "enum",
          "try",
          "catch",
          "throw",
          "throws");

  @Override
  public List<HighlightRegion> collectMatches(String text) {
    var lexer = new MiniJavaLexer(CharStreams.fromString(text));
    var tokenStream = new CommonTokenStream(lexer);
    tokenStream.fill();

    List<HighlightRegion> regions = new ArrayList<>();
    List<org.antlr.v4.runtime.Token> tokens = tokenStream.getTokens();

    for (int i = 0; i < tokens.size(); i++) {
      var token = tokens.get(i);

      if (token.getType() == org.antlr.v4.runtime.Token.EOF) {
        continue;
      }

      String tokenText = token.getText();
      Color colour = colourFor(tokenText);

      if (colour != null) {
        regions.add(new HighlightRegion(token.getStartIndex(), token.getStopIndex() + 1, colour));
      }

      if ("@".equals(tokenText) && i + 1 < tokens.size()) {
        var nextToken = tokens.get(i + 1);

        if (nextToken.getType() != org.antlr.v4.runtime.Token.EOF
            && isIdentifier(nextToken.getText())) {
          regions.add(
              new HighlightRegion(
                  token.getStartIndex(),
                  nextToken.getStopIndex() + 1,
                  MiniJavaColours.ANNOTATION_COLOUR));
          i++;
        }
      }
    }

    return regions;
  }

  private static Color colourFor(String tokenText) {
    if (tokenText.startsWith("/**")) {
      return MiniJavaColours.JAVADOC_COMMENT_COLOUR;
    }

    if (tokenText.startsWith("/*")) {
      return MiniJavaColours.BLOCK_COMMENT_COLOUR;
    }

    if (tokenText.startsWith("//")) {
      return MiniJavaColours.LINE_COMMENT_COLOUR;
    }

    if (tokenText.startsWith("\"")) {
      return MiniJavaColours.STRING_LITERAL_COLOUR;
    }

    if (tokenText.startsWith("'")) {
      return MiniJavaColours.CHAR_LITERAL_COLOUR;
    }

    if (tokenText.startsWith("@")) {
      return MiniJavaColours.ANNOTATION_COLOUR;
    }

    if (KEYWORDS.contains(tokenText)) {
      return MiniJavaColours.KEYWORD_COLOUR;
    }

    return null;
  }

  private static boolean isIdentifier(String tokenText) {
    return tokenText.matches("[A-Za-z_][A-Za-z0-9_]*");
  }
}
