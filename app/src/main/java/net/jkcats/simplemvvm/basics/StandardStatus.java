package net.jkcats.simplemvvm.basics;

public interface StandardStatus {

    void showEmptyView();

    void showErrorView(String errorMsg);

    void showLoadingView(Boolean enabled);
}
