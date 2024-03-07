package net.jkcats.simplemvvm.viewmodels;

import static net.jkcats.simplemvvm.network.proxy.ClientProxy.mRetrofit;

import androidx.lifecycle.MutableLiveData;

import net.jkcats.simplemvvm.basics.BaseViewModel;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.callback.ServerCallback;
import net.jkcats.simplemvvm.network.process.rxjava.BaseObserver;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {

    @Inject
    public HomeViewModel() {

    }

    public final MutableLiveData<HomeModel> homeData = new MutableLiveData<>();

    public final MutableLiveData<ArrayList<HomeModel.HomeData>> homeData2 = new MutableLiveData<>();

    public void getHomeData() {
        sendRequestWithLoading(
                () -> mClientProxy.sendGet(
                        "banner/json", new ServerCallback<HomeModel>() {
                            @Override
                            public void onCompleted(HomeModel result) {
                                homeData.postValue(result);
                            }

                            @Override
                            public void onError(String errorMsg) {
                                mCrashData.postValue(errorMsg);
                            }
                        }
                )
        );
    }

    public void getBannerData() {
        sendRequestWithLoading(() ->
                mClientProxy.sendRequest(mRetrofit.getBannerData(), new BaseObserver<ArrayList<HomeModel.HomeData>>() {
                    @Override
                    protected void onSuccess(ArrayList<HomeModel.HomeData> response) {
                        homeData2.postValue(response);
                    }
                })
        );
    }
}
