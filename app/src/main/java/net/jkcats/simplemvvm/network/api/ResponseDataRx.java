package net.jkcats.simplemvvm.network.api;

/**
 * 响应数据的基类
 * @param <T> "data"层级对应的对象类型
 */
public class ResponseDataRx<T> extends ResponseData {

    /**
     * 响应数据对象
     */
    public T data;
}
