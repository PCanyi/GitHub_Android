package com.zpauly.githubapp.view.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zpauly.githubapp.Constants;
import com.zpauly.githubapp.R;
import com.zpauly.githubapp.entity.response.AuthenticatedUserBean;
import com.zpauly.githubapp.entity.response.UserBean;
import com.zpauly.githubapp.presenter.profile.ProfileContract;
import com.zpauly.githubapp.presenter.profile.ProfilePresenter;
import com.zpauly.githubapp.ui.RefreshView;
import com.zpauly.githubapp.utils.ImageUtil;
import com.zpauly.githubapp.utils.SPUtil;
import com.zpauly.githubapp.utils.TextUtil;
import com.zpauly.githubapp.view.ToolbarActivity;
import com.zpauly.githubapp.view.events.EventsActivity;
import com.zpauly.githubapp.view.repositories.ReposActivity;
import com.zpauly.githubapp.view.users.UsersActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zpauly on 16-7-30.
 */

public class ProfileActivity extends ToolbarActivity implements ProfileContract.View {
    private final String TAG = getClass().getName();

    private ProfileContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private CircleImageView mAvatarIV;
    private TextView mLoginTV;
    private TextView mNameTV;
    private TextView mBioTV;
    private TextView mLocationTV;
    private TextView mEmailTV;
    private TextView mJoinTimeTV;
    private TextView mFollowersTV;
    private TextView mFollowingTV;
    private TextView mCompanyTV;
    private TextView mBlogTV;
    private ImageView mLocationDividerIV;
    private ImageView mEmailDividerIV;
    private ImageView mTimeDividerIV;
    private ImageView mCompanyDividerIV;
    private ImageView mBlogDividerTV;
    private LinearLayout mLocationLayout;
    private LinearLayout mEmailLayout;
    private LinearLayout mTimeLayout;
    private LinearLayout mCompanyLayout;
    private LinearLayout mBlogLayout;
    private LinearLayout mFollowersLayout;
    private LinearLayout mFollowingLayout;
    private RelativeLayout mEventsLayout;
    private RelativeLayout mReposLayout;
    private RelativeLayout mOrgsLayout;

    private RefreshView mRefreshView;

    private AuthenticatedUserBean userBean;

    @Override
    public void initViews() {
        setContent(R.layout.fragment_profile);

        new ProfilePresenter(this, this);

        mRefreshView = (RefreshView) findViewById(R.id.profile_RefreshView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.profile_SRLayout);

        mAvatarIV = (CircleImageView) findViewById(R.id.profile_avatar);
        mLoginTV = (TextView) findViewById(R.id.profile_login_TV);
        mLoginTV.setText("");
        mNameTV = (TextView) findViewById(R.id.profile_name_TV);
        mNameTV.setText("");
        mBioTV = (TextView) findViewById(R.id.profile_bio_TV);
        mBioTV.setText("");
        mLocationTV = (TextView) findViewById(R.id.profile_location_TV);
        mLocationTV.setText("");
        mEmailTV = (TextView) findViewById(R.id.profile_email_TV);
        mEmailTV.setText("");
        mJoinTimeTV = (TextView) findViewById(R.id.profile_join_time_TV);
        mJoinTimeTV.setText("");
        mCompanyTV = (TextView) findViewById(R.id.profile_company_TV);
        mCompanyTV.setText("");
        mBlogTV = (TextView) findViewById(R.id.profile_blog_TV);
        mBlogTV.setText("");
        mLocationLayout = (LinearLayout) findViewById(R.id.profile_location_layout);
        mEmailLayout = (LinearLayout) findViewById(R.id.profile_email_layout);
        mTimeLayout = (LinearLayout) findViewById(R.id.profile_time_layout);
        mCompanyLayout = (LinearLayout) findViewById(R.id.profile_company_layout);
        mBlogLayout = (LinearLayout) findViewById(R.id.profile_blog_layout);
        mLocationDividerIV = (ImageView) findViewById(R.id.profile_company_divider_IV);
        mEmailDividerIV = (ImageView) findViewById(R.id.profile_email_divider_IV);
        mTimeDividerIV = (ImageView) findViewById(R.id.profile_time_divider_IV);
        mCompanyDividerIV = (ImageView) findViewById(R.id.profile_company_divider_IV);
        mBlogDividerTV = (ImageView) findViewById(R.id.profile_blog_divider_IV);
        mFollowersTV = (TextView) findViewById(R.id.profile_followers_TV);
        mFollowingTV = (TextView) findViewById(R.id.profile_following_TV);
        mFollowersLayout = (LinearLayout) findViewById(R.id.profile_followers_layout);
        mFollowingLayout = (LinearLayout) findViewById(R.id.profile_following_layout);
        mEventsLayout = (RelativeLayout) findViewById(R.id.profile_events_layout);
        mReposLayout = (RelativeLayout) findViewById(R.id.profile_repos_layout);
        mOrgsLayout = (RelativeLayout) findViewById(R.id.profile_orgs_layout);

        setUserInfo();

        setupSwpieRefreshLayout();

        mRefreshView.setOnRefreshStateListener(new RefreshView.OnRefreshStateListener() {
            @Override
            public void beforeRefreshing() {
                loadUserInfo();
            }

            @Override
            public void onRefreshSuccess() {
                mRefreshView.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRefreshFail() {
                mRefreshView.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.GONE);
            }
        });
        mRefreshView.startRefresh();
    }

