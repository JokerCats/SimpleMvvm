package net.jkcats.simplemvvm.repositories;

import net.jkcats.simplemvvm.basics.BaseRepository;
import net.jkcats.simplemvvm.basics.rx.BaseObserver;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.RequestHelper;
import net.jkcats.simplemvvm.network.ResponseData;
import net.jkcats.simplemvvm.viewmodels.HomeViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeRepository extends BaseRepository {

    public ResponseData<List<HomeModel>> getHomeData() {
        return executeServer(() -> {
            Call<ResponseData<List<HomeModel>>> call = RequestHelper.getInstance().getService().getHomeData();
            try {
                Response<ResponseData<List<HomeModel>>> response = call.execute();
                return response.body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // ############## support rxJava ##############

    private final HomeViewModel mCurrentViewModel;

    public HomeRepository(HomeViewModel viewModel) {
        super(viewModel);
        this.mCurrentViewModel = viewModel;
    }

    public void requestHomeData() {
        requestApi(mRetrofit.getHomeData2(), new BaseObserver<List<HomeModel>>(mCurrentViewModel) {
            @Override
            protected void onSuccess(List<HomeModel> response) {
                mCurrentViewModel.homeData.postValue(response);
            }
        });
    }
}
