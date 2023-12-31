package compiladores;

import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

// Las diferentes entradas se explicaran oportunamente
public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, Compilador!!!");
        // create a CharStream that reads from file
        CharStream input = CharStreams.fromFileName("input/prog.txt");

        // create a lexer that feeds off of input CharStream
        compiladoresLexer lexer = new compiladoresLexer(input);
        
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        // create a parser that feeds off the tokens buffer
        compiladoresParser parser = new compiladoresParser(tokens);

        SymbolTable tablaSimbolos = SymbolTable.getInstance("tabla_test");
        tablaSimbolos.addContext();
        // Variable varTest = new Variable("test1", false, false, "int");
        // List<String> args2 = new ArrayList<>(); args2.add("5"); args2.add("testParam");
        // Function funcTest = new Function("test1", false, false, "int", args2);
        // tablaSimbolos.addSymbol("varKeyTest", varTest, -1);
        // tablaSimbolos.addSymbol("funcKeyTest", funcTest, -1);
        // tablaSimbolos.printContexts();
                
        // create Listener
        compiladoresBaseListener escucha = new Escucha();

        // Conecto el objeto con Listeners al parser
        parser.addParseListener(escucha);

        // Solicito al parser que comience indicando una regla gramatical
        // En este caso la regla es el simbolo inicial
        // parser.s();
        ParseTree tree = parser.programa();
        // Conectamos el visitor
        // Caminante walker = new Caminante();
        // walker.visit(tree);
        // System.out.println(walker);
        // System.out.println(walker.getErrorNodes());
        // Imprime el arbol obtenido
        System.out.println(tree.toStringTree(parser));
        // System.out.println(escucha);
        
    }
}
