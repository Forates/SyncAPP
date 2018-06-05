package br.com.exemplo.syncapp.repositorio;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class RepositorioEnvio {

    @Id
    long ID;

    String Sistema;

    String Endereco;

    String Retorno;

    String IntencaoRetorno;

    boolean Enviado;

}
