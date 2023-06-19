package compiladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SymbolTable {
    private static SymbolTable instance;
    public String value;
    public List<Map<String, Id>> contexts;

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

    public void removeContext(int pos) {
        this.contexts.remove(pos);
    }

    public void addSymbol(int contextPos, String key, Id value) {
        // Map<String, Id> symbol = this.contexts.get(contextPos);
        // symbol.put(key, value);
        // Map<String, Id> symbol2 = this.contexts.get(contextPos);
        // System.out.println(symbol2.get(key).dataType);
    }

}

abstract class Id {
    String name;
    Boolean initialized;
    Boolean used;
    String dataType;
}

class Variable extends Id {

    public Variable (String name, Boolean initialized, Boolean used, String dataType) {
        this.name = name;
        this.initialized = initialized;
        this.used = used;
        this.dataType = dataType;
    }

}

class Function extends Id {

    public Function (String name, Boolean initialized, Boolean used, String dataType) {
        this.name = name;
        this.initialized = initialized;
        this.used = used;
        this.dataType = dataType;
    }
}
