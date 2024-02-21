package net.jkcats.simplemvvm.repositories;

import net.jkcats.simplemvvm.basics.BaseRepository;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.ResponseData;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class HomeRepository extends BaseRepository {

    @Inject
    HomeRepository() {

    }

    public ResponseData<List<HomeModel>> getHomeData() {
        return executeServer(() -> {
            Call<ResponseData<List<HomeModel>>> call = mRequestService.getHomeData();
            try {
                Response<ResponseData<List<HomeModel>>> response = call.execute();
                return response.body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
