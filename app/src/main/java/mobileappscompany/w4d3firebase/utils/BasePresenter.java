package mobileappscompany.w4d3firebase.utils;

/**
 * Created by fallaye on 12/20/17.
 */

public interface BasePresenter <V extends BaseView>{

    void attachView(V view);
    void detachView();
}
