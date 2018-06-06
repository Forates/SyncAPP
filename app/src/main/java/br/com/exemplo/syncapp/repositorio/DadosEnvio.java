package br.com.exemplo.syncapp.repositorio;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DadosEnvio implements Serializable {

    public String Sistema;
    public String Endereco;
    public String Retorno;
    public String IntencaoRetorno;
    @Id
    long ID;

    public DadosEnvio() {
    }

    public DadosEnvio(String sistema, String endereco, String retorno, String intencaoRetorno) {

        Sistema = sistema;

        Endereco = endereco;

        Retorno = retorno;

        IntencaoRetorno = intencaoRetorno;
    }

}