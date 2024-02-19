package net.jkcats.simplemvvm.basics;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import net.jkcats.simplemvvm.widgets.LoadingDialog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class StandardActivity<VM extends BaseViewModel> extends BaseActivity implements IStatusView {

    protected @Nullable VM mViewModel;

    private final LoadingDialog mLoadingDialog = new LoadingDialog();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = initViewModel();

        launchMonitor();
    }

    private void launchMonitor() {
        if (mViewModel != null) {
            mViewModel.mCrashData.observe(this, this::showErrorPage);
            mViewModel.mLoadingData.observe(this, this::showLoadingView);
        }
    }

    protected VM initViewModel() {
        Class<VM> vClass = getViewModelClass();
        if (vClass != null) {
            return new ViewModelProvider(this).get(vClass);
        }
        return null;
    }

    private Class<VM> getViewModelClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            return (Class<VM>) parameterizedType.getActualTypeArguments()[0];
        }
        return null;
    }

    @Override
    public void showEmptyPage() {

    }

    @Override
    public void showErrorPage(String errorMsg) {
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
