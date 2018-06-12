package br.com.exemplo.syncapp.util.api.base;

public interface IAcaoResponse<T> {
    void execute(T response);
}
