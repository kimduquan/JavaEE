package epf.lang.schema.ollama;

/**
 * 
 */
public class Function {

    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private String description;
    /**
     * 
     */
    private Parameters parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
}