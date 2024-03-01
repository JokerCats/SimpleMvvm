package net.jkcats.simplemvvm.basics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import net.jkcats.simplemvvm.utils.logger.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class StandardFragment<V extends ViewBinding> extends Fragment {

    private static final String TAG = StandardFragment.class.getSimpleName();

    protected @Nullable V mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = initBinding(inflater, container, false);
        if (mBinding != null) {
            return mBinding.getRoot();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    private Class<V> getBindingClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<V>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    private V initBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, boolean attachToRoot) {
        try {
            Class<V> vClass = getBindingClass();
            if (vClass != null) {
                Method method = vClass.getMethod(
                        "inflate",
                        LayoutInflater.class, ViewGroup.class, boolean.class
                );
                return (V) method.invoke(null, inflater, container, attachToRoot);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            Logger.error(e.getMessage());
        }
        return null;
    }
}
