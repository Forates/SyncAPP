package br.com.exemplo.syncapp.util.api.base.acao;

public interface IAcaoResponse<T> {
    void execute(T response);
}
