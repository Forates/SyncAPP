package br.com.exemplo.syncapp.util.api.servico;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;
import br.com.exemplo.syncapp.util.api.base.IResponse;
import br.com.exemplo.syncapp.util.api.base.ServicoRequisicaoBase;

public class ExemploServico <T> extends ServicoRequisicaoBase{

    protected ExemploServico(Context ctxContexto) {
        super(ctxContexto);
    }

    public void create(final T model, IResponse response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = ConverterObjectEmJSON(model);
            PostJSON(URL+"Criar", jsonObject, response.result(jsonObject),response.error(), false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
