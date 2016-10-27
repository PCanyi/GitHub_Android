package com.zpauly.githubapp.view.repositories;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zpauly.githubapp.R;
import com.zpauly.githubapp.adapter.ReposRecyclerViewAdapter;
import com.zpauly.githubapp.base.BaseFragment;
import com.zpauly.githubapp.db.ReposDao;
import com.zpauly.githubapp.db.ReposModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 16-7-18.
 */

public class ReposFragment extends BaseFragment {
    private final String TAG = getClass().getName();

    public static final String FRAGMENT_TAG = "FRAGMENT_TAG";

    public static final int ALL = 0;
    public static final int PUBLIC = 1;
    public static final int PRIVATE = 2;
    public static final int SOURCE = 3;
    public static final int FORK = 4;

    private int fragmentTag;

    private RecyclerView mReposRV;
    private ReposRecyclerViewAdapter mReposRVAdapter;

    @Override
    protected void initViews(View view) {
        fragmentTag = getArguments().getInt(FRAGMENT_TAG);

        mReposRV = (RecyclerView) view.findViewById(R.id.repos_RV);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mReposRVAdapter = new ReposRecyclerViewAdapter(getContext());
        mReposRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mReposRV.setAdapter(mReposRVAdapter);
        mReposRVAdapter.setHasLoading(false);

        loadData();
    }

    private void loadData() {
        Observable.create(new Observable.OnSubscribe<List<ReposModel>>() {
            @Override
            public void call(Subscriber<? super List<ReposModel>> subscriber) {
                List<ReposModel> list = new ArrayList<>();
                switch (fragmentTag) {
                    case ALL:
                        list = ReposDao.queryRepos();
                        break;
                    case PUBLIC:
                        list = ReposDao.queryRepos("privatex", String.valueOf(0));
                        break;
                    case PRIVATE:
                        list = ReposDao.queryRepos("privatex", String.valueOf(1));
                        break;
                    case SOURCE:
                        list = ReposDao.queryRepos("fork", String.valueOf(0));
                        break;
                    case FORK:
                        list = ReposDao.queryRepos("fork", String.valueOf(1));
                        break;
                    default:
                        break;
                }
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ReposModel>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ReposModel> reposModels) {
                        mReposRVAdapter.addAllData(reposModels);
                    }
                });
    }

    @Override
    protected View setContentView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_repos, container, false);
    }
}
