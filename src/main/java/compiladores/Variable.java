package compiladores;

class Variable extends Id {

    public Variable(String name, Boolean initialized, Boolean used, String dataType) {
        super(name, initialized, used, dataType);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Variable - ").append(super.toString()).append("\n");
        return sb.toString();
    }
}