package compiladores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SymbolTable {
    private static SymbolTable instance;
    public String value;
    public List<Map<String, Id>> contexts = new ArrayList<>();

    private SymbolTable(String value) {
        this.value = value;
    }

    public static SymbolTable getInstance(String value) {
        if (instance == null) {
            instance = new SymbolTable(value);
        }
        return instance;
    }

    public String getValue() {
        return this.value;
    }

    public void addContext() {
        this.contexts.add(new HashMap<String, Id>());
    }

    public void printContexts() {
        int index = 0;
        for (Map<String, Id> map : contexts) {
            System.out.println("Contexto: " + index);
            for (Map.Entry<String, Id> entry : map.entrySet()) {
                System.out.println("Clave: " + entry.getKey() + ", Valor: " + entry.getValue().toString());
            }
            index++;
        }
    }

    public void removeContext(int pos) {
        this.contexts.remove(pos);
    }

    public void addSymbol(String key, Id value, int contextPos) {
        int contextsSize = this.contexts.size();
        if (contextsSize > 0) {
            if (contextPos < 0) contextPos = contextsSize - 1;
            Map<String, Id> context = this.contexts.get(contextPos);
            context.put(key, value);
            // System.out.println("Clave: " + key + ", Valor: " + value + ", contextPos: " + contextPos);
        }
        else {
            System.err.println("SymbolTable.addSymbol err: contexts.size es 0");
        }
    }

}
