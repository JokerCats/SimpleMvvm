package net.jkcats.simplemvvm.network;

import net.jkcats.simplemvvm.models.HomeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestService {

    @GET("banner/json")
    Call<ResponseData<List<HomeModel>>> getHomeData();
}
