package autoFlow.config;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import java.io.Serializable;

public abstract class AbstractConfig implements Serializable {
    private static final long serialVersionUID = -6643881959277425014L;
    private String id;

    public AbstractConfig() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
