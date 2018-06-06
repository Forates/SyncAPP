package br.com.exemplo.syncapp.repositorio;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class RepositorioEnvio {

    @Id
    long ID;

    public String Sistema;

    public String Endereco;

    public String Retorno;

    public String IntencaoRetorno;

    public String Enviado;

    public RepositorioEnvio() {
    }

    public RepositorioEnvio(String sistema, String endereco, String retorno, String intencaoRetorno) {
        Sistema = sistema;

        Endereco = endereco;

        Retorno = retorno;

        IntencaoRetorno = intencaoRetorno;

        Enviado = "N";
    }

    @Override
    public String toString() {
        return "Sistema: " + Sistema + "-Endereço: " + Endereco + "-Retorno: " + Retorno + "-IntencaoRetorno: " + IntencaoRetorno + "-Enviado: " + Enviado;
    }
}
