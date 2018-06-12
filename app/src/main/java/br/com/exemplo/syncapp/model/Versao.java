package br.com.exemplo.syncapp.model;

public class Versao {
    private int compatibilidade;
    private int versao;
    private String release;
    private String versaoBancoDados;
    private String codigoCliente;

    public int getCompatibilidade() {
        return compatibilidade;
    }

    public void setCompatibilidade(int compatibilidade) {
        this.compatibilidade = compatibilidade;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getVersaoBancoDados() {
        return versaoBancoDados;
    }

    public void setVersaoBancoDados(String versaoBancoDados) {
        this.versaoBancoDados = versaoBancoDados;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }
}
