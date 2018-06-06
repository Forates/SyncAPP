package br.com.exemplo.syncapp.util.api.base.enuns;


public enum TypeContent {
    Json("application/json"),
    FormUrlEncoded("application/x-www-form-urlencoded");

    private final String name;

    private TypeContent(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}