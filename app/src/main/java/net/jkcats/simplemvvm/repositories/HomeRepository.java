package net.jkcats.simplemvvm.repositories;

import net.jkcats.simplemvvm.basics.BaseRepository;
import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.network.RequestHelper;
import net.jkcats.simplemvvm.network.ResponseData;

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
}
