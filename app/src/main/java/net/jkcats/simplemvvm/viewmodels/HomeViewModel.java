package net.jkcats.simplemvvm.viewmodels;

import androidx.lifecycle.MutableLiveData;

import net.jkcats.simplemvvm.basics.BaseViewModel;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.ResponseData;
import net.jkcats.simplemvvm.repositories.HomeRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends BaseViewModel {

    @Inject
    public HomeViewModel() {

    }

    @Inject
    public HomeRepository mRepository;

    public final MutableLiveData<List<HomeModel>> homeData = new MutableLiveData<>();

    public void getHomeData() {
        sendRequestWithLoading(() -> {
            ResponseData<List<HomeModel>> response = mRepository.getHomeData();
            switch (response.state) {
                case Success:
                    homeData.postValue(response.data);
                    break;
                case Failure:
                    mCrashData.postValue(response.msg);
                    break;
            }
        });
    }
}
