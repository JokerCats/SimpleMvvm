package net.jkcats.simplemvvm.features.main

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import net.jkcats.simplemvvm.basics.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class MainModel @Inject constructor() : BaseViewModel() {

    /**
     * 需要处理的数据
     */
    val updateInfoResponse = MutableLiveData<String>()

}