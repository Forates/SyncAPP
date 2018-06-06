package br.com.exemplo.syncapp.util.api.base;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.List;
import br.com.exemplo.syncapp.util.api.base.acao.IAcaoRequisicao;
import br.com.exemplo.syncapp.util.api.base.config.ApiConst;
import br.com.exemplo.syncapp.util.api.base.config.AuthJSONRequest;
import br.com.exemplo.syncapp.util.api.base.config.FilaExecucao;

public class ServicoRequisicaoBase {
    protected  String URL,
            ERRO_TIMEOUT = "Falha ao conectar no servidor. Erro Timeout.",
            ERRO_AUTH = "Erro de autenticação.",
            ERRO_SERVER = "Não foi possível conectar ao servidor.",
            ERRO_NETWORK = "Não foi possível estabelecer conexão com a internet.",
            ERRO_PARSE = "Não foi possível realizar as conversões dos dados enviados pelo servidor.";

    protected Context pctxContexto;

    protected ServicoRequisicaoBase(Context ctxContexto) {
        pctxContexto = ctxContexto;
        URL = ApiConst.ENDERECOAPI;
    }

    public void PostJSON(String strUrl, JSONObject jsnObjetoParam,
                         Response.Listener<JSONObject> lstnSucesso,
                         Response.ErrorListener lstnErro, boolean blnAutenticar) {
        try {

            JsonObjectRequest lclsRequisicao;

             if (blnAutenticar){
             lclsRequisicao = new AuthJSONRequest(pctxContexto, Request.Method.POST, strUrl,
                 jsnObjetoParam, lstnSucesso, lstnErro);
             }
              else {
            lclsRequisicao = new JsonObjectRequest(Request.Method.POST, strUrl,
                    jsnObjetoParam, lstnSucesso, lstnErro);
              }

            FilaExecucao.FilaAtual(pctxContexto).AdicionarRequisicaoNaFila(lclsRequisicao);
        }catch (Exception e){
            Log.i("SYNC-MENSAGEM_SERVICE_",e.getMessage());
            validaErros((VolleyError) e,null);

        }

    }

    protected void PutJSON(String strUrl, JSONObject jsnObjetoParam,
                           Response.Listener<JSONObject> lstnSucesso,
                           Response.ErrorListener lstnErro, boolean blnAutenticar) {
        try {
            JsonObjectRequest lclsRequisicao;

             if (blnAutenticar){
             lclsRequisicao = new AuthJSONRequest(pctxContexto, Request.Method.PUT, strUrl,
                 jsnObjetoParam, lstnSucesso, lstnErro);
             }
              else {
            lclsRequisicao = new JsonObjectRequest(Request.Method.PUT, strUrl,
                    jsnObjetoParam, lstnSucesso, lstnErro);
            }

            FilaExecucao.FilaAtual(pctxContexto).AdicionarRequisicaoNaFila(lclsRequisicao);
        }catch (Exception e){
            validaErros((VolleyError) e,null);
        }
    }

    protected void GetJSONArray(String strUrl, Response.Listener<JSONArray> lstnSucesso,
                                Response.ErrorListener lstnErro) {
        try {
            JsonArrayRequest lclsRequisicao;
            lclsRequisicao = new JsonArrayRequest(strUrl, lstnSucesso, lstnErro);

            FilaExecucao.FilaAtual(pctxContexto).AdicionarRequisicaoNaFila(lclsRequisicao);
        }catch (Exception e){
            validaErros((VolleyError) e,null);
        }
    }

    protected void GetJSONObject(String strUrl, Response.Listener<JSONObject> lstnSucesso,
                                 Response.ErrorListener lstnErro) {
        try {

            JsonObjectRequest lclsRequisicao;
            lclsRequisicao = new JsonObjectRequest(Request.Method.GET, strUrl, null, lstnSucesso, lstnErro);

            FilaExecucao.FilaAtual(pctxContexto).AdicionarRequisicaoNaFila(lclsRequisicao);
        }catch (Exception e){
            validaErros((VolleyError) e,null);
        }
    }

    protected <T> T ConverterJSONEmObject(JSONObject clsObjeto, Class<T> clClasse, FieldNamingPolicy clsPolicy) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(clsPolicy)
                .create();
        return gson.fromJson(clsObjeto.toString(), clClasse);
    }

    protected <T> T ConverterJSONEmObject(JSONObject clsObjeto, Class<T> clClasse) {
        return ConverterJSONEmObject(clsObjeto, clClasse, FieldNamingPolicy.IDENTITY);
    }

    protected <T> List<T> ConverterJSONEmList(JSONObject clsObjeto) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create();

        Type listType = new TypeToken<List<T>>(){}.getType();

        return (List<T>) gson.fromJson(clsObjeto.toString(), listType);
    }

    protected <T> JSONObject ConverterObjectEmJSON(T clsObjeto) throws JSONException {
        Gson gson = new Gson();
        String json = gson.toJson(clsObjeto);
        return new JSONObject(json);
    }

    public void validaErros(VolleyError volleyError, IAcaoRequisicao acao) {
        if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
            Log.i("SYNC-VOLEY_",ERRO_TIMEOUT+": "+volleyError.getMessage());
        } else if (volleyError instanceof AuthFailureError) {
            Log.i("SYNC-VOLEY_",ERRO_AUTH+": "+volleyError.getMessage());
        } else if (volleyError instanceof ServerError) {
            Log.i("SYNC-VOLEY_",ERRO_SERVER+": "+volleyError.getMessage());
        } else if (volleyError instanceof NetworkError) {
            Log.i("SYNC-VOLEY_",ERRO_NETWORK+": "+volleyError.getMessage());
        } else if (volleyError instanceof ParseError) {
            Log.i("SYNC-VOLEY_",ERRO_PARSE+": "+volleyError.getMessage());
        } else {
            //Toast.makeText(pctxContexto, volleyError.getMessage(), Toast.LENGTH_LONG).show();
            Log.i("SYNC-VOLEY_",volleyError.getLocalizedMessage());
        }

        if(acao != null){
            acao.execute();
        }

    }
}
