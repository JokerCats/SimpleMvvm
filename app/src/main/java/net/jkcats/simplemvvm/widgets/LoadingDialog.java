package net.jkcats.simplemvvm.widgets;

import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LoadingDialog extends DialogFragment {

    private static final String TAG = LoadingDialog.class.getSimpleName();

    public void show() {
        Log.i(TAG, "dialog is opened.");
    }

    public void hide() {
        Log.i(TAG, "dialog is closed.");
    }
}
