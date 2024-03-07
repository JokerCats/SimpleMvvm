package net.jkcats.simplemvvm.network.process.rxjava;

import net.jkcats.simplemvvm.basics.BaseViewModel;
import net.jkcats.simplemvvm.network.api.ResponseDataRx;
import net.jkcats.simplemvvm.network.callback.ICallback;
import net.jkcats.simplemvvm.network.process.IClientProcess;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * BaseMvpPresenter / BaseModel
 */
public class RxJavaProcess implements IClientProcess {

    /**
     * 当前 BaseRepository 含有空参构造函数，mBaseViewModel可能不会被赋值
     * 使用时请注意判空
     */
    public BaseViewModel mBaseViewModel;

    public RxJavaProcess() {

    }

    public <VM extends BaseViewModel> RxJavaProcess(VM viewModel) {
        this.mBaseViewModel = viewModel;
    }

    @Deprecated
    @Override
    public void sendGet(String path, ICallback callback) {}

    @Deprecated
    @Override
    public void sendGet(String path, Map<String, Object> params, ICallback callback) {}

    @Deprecated
    @Override
    public void sendPost(String path, Map<String, Object> params, ICallback callback) {}


    /**
     * 使用 Rxjava + Retrofit 执行网络请求并处理返回结果。
     *
     * @param <T>        响应数据的类型
     * @param observable 用于发送网络请求的 Observable
     * @param observer   用于接收并处理响应结果的 Observer
     */
    public <T> void requestApi(Observable<ResponseDataRx<T>> observable, BaseObserver<T> observer) {
        observable.observeOn(AndroidSchedulers.mainThread()) // 将观察者切换到主线程，进行 UI 更新
                .subscribeOn(Schedulers.io())                // 将订阅切换到 IO 线程，执行网络请求
                .subscribe(observer);                        // 订阅 Observable，并由指定的 Observer 处理结果
    }
}
