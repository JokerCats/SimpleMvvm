package net.jkcats.simplemvvm.basics;

import net.jkcats.simplemvvm.basics.rx.BaseObserver;
import net.jkcats.simplemvvm.network.RequestConfig;
import net.jkcats.simplemvvm.network.RequestHelper;
import net.jkcats.simplemvvm.network.RequestManifest;
import net.jkcats.simplemvvm.network.ResponseBlock;
import net.jkcats.simplemvvm.network.ResponseData;
import net.jkcats.simplemvvm.network.ResponseState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseRepository {

    protected void executeSP() {

    }

    protected void executeDB() {

    }

    protected void executeFiles() {

    }

    protected <T> ResponseData<T> executeServer(ResponseBlock<T> block) {
        ResponseData<T> data = block.execute();
        if (data.code == RequestConfig.ERROR_CODE) {
            data.state = ResponseState.Failure;
        } else {
            data.state = ResponseState.Success;
        }
        return data;
    }

    // ########################### support rxJava ##########################
    public RequestManifest mRetrofit;

    /**
     * 当前 BaseRepository 含有空参构造函数，mBaseViewModel可能不会被赋值
     * 使用时请注意判空
     */
    public BaseViewModel mBaseViewModel;

    public BaseRepository() {
        initRetrofit();
    }

    public <VM extends BaseViewModel> BaseRepository(VM viewModel) {
        initRetrofit();
        this.mBaseViewModel = viewModel;
    }

    private void initRetrofit() {
        this.mRetrofit = RequestHelper.getInstance().getService();
    }

    /**
     * 执行网络请求并处理结果。
     *
     * @param <T>        响应数据的类型
     * @param observable 用于发送网络请求的 Observable
     * @param observer   用于接收并处理响应结果的 Observer
     */
    public <T> void requestApi(Observable<ResponseData<T>> observable, BaseObserver<T> observer) {
        observable.observeOn(AndroidSchedulers.mainThread()) // 将观察者切换到主线程，以便进行 UI 更新
                .subscribeOn(Schedulers.io())                // 将订阅切换到 IO 线程，以便执行网络请求
                .subscribe(observer);                        // 订阅 Observable，并由指定的 Observer 处理结果
    }
}
