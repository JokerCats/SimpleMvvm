package net.jkcats.simplemvvm.viewmodels;

import androidx.lifecycle.MutableLiveData;

import net.jkcats.simplemvvm.basics.BaseViewModel;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.ResponseData;
import net.jkcats.simplemvvm.repositories.HomeRepository;

import java.util.List;
import java.util.function.Function;

public class HomeViewModel extends BaseViewModel {

    private final HomeRepository mRepository = new HomeRepository();

    public final MutableLiveData<List<HomeModel>> homeData = new MutableLiveData<>();

    public void getHomeData() {
        sendRequestWithLoading(new Function<Void, Void>() {
            @Override
            public Void apply(Void unused) {
                ResponseData<List<HomeModel>> response = mRepository.getHomeData();
                switch (response.state) {
                    case Success:
                        homeData.postValue(response.data);
                        break;
                    case Failure:
                        mCrashData.postValue(response.msg);
                        break;
                }
                return null;
            }
        });
    }
}
