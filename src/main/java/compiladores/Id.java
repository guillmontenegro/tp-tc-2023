package compiladores;

abstract class Id {
    private String name;
    private Boolean initialized;
    private Boolean used;
    private String dataType;

    public Id(String name, Boolean initialized, Boolean used, String dataType) {
        this.name = name;
        this.initialized = initialized;
        this.used = used;
        this.dataType = dataType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInitialized(Boolean initialized) {
        this.initialized = initialized;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public Boolean getInitialized() {
        return initialized;
    }

    public Boolean getUsed() {
        return used;
    }

    public String getDataType() {
        return dataType;
    }

    @Override
    public String toString() {
        return  "Name: " + name +
                ", Initialized: " + initialized +
                ", Used: " + used +
                ", Data Type: " + dataType;
    }
}
