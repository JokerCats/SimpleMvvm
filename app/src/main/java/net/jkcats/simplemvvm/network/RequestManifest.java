package net.jkcats.simplemvvm.network;

import net.jkcats.simplemvvm.models.HomeModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestManifest {

    @GET("banner/json")
    Call<ResponseData<List<HomeModel>>> getHomeData();


    @GET("banner/json")
    Observable<ResponseData<List<HomeModel>>> getHomeData2();
}
