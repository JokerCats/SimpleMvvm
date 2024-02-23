package net.jkcats.simplemvvm.network.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({ResponseState.FAILURE, ResponseState.SUCCESS})
@Retention(RetentionPolicy.SOURCE)
public @interface ResponseState {
    int SUCCESS = 0;
    int FAILURE = 1;
}