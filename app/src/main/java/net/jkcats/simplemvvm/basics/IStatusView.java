package net.jkcats.simplemvvm.basics;

public interface IStatusView {

    void showEmptyPage();

    void showErrorPage(String errorMsg);

    void showLoadingView(Boolean enabled);
}