    @Override
    protected void setToolbar() {
        super.setToolbar();
        setToolbarTitle(R.string.profile);
        setOnToolbarNavClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static void launchActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    private void setUserInfo() {
        if (userBean == null)
            return;
        ImageUtil.loadAvatarImageFromUrl(this, userBean.getAvatar_url(), mAvatarIV);
        mFollowersTV.setText(String.valueOf(userBean.getFollowers()));
        mFollowingTV.setText(String.valueOf(userBean.getFollowing()));
        mLoginTV.setText(userBean.getLogin());
        mNameTV.setText(userBean.getName());
        mBioTV.setText(userBean.getBio());
        mLocationTV.setText(userBean.getLocation());
        mEmailTV.setText(userBean.getEmail());
        mCompanyTV.setText(userBean.getCompany());
        mBlogTV.setText(userBean.getBlog());
        mJoinTimeTV.setText(TextUtil.timeConverter(userBean.getCreated_at()));
        if (userBean.getEmail() == null || userBean.getEmail().equals("")) {
            mEmailLayout.setVisibility(View.GONE);
            mEmailDividerIV.setVisibility(View.GONE);
        }
        if (userBean.getLocation() == null || userBean.getLocation().equals("")) {
            mLocationLayout.setVisibility(View.GONE);
            mLocationDividerIV.setVisibility(View.GONE);
        }
        if (userBean.getCreated_at() == null || userBean.getCreated_at().equals("")) {
            mTimeLayout.setVisibility(View.GONE);
            mTimeDividerIV.setVisibility(View.GONE);
        }
        if (userBean.getCompany() == null || userBean.getCompany().equals("")) {
            mCompanyLayout.setVisibility(View.GONE);
            mCompanyDividerIV.setVisibility(View.GONE);
        }
        if (userBean.getBlog() == null || userBean.getBlog().equals("")) {
            mBlogLayout.setVisibility(View.GONE);
            mBlogDividerTV.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        if (mPresenter != null) {
            mPresenter.stop();
        }
        super.onStop();
    }

    private void setupSwpieRefreshLayout() {
        mSwipeRefreshLayout.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUserInfo();
            }
        });
    }

    private void setClickListener() {
        mFollowersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersActivity.launchActivity(ProfileActivity.this, null, UsersActivity.FOLLOWERS);
            }
        });

        mFollowingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersActivity.launchActivity(ProfileActivity.this, null, UsersActivity.FOLLOWING);
            }
        });

        mEventsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventsActivity.launchActivity(ProfileActivity.this, EventsActivity.USER_EVENTS);
            }
        });

        mReposLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReposActivity.launchActivity(ProfileActivity.this, null);
            }
        });

        mOrgsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersActivity.launchActivity(ProfileActivity.this, null, UsersActivity.ORGS);
            }
        });
    }

    private void loadUserInfo() {
        mPresenter.loadUserInfo();
    }

    @Override
    public void loadInfoSuccess() {
        mSwipeRefreshLayout.setRefreshing(false);
        setUserInfo();
        setClickListener();
        if (!mRefreshView.isRefreshSuccess()) {
            mRefreshView.refreshSuccess();
        }
    }

    @Override
    public void loadInfoFail() {
        mSwipeRefreshLayout.setRefreshing(false);
        mRefreshView.refreshFail();
    }

    @Override
    public void loadInfo(AuthenticatedUserBean user) {
        userBean = user;
    }

    @Override
    public void loadOtherInfo(UserBean user) {

    }

    @Override
    public void checkFollowFail() {

    }

    @Override
    public void isFollowed() {

    }

    @Override
    public void isUnfollowed() {

    }

    @Override
    public void followFail() {

    }

    @Override
    public void followSuccess() {

    }

    @Override
    public void unfollowSuccess() {

    }

    @Override
    public void unfollowFail() {

    }

    @Override
    public String getUsername() {
        return SPUtil.getString(this, Constants.USER_INFO, Constants.USER_LOGIN, null);
    }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
