package br.com.exemplo.syncapp.util.api.base;
import com.android.volley.Response;
import org.json.JSONObject;

public interface IResponse {

    Response.Listener<JSONObject> result(JSONObject response);
    Response.ErrorListener error();


}