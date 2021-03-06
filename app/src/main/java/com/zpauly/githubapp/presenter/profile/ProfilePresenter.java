package com.zpauly.githubapp.presenter.profile;

import android.content.Context;

import com.zpauly.githubapp.Constants;
import com.zpauly.githubapp.base.NetPresenter;
import com.zpauly.githubapp.entity.response.AuthenticatedUserBean;
import com.zpauly.githubapp.entity.response.UserBean;
import com.zpauly.githubapp.network.user.UserMethod;
import com.zpauly.githubapp.utils.SPUtil;

import rx.Subscriber;

/**
 * Created by zpauly on 16-6-10.
 */
public class ProfilePresenter extends NetPresenter implements ProfileContract.Presenter {
    private ProfileContract.View mHomeView;
    private Context mContext;

    private UserMethod method;
    private String auth;

    private Subscriber<AuthenticatedUserBean> authenticatedUserSubscriber;
    private Subscriber<UserBean> userSubscriber;
    private Subscriber<String> followSubscriber;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        mHomeView = view;
        mContext = context;
        mHomeView.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        method = getMethod(UserMethod.class);
        auth = getAuth(mContext);
    }

    @Override
    public void stop() {
        unsubscribe(authenticatedUserSubscriber, userSubscriber, followSubscriber);
    }

    @Override
    public void loadUserInfo() {
        authenticatedUserSubscriber = new Subscriber<AuthenticatedUserBean>() {
            @Override
            public void onCompleted() {
                mHomeView.loadInfoSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mHomeView.loadInfoFail();
            }

            @Override
            public void onNext(AuthenticatedUserBean authenticatedUser) {
                mHomeView.loadInfo(authenticatedUser);
            }
        };
        method.getAuthenticatedUser(authenticatedUserSubscriber, auth);
    }

    @Override
    public void loadOtherInfo() {
        userSubscriber = new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {
                mHomeView.loadInfoSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mHomeView.loadInfoFail();
            }

            @Override
            public void onNext(UserBean bean) {
                mHomeView.loadOtherInfo(bean);
            }
        };
        method.getUser(userSubscriber, mHomeView.getUsername());
    }

    @Override
    public void checkUserFollowed() {
        followSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mHomeView.isFollowed();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (e.getMessage().contains("404")) {
                    mHomeView.isUnfollowed();
                } else {
                    mHomeView.checkFollowFail();
                }
            }

            @Override
            public void onNext(String s) {

            }
        };
        method.isUserFollowed(followSubscriber, auth, mHomeView.getUsername());
    }

    @Override
    public void follow() {
        followSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mHomeView.followSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mHomeView.followFail();
            }

            @Override
            public void onNext(String s) {

            }
        };
        method.followAUser(followSubscriber, auth, mHomeView.getUsername());
    }

    @Override
    public void unfollow() {
        followSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mHomeView.unfollowSuccess();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mHomeView.unfollowFail();
            }

            @Override
            public void onNext(String s) {

            }
        };
        method.unfollowAUser(followSubscriber, auth, mHomeView.getUsername());
    }
}
