package net.jkcats.simplemvvm.network.api;

import net.jkcats.simplemvvm.models.HomeModel;
import net.jkcats.simplemvvm.models.UpdateInfo;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestService {

    @GET("banner/json")
    Call<HomeModel> getHomeData();

    @GET("banner/json")
    Observable<ResponseDataRx<ArrayList<HomeModel.HomeData>>> getBannerData();

    @GET("checkForUpdate")
    Observable<ResponseDataRx<UpdateInfo>> checkForUpdate();
}
