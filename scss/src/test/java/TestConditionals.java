import org.testng.Assert;
import org.testng.annotations.Test;


public class TestConditionals extends TestBase
{
  @Test
  public void testIf()
  {
    String [] lines = {
        "@if 1 + 1 == 2 {}"
    };
    ScssParser.IfDeclarationContext context = parse(lines).statement(0).ifDeclaration();
    Assert.assertEquals(context.conditions().condition().commandStatement().expression(0).getText(), "1");
    Assert.assertEquals(context.conditions().condition().commandStatement().mathCharacter().getText(), "+");
    Assert.assertEquals(context.conditions().condition().commandStatement().commandStatement().expression(0).getText(), "1");

    Assert.assertEquals(context.conditions().condition().conditions().condition().commandStatement().expression(0).getText()
        , "2");


  }

  @Test
  public void testElseIf()
  {
    String [] lines = {
        "@if 1 + 1 == 2 {}",
        "@else if 1 + 1 == 3 {}"
    };
    ScssParser.ElseIfStatementContext context = parse(lines).statement(0).ifDeclaration().elseIfStatement(0);
    Assert.assertEquals(context.conditions().condition().commandStatement().expression(0).getText(), "1");
    Assert.assertEquals(context.conditions().condition().commandStatement().mathCharacter().getText(), "+");
    Assert.assertEquals(context.conditions().condition().commandStatement().commandStatement().expression(0).getText(), "1");

    Assert.assertEquals(context.conditions().condition().conditions().condition().commandStatement().expression(0).getText()
        , "3");
  }


  @Test
  public void testElseIfElse()
  {
    String [] lines = {
        "@if 1 + 1 == 2 {}",
        "@else if 1 + 1 == 3 {}",
        "@else { color: red }"

    };
    ScssParser.ElseStatementContext context = parse(lines).statement(0).ifDeclaration().elseStatement();
    Assert.assertEquals(context.block().property(0).identifier().getText(), "color");
    Assert.assertEquals(context.block().property(0).value().getText(), "red");

  }



  @Test
  public void testForLoop()
  {
    String [] lines = {
        "@for $i from 1 through 3 {}"

    };
    ScssParser.ForDeclarationContext context = parse(lines).statement(0).forDeclaration();
    Assert.assertEquals(context.variableName().getText(), "$i");
    Assert.assertEquals(context.fromNumber().getText(), "1");
    Assert.assertEquals(context.throughNumber().getText(), "3");



  }

  @Test
  public void testWhileLoop()
  {
    String [] lines = {
        "@while $i > 0 {}"

    };
    ScssParser.WhileDeclarationContext context = parse(lines).statement(0).whileDeclaration();
    Assert.assertEquals(context.conditions().condition().commandStatement().expression(0).variableName().getText(), "$i");
    Assert.assertEquals(context.conditions().condition().conditions().condition().commandStatement().getText(), "0");
  }

  @Test
  public void testBasicEach()
  {
    String [] lines = {
        "@each $animal in puma, sea-slug, egret, salamander {}"

    };
    ScssParser.EachDeclarationContext context = parse(lines).statement(0).eachDeclaration();
    Assert.assertEquals(context.variableName(0).getText(), "$animal");
    Assert.assertEquals(context.eachValueList().Identifier(0).getText(), "puma");
    Assert.assertEquals(context.eachValueList().Identifier(1).getText(), "sea-slug");
    Assert.assertEquals(context.eachValueList().Identifier(2).getText(), "egret");
    Assert.assertEquals(context.eachValueList().Identifier(3).getText(), "salamander");
  }

  @Test
  public void testBasicEachMultiAssign()
  {
    String [] lines = {
        "@each $animal, $color, $cursor in (puma, black, default), (sea-slug, blue, pointer) {}"

    };
    ScssParser.EachDeclarationContext context = parse(lines).statement(0).eachDeclaration();
    Assert.assertEquals(context.variableName(0).getText(), "$animal");
    Assert.assertEquals(context.variableName(1).getText(), "$color");
    Assert.assertEquals(context.variableName(2).getText(), "$cursor");


    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(0).getText(), "puma");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(1).getText(), "black");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(2).getText(), "default");

    Assert.assertEquals(context.eachValueList().identifierListOrMap(1).identifierValue(0).getText(), "sea-slug");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(1).identifierValue(1).getText(), "blue");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(1).identifierValue(2).getText(), "pointer");
  }

  @Test
  public void testBasicEachMultiAssignWithValue()
  {
    String [] lines = {
        "@each $header, $size in (h1: 2em, h2: 1.5em, h3: 1.2em) {}"

    };
    ScssParser.EachDeclarationContext context = parse(lines).statement(0).eachDeclaration();
    Assert.assertEquals(context.variableName(0).getText(), "$header");
    Assert.assertEquals(context.variableName(1).getText(), "$size");


    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(0).identifier().getText(), "h1");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(0).value().getText(), "2em");

    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(1).identifier().getText(), "h2");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(1).value().getText(), "1.5em");

    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(2).identifier().getText(), "h3");
    Assert.assertEquals(context.eachValueList().identifierListOrMap(0).identifierValue(2).value().getText(), "1.2em");
  }
}
