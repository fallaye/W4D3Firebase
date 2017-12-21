package mobileappscompany.w4d3firebase.view.login;

import mobileappscompany.w4d3firebase.utils.BasePresenter;
import mobileappscompany.w4d3firebase.utils.BaseView;

/**
 * Created by fallaye on 12/20/17.
 */

public interface LoginContract {

    interface View extends BaseView{
        void onUserCreation(boolean isCreated);
        void onUserValidation(boolean isValid);
        void isSessionValid(boolean isValid);
    }

    interface Presenter extends BasePresenter<View>{
        void validateUser(String email, String password);
        void createUser(String email, String password);
        void checkSession();
    }
}
