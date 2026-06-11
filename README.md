---
title: Student Support Code for 'Syntaxhighlighting' Task
---

<!-- pandoc -s -f markdown -t markdown --columns=94 --reference-links=true README.md -->

# Praktikum – Post Mortem

## Zusammenfassung

In dieser Aufgabe habe ich zwei Teilbereiche bearbeitet.

Im ersten Teil habe ich im Syntaxhighlighting-Projekt mit ANTLR weitergearbeitet. Dafür wurde der `AntlrTokenCollector` implementiert. Der Collector nutzt den von ANTLR generierten `MiniJavaLexer`, um aus dem Eingabetext Tokens zu erzeugen. Diese Tokens werden anschließend in `HighlightRegion`-Objekte umgewandelt, damit sie im Editor farbig hervorgehoben werden können.

Außerdem wurde der `PrettyPrinterVisitor` erweitert. Dabei wurden die Methoden `visitCompilationUnit`, `visitClassBody`, `visitBlock` und `visitStatement` implementiert. Ziel war es, MiniJava-Code strukturiert auszugeben, sodass Einrückungen und Zeilenumbrüche konsistent erzeugt werden.

Im zweiten Teil habe ich im Cycle-Chronicles-Projekt die Methode `Shop.accept` analysiert. Dazu habe ich Äquivalenzklassen und Grenzwerte dokumentiert. Anschließend habe ich JUnit-Tests mit Mockito geschrieben, um die Methode `Shop.accept` zu testen. Getestet wurden gültige Fahrradtypen, abgelehnte Fahrradtypen, doppelte Kunden und die maximale Anzahl offener Aufträge in der Warteschlange.

## Details

Beim ANTLR-Teil war wichtig, dass der Lexer die Eingabe bereits in Tokens zerlegt. Dadurch können Highlight-Regionen direkter erzeugt werden als bei der Regex-Variante. Im Pretty Printer war besonders die Struktur des Parse-Trees wichtig. Die Ausgabe sollte nicht perfekt formatiert sein, aber Blöcke, Statements und Klassenteile sollten sinnvoll eingerückt und getrennt werden.

Im Cycle-Chronicles-Teil war wichtig, dass die Klasse `Order` noch nicht vollständig implementiert ist. Die Methoden `getBicycleType()` und `getCustomer()` werfen in der Vorgabe noch eine `UnsupportedOperationException`. Deshalb wurden `Order`-Objekte in den Tests mit Mockito gemockt. Die Methode `Shop.accept` selbst wurde dabei nicht gemockt, sondern wirklich getestet.

## Reflexion

Der schwierigste Teil war für mich das Verständnis, wie ANTLR den Code in Tokens und Parse-Tree-Strukturen zerlegt. Beim Pretty Printing musste ich darauf achten, dass Einrückungen und Zeilenumbrüche an sinnvollen Stellen erzeugt werden.

Beim Cycle-Chronicles-Projekt habe ich besser verstanden, wie Äquivalenzklassen und Grenzwerte zu konkreten Testfällen führen. Außerdem habe ich gelernt, warum Mockito hilfreich ist, wenn abhängige Klassen noch nicht vollständig implementiert sind.

Insgesamt habe ich besser verstanden, wie Lexer, Visitor, JUnit, Mockito, Git-Branches und Pull Requests zusammenarbeiten.

## Lösungsrepos

Syntaxhighlighting:
https://github.com/ZARS1/prog2_ybel_syntaxhighlighting

Cycle Chronicles:
https://github.com/ZARS1/prog2_ybel_cyclechronicles

## Pull Requests

ANTLR / Pretty Printing:
[[PR-Link einfügen]](https://github.com/ZARS1/prog2_ybel_syntaxhighlighting/pulls?q=is%3Apr+is%3Aclosed)

Cycle Chronicles / Shop.accept Tests:
[[PR-Link einfügen]](https://github.com/ZARS1/prog2_ybel_cyclechronicles/pulls)

## License

This [work] by [Carsten Gips] and [contributors] is licensed under [MIT].

  [Syntaxhighlighting task]: https://github.com/Programmiermethoden-CampusMinden/Prog2-Lecture/tree/master/homework
  [work]: https://github.com/Programmiermethoden-CampusMinden/prog2_ybel_syntaxhighlighting
  [Carsten Gips]: https://github.com/cagix
  [contributors]: https://github.com/Programmiermethoden-CampusMinden/prog2_ybel_syntaxhighlighting/graphs/contributors
  [MIT]: LICENSE.md
