package compiladores;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import compiladores.compiladoresParser.AsignacionContext;
import compiladores.compiladoresParser.Call_fContext;
import compiladores.compiladoresParser.Call_f_factorContext;
import compiladores.compiladoresParser.Dec_f_paramsContext;
import compiladores.compiladoresParser.DeclaracionContext;
import compiladores.compiladoresParser.For_iContext;
import compiladores.compiladoresParser.If_iContext;
import compiladores.compiladoresParser.ProgramaContext;
import compiladores.compiladoresParser.Return_iContext;
import compiladores.compiladoresParser.While_iContext;

public class Escucha extends compiladoresBaseListener {
    private Integer nodos = 0;
    private Integer tokens = 0;
    private Integer errors = 0;
    
    @Override
    public void enterPrograma(ProgramaContext ctx) {
        System.out.println("Comienza el parsing...");
        super.enterPrograma(ctx);
    }

    @Override
    public void exitPrograma(ProgramaContext ctx) {
        super.exitPrograma(ctx);
        System.out.println("Fin de la compilacion");
        System.out.println(" - Se visitaron " + nodos + " nodos");
        System.out.println(" - Hay " + tokens + " tokens");
        System.out.println(" - Se encontraron " + errors + " errores");
    }

    @Override
    public void enterAsignacion(AsignacionContext ctx) {
        // System.out.println("\tNueva asignacion: |" + ctx.getText()
        //                         + "| - hijos = " + ctx.getChildCount());
        super.enterAsignacion(ctx);
    }

    @Override
    public void exitAsignacion(AsignacionContext ctx) {
        super.exitAsignacion(ctx);
        this.checkPYC(ctx, "Asignación");
    }

    @Override
    public void exitWhile_i(While_iContext ctx) {
        super.exitWhile_i(ctx);
        this.checkPA(ctx, "while");
    }    

    @Override
    public void exitFor_i(For_iContext ctx) {
        super.exitFor_i(ctx);
        this.checkPA(ctx, "for");
    }

    @Override
    public void exitIf_i(If_iContext ctx) {
        super.exitIf_i(ctx);
        this.checkPA(ctx, "if");
    }

    

    @Override
    public void exitDec_f_params(Dec_f_paramsContext ctx) {
        super.exitDec_f_params(ctx);
        this.checkPA(ctx, "Declaración función");
    }

    @Override
    public void exitDeclaracion(DeclaracionContext ctx) {
        super.exitDeclaracion(ctx);
        String ruleName = "Declaración";
        this.checkPYC(ctx, ruleName);
        this.checkDataType(ctx, ruleName);
    }

    @Override
    public void exitCall_f(Call_fContext ctx) {
        super.exitCall_f(ctx);
        String ruleName = "Llamada a función";
        this.checkPYC(ctx, ruleName);
        this.checkPA(ctx, ruleName);
    }


    @Override
    public void exitCall_f_factor(Call_f_factorContext ctx) {
        super.exitCall_f_factor(ctx);
        this.checkPA(ctx, "Llamada a función factor");
    }

    @Override
    public void exitReturn_i(Return_iContext ctx) {
        super.exitReturn_i(ctx);
                this.checkPYC(ctx, "Return");
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        nodos++;
        super.enterEveryRule(ctx);
    }

    @Override
    public void visitErrorNode(ErrorNode node) {
        // System.out.println("\t  err node: " + node.getText());
        errors++;
        super.visitErrorNode(node);
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        tokens++;
        super.visitTerminal(node);
    }

    /**
    * Check punto y coma
    *
    * @param ctx Rule context
    * @param ruleName Nombre de la regla
    */
    private void checkPYC(ParserRuleContext ctx, String ruleName) {
        try {
            String txt = ctx.getText();
            boolean ignorePYC = false;
            if (ruleName == "Declaración") {
                int childCount = ctx.getChildCount();
                if (childCount >= 3) {
                    ParseTree decType = ctx.getChild(2);
                    if (decType.getChildCount() > 0) {
                        String decTypeName = decType.getChild(0).getClass().getSimpleName();
                        if (decTypeName.equals("Dec_f_blkContext")) {
                            ignorePYC = true;
                        }
                    }
                }
            }
            if (!ignorePYC && !txt.endsWith(";")) {
                int linea = ctx.start.getLine();
                int columna = ctx.start.getCharPositionInLine() + ctx.start.getText().length();
                System.err.println("* Error sintáctico '" + ruleName + "':" + " Falta el punto y coma." + " Línea: "
                        + linea + ", columna: " + columna);
            }
        } catch (Exception e) {
            System.err.println("ERROR checkPYC: " + e);
        }
    }
    
    /**
    * Check apertura parentesis
    *
    * @param ctx Rule context
    * @param ruleName Nombre de la regla
    */
    private void checkPA(ParserRuleContext ctx, String ruleName) {
        try {
            int childCount = ctx.getChildCount();
            int childCountCompare = 0, getChildIndex = 0;
            if (ruleName.equals("while") || ruleName.equals("for") || ruleName.equals("if") || 
            ruleName.equals("Llamada a función") || ruleName.equals("Llamada a función factor")) {
                childCountCompare = 2;
                getChildIndex = 1;
            }
            else if (ruleName.equals("Declaración función")) {
                childCountCompare = -1;
                getChildIndex = 0;
            }
                
            if (!(childCount > childCountCompare) || !(ctx.getChild(getChildIndex).getText().equals("("))) {
                int linea = ctx.start.getLine();
                int columna = ctx.start.getCharPositionInLine() + ctx.start.getText().length() - 1;
                System.err.println("* Error sintáctico '" + ruleName + "':" + " Falta parentesis de apertura." + " Línea: "
                    + linea + ", columna: " + columna);
            }
            
        } catch (Exception e) {
            System.err.println("ERROR checkPA: " + e);
        }
    }

    /**
    * Check tipo de dato
    *
    * @param ctx Rule context
    * @param ruleName Nombre de la regla
    */
    private void checkDataType(ParserRuleContext ctx, String ruleName) {
        try {
            String txt = ctx.getText();
            boolean isFunction = false;
            if (ruleName == "Declaración") {
                int childCount = ctx.getChildCount();
                if (childCount >= 3) {
                    ParseTree decType = ctx.getChild(2);
                    if (decType.getChildCount() > 0) {
                        String decTypeName = decType.getChild(0).getClass().getSimpleName();
                        if (decTypeName.equals("Dec_f_blkContext") || decTypeName.equals("Dec_f_pycContext")) {
                            isFunction = true;
                        }
                    }
                }
            }

            if (!isFunction) {
                // int linea = ctx.start.getLine();
                // int columna = ctx.start.getCharPositionInLine() + ctx.start.getText().length();
                // System.err.println("* Error sintáctico '" + ruleName + "':" + " Falta el punto y coma." + " Línea: "
                //         + linea + ", columna: " + columna);
            }
        } catch (Exception e) {
            System.err.println("ERROR checkDataType: " + e);
        }
    }

}