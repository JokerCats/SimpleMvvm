package net.jkcats.simplemvvm.basics.rx;

import androidx.annotation.NonNull;

import net.jkcats.simplemvvm.basics.BaseViewModel;
import net.jkcats.simplemvvm.network.ResponseData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<ResponseData<T>> {

    public BaseViewModel mBaseViewModel;

    public <VM extends BaseViewModel> BaseObserver(VM viewModel) {
        this.mBaseViewModel = viewModel;
    }

    @Override
    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
        setViewModelLoadingStatus(true);
    }

    @Override
    public void onComplete() {
        setViewModelLoadingStatus(false);
    }

    @Override
    public void onNext(@NotNull ResponseData<T> response) {
        onSuccess(response.data);
    }

    /**
     * 处理API请求失败场景
     */
    @Override
    public void onError(@NonNull Throwable e) {
        setViewModelLoadingStatus(false);
        // 可在此处按照项目的业务逻辑处理错误信息
        String errorMessage = "";
        if (e instanceof HttpException) {
            // 处理 HTTP 状态码不等于200 时的场景
            errorMessage = "请求失败 code = " + ((HttpException) e).code();
        } else if (e instanceof IOException) {
            // 处理网络错误，（如连接超时）。
            errorMessage = "网络错误，连接超时。";
        } else {
            // 处理其他错误
            errorMessage = e.getMessage();
        }
        requestError(errorMessage, e);
        setViewModelCrashData(errorMessage);
    }

    /**
     * 请求失败
     *
     * @param msg 请求失败错误信息
     * @param e   导致请求失败的具体 Throwable 异常类型
     */
    protected void requestError(String msg, Throwable e) {

    }

    /**
     * 请求成功
     *
     * @param response 接口返回的数据
     */
    protected abstract void onSuccess(T response);

    /**
     * 设置 ViewModel 中 记录当前请求状态的变量
     *
     * @param isRequesting true  : 正在请求API
     *                     false : API请求结束（成功或失败）
     */
    private void setViewModelLoadingStatus(boolean isRequesting) {
        if (isRequesting) {
            mBaseViewModel.invokeLoading();
        } else {
            mBaseViewModel.finishLoading();
        }
    }

    /**
     * 设置 ViewModel 中 记录请求失败的 信息变量
     *
     * @param errorMessage 错误信息
     */
    private void setViewModelCrashData(String errorMessage) {
        mBaseViewModel.mCrashData.postValue(errorMessage);
    }
}
