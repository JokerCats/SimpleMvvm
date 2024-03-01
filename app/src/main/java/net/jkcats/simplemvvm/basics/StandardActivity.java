package net.jkcats.simplemvvm.basics;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import net.jkcats.simplemvvm.utils.logger.Logger;
import net.jkcats.simplemvvm.widgets.LoadingDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class StandardActivity<V extends ViewBinding, VM extends BaseViewModel> extends BaseActivity implements StandardStatus {

    protected @Nullable V mBinding;
    protected @Nullable VM mViewModel;

    private final LoadingDialog mLoadingDialog = new LoadingDialog();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = initBinding(getLayoutInflater());
        mViewModel = initViewModel();

        launchMonitor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mViewModel = null;
    }

    private Class<V> getBindingClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<V>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    private Class<VM> getViewModelClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<VM>) parameterizedType.getActualTypeArguments()[1];
        }
        return null;
    }

    private void launchMonitor() {
        if (mViewModel != null) {
            mViewModel.mCrashData.observe(this, this::showErrorView);
            mViewModel.mLoadingData.observe(this, this::showLoadingView);
        }
    }

    private V initBinding(@NonNull LayoutInflater inflater) {
        try {
            Class<V> vClass = getBindingClass();
            if (vClass != null) {
                Method method = vClass.getMethod(
                        "inflate",
                        LayoutInflater.class
                );
                return (V) method.invoke(null, inflater);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            Logger.error(e.getMessage());
        }
        return null;
    }

    protected VM initViewModel() {
        Class<VM> vClass = getViewModelClass();
        if (vClass != null) {
            return new ViewModelProvider(this).get(vClass);
        }
        return null;
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView(String errorMsg) {
        toast(errorMsg);
    }

    @Override
    public void showLoadingView(Boolean enabled) {
        if (mLoadingDialog != null) {
            if (enabled) {
                mLoadingDialog.show();
            } else {
                mLoadingDialog.hide();
            }
        }
    }
}
