package net.jkcats.simplemvvm.network;

public interface ResponseBlock<T> {

    ResponseData<T> execute();
}
