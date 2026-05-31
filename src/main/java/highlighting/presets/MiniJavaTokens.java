package highlighting.presets;

import highlighting.regex.Token;
import java.util.List;
import java.util.regex.Pattern;

public final class MiniJavaTokens {

  private MiniJavaTokens() {}

  public static List<Token> defaultTokens() {
    return List.of(
        Token.of(Pattern.compile("/\\*\\*[\\s\\S]*?\\*/"), MiniJavaColours.JAVADOC_COMMENT_COLOUR),
        Token.of(Pattern.compile("/\\*[\\s\\S]*?\\*/"), MiniJavaColours.BLOCK_COMMENT_COLOUR),
        Token.of(Pattern.compile("//[^\\r\\n]*"), MiniJavaColours.LINE_COMMENT_COLOUR),
        Token.of(Pattern.compile("\"([^\"\\\\]|\\\\.)*\""), MiniJavaColours.STRING_LITERAL_COLOUR),
        Token.of(Pattern.compile("'([^'\\\\]|\\\\.)'"), MiniJavaColours.CHAR_LITERAL_COLOUR),
        Token.of(
            Pattern.compile(
                "\\b(package|import|class|public|private|protected|static|final|return|null|new|if|else|for|while|switch|case|default|break|continue|void|int|boolean|char|String|this|super|extends|implements|interface|enum|try|catch|throw|throws)\\b"),
            MiniJavaColours.KEYWORD_COLOUR),
        Token.of(Pattern.compile("@[A-Za-z_][A-Za-z0-9_]*"), MiniJavaColours.ANNOTATION_COLOUR));
  }
}
