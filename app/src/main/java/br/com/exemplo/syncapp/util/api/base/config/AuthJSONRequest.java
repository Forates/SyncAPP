package br.com.exemplo.syncapp.util.api.base.config;
import android.content.Context;
import android.util.Base64;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AuthJSONRequest  extends JsonObjectRequest {

    private Context pctxContexto;

    private AuthJSONRequest(int method, String url, JSONObject jsonRequest,
                            Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    private AuthJSONRequest(String url, JSONObject jsonRequest,
                            Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public AuthJSONRequest(Context ctxContexto, int method, String url, JSONObject jsonRequest,
                           Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        this(method, url, jsonRequest, listener, errorListener);
        pctxContexto = ctxContexto;
    }

    public AuthJSONRequest(Context ctxContexto, String url, JSONObject jsonRequest,
                           Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        this(url, jsonRequest, listener, errorListener);
        pctxContexto = ctxContexto;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return createBasicAuthHeader();
    }

    Map<String, String> createBasicAuthHeader() throws AuthFailureError {
        Map<String, String> headerMap = new HashMap<>();

        String lstrToken = ApiConst.NOMECONTEXTO + ":" + ApiConst.CODIGOHDN;

        headerMap.put("Authorization", "Basic " + ConverterTextoParaBase64(lstrToken));

        return headerMap;
    }

    private String ConverterTextoParaBase64(String strTexto) {
        byte[] lvetBytes;
        try {
            lvetBytes = strTexto.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return Base64.encodeToString(lvetBytes, Base64.DEFAULT);
    }
}