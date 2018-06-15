package br.com.exemplo.syncapp.util.api.servico;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
import br.com.exemplo.syncapp.util.api.base.acao.IAcaoResponse;
import br.com.exemplo.syncapp.util.api.base.IResponse;
import br.com.exemplo.syncapp.util.api.base.ServicoRequisicaoBase;
import br.com.exemplo.syncapp.util.api.base.acao.IAcaoRequisicao;

public class ServicoComString  extends ServicoRequisicaoBase{

    public ServicoComString(Context ctxContexto) {
        super(ctxContexto);
    }

    public void post(final String model, IResponse response, String rota) {
        try {
            JSONObject jsonObject = new JSONObject(model);
            PostJSON(URL+rota, jsonObject, response.result(jsonObject),response.error(), false);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void get (final IAcaoResponse acaoResponse, final IAcaoRequisicao acaoErro, String rota){
        try {
            Response.Listener<JSONObject> lstResponse = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    acaoResponse.execute(response);
                }
            };

            Response.ErrorListener lstError = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    acaoErro.execute();
                }
            };

            GetJSONObject(URL + rota, lstResponse, lstError);
        }catch (Exception e){}
    }

    public void put(final String model, final IAcaoRequisicao acaoSuccess, final IAcaoRequisicao acaoError, String rota){

        Response.Listener<JSONObject> lstSuccess = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                acaoSuccess.execute();
                Log.i("SYNC-HTTP_","Atualizado com sucesso!" );
            }
        };

        Response.ErrorListener lstError = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                acaoError.execute();
                Log.i("SYNC-HTTP_", "Erro ao atualizar: "+error);
            }
        };

        try {
            JSONObject jsonObject = new JSONObject(model);
            PutJSON(URL + rota, jsonObject, lstSuccess, lstError, false);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("SYNC-HTTP_", "Erro ao atualizar: "+ e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> JSONObject ConverterObjectEmJSON(T clsObjeto) throws JSONException {
        return super.ConverterObjectEmJSON(clsObjeto);
    }

    @Override
    public <T> T ConverterJSONEmObject(JSONObject clsObjeto, Class<T> clClasse) {
        return super.ConverterJSONEmObject(clsObjeto, clClasse);
    }
}
