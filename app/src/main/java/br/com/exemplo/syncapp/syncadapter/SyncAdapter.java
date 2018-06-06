package br.com.exemplo.syncapp.syncadapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

import br.com.exemplo.syncapp.App;
import br.com.exemplo.syncapp.repositorio.DadosEnvio;
import br.com.exemplo.syncapp.repositorio.RepositorioEnvio;
import br.com.exemplo.syncapp.repositorio.RepositorioEnvio_;
import io.objectbox.Box;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private static String TAG = "SYNC-ADAPTER";
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    Box<RepositorioEnvio> mBoxRepositorioEnvio;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        Log.i(TAG, "Service created");
        mContentResolver = context.getContentResolver();
        Log.i(TAG, "Content Resolver created");

        mBoxRepositorioEnvio = ((App) context).getBoxRepositorioEnvio();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        Log.i(TAG, "Service created");
        mContentResolver = context.getContentResolver();
        Log.i(TAG, "Content Resolver created");
    }

    /*
     * Specify the code you want to run in the sync adapter. The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing.
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {

        /*
         * Put the data transfer code here.
         */
        Log.i(TAG, "Executando Sync");

        DadosEnvio dadosEnvio = null;

        if (extras.containsKey("DadosEnvio-Sistema")) {

            String sistema = extras.getString("DadosEnvio-Sistema");
            String endereco = extras.getString("DadosEnvio-Endereco");
            String retorno = extras.getString("DadosEnvio-Retorno");
            String intencaoRetorno = extras.getString("DadosEnvio-IntecaoRetorno");

            dadosEnvio = new DadosEnvio(sistema, endereco, retorno, intencaoRetorno);
        }

        if (dadosEnvio == null) {
            Log.i(TAG, "Total Pendencias: " + mBoxRepositorioEnvio.count());

            Log.i(TAG, "Pendencias enviadas: " + mBoxRepositorioEnvio.find(RepositorioEnvio_.Enviado, "S").size());
            Log.i(TAG, "Pendencias não enviadas: " + mBoxRepositorioEnvio.find(RepositorioEnvio_.Enviado, "N").size());

            for (RepositorioEnvio repositorioEnvio : mBoxRepositorioEnvio.getAll()) {
                if (repositorioEnvio.Enviado.equals("N")) {
                    RealizarOperacaoEnvio(repositorioEnvio);
                }
            }
        } else {

            mBoxRepositorioEnvio.put(ConverterDadosEnvio(dadosEnvio));

            Log.i(TAG, "Pendencias adicionada!");

        }


    }

    private RepositorioEnvio ConverterDadosEnvio(Serializable dadosEnvio) {

        DadosEnvio dadosEnvio1 = (DadosEnvio) dadosEnvio;

        return new RepositorioEnvio(dadosEnvio1.Sistema, dadosEnvio1.Endereco,
                dadosEnvio1.Retorno, dadosEnvio1.IntencaoRetorno);

    }

    private void RealizarOperacaoEnvio(RepositorioEnvio repositorioEnvio) {

        mBoxRepositorioEnvio.remove(repositorioEnvio);

        Log.i(TAG, "Pendencia enviada!" + repositorioEnvio.toString());

        repositorioEnvio.Enviado = "S";
        mBoxRepositorioEnvio.put(repositorioEnvio);

    }

}
