package br.com.exemplo.syncapp.util.api.base.config;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

public class FilaExecucao {
    private static FilaExecucao pclsInstancia;
    private static Context pctxContexto;
    private RequestQueue pclsFilaExecucaoAplicacao;
    private int pintRequisicoesNaFila;

    private FilaExecucao(Context ctxContexto) {
        pctxContexto = ctxContexto;
        pclsFilaExecucaoAplicacao = RetornarFilaExecucaoAplicacao();
    }

    public static synchronized FilaExecucao FilaAtual(Context ctxContexto) {
        if (pclsInstancia == null) {
            pclsInstancia = new FilaExecucao(ctxContexto);
        }
        return pclsInstancia;
    }

    private RequestQueue RetornarFilaExecucaoAplicacao() {
        if (pclsFilaExecucaoAplicacao == null) {
            Cache cache = new DiskBasedCache(pctxContexto.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            pclsFilaExecucaoAplicacao = new RequestQueue(cache, network);
            pclsFilaExecucaoAplicacao.addRequestFinishedListener(new ListenerRequisicoesFinalizadas());

            // Don't forget to start the volley request queue
            pclsFilaExecucaoAplicacao.start();
            pintRequisicoesNaFila = 0;
        }

        return pclsFilaExecucaoAplicacao;
    }

    public void AdicionarRequisicaoNaFila(Request clsRequest) {
        AdicionarRequisicaoNaFila(clsRequest, 0);
    }

    public void AdicionarRequisicaoNaFila(Request clsRequest, int intTimeout) {
        clsRequest.setRetryPolicy(new DefaultRetryPolicy(intTimeout, -1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        pclsFilaExecucaoAplicacao.add(clsRequest);
        pintRequisicoesNaFila++;
    }

    private class ListenerRequisicoesFinalizadas implements RequestQueue.RequestFinishedListener <Object> {
        @Override
        public void onRequestFinished(Request<Object> request) {
            pintRequisicoesNaFila --;
        }
    }

    public int QuantidadeRequisicoesNaFila() {
        return pintRequisicoesNaFila;
    }
}
