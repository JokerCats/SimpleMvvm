package net.jkcats.simplemvvm.simple.upgrade

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jkcats.simplemvvm.basics.BaseViewModel
import net.jkcats.simplemvvm.models.UpdateInfo
import net.jkcats.simplemvvm.network.process.rxjava.BaseObserver
import net.jkcats.simplemvvm.network.proxy.ClientProxy
import javax.inject.Inject

@HiltViewModel
class UpgradeModel @Inject constructor() : BaseViewModel() {

    val updateInfoResponse = MutableLiveData<UpdateInfo>()

    fun checkNewVersion(versionCode: Int) {
        sendRequestWithLoading {
            mClientProxy.sendRequest(ClientProxy.mRetrofit.checkForUpdate(), object : BaseObserver<UpdateInfo>() {
                override fun onSuccess(updateInfo: UpdateInfo?) = Unit

                override fun onRequestError(msg: String?, e: Throwable?) {
                    // 模拟请求成功
                    val updateInfo = UpdateInfo(hasNewVersion = true, apkDownloadUrl = "")
                    updateInfoResponse.value = updateInfo
                }
            })
        }
    }
}