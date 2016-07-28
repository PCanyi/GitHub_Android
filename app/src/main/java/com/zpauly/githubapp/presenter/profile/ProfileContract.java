package com.zpauly.githubapp.presenter.profile;

import com.zpauly.githubapp.base.BasePresenter;
import com.zpauly.githubapp.base.BaseView;
import com.zpauly.githubapp.entity.response.AuthenticatedUserBean;
import com.zpauly.githubapp.entity.response.UserBean;

/**
 * Created by zpauly on 16-6-10.
 */
public class ProfileContract {
    public interface Presenter extends BasePresenter {
        void loadUserInfo();

        void loadOtherInfo();
    }

    public interface View extends BaseView<Presenter> {
        void loadInfoSuccess();

        void loadInfoFail();

        void loadInfo(AuthenticatedUserBean user);

        void loadOtherInfo(UserBean user);

        String getUsername();
    }
}
