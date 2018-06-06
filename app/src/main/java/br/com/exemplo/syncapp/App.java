package br.com.exemplo.syncapp;

import android.app.Application;
import android.util.Log;

import br.com.exemplo.syncapp.repositorio.MyObjectBox;
import br.com.exemplo.syncapp.repositorio.RepositorioEnvio;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class App extends Application {

    public static final String TAG = "SYNC-OBJECTBOX";
    public static final boolean EXTERNAL_DIR = false;

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(this);
            Log.i(TAG, "ObjectBrowser - Started: " + started);
        }

        Log.d(TAG, "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public Box<RepositorioEnvio> getBoxRepositorioEnvio() {
        return boxStore.boxFor(RepositorioEnvio.class);
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}