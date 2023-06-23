package compiladores;

import java.util.List;

class Function extends Id {

    private List<String> args;

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public Function(String name, Boolean initialized, Boolean used, String dataType, List<String> args) {
        super(name, initialized, used, dataType);
        this.args = args;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Function - ").append(super.toString()).append("\n");
        sb.append("Arguments: ").append(args.toString());
        return sb.toString();
    }

}