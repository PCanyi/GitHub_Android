package com.zpauly.githubapp.network.pullRequests;

import com.zpauly.githubapp.entity.response.CommentBean;
import com.zpauly.githubapp.entity.response.PullRequestBean;
import com.zpauly.githubapp.entity.response.repos.FileBean;
import com.zpauly.githubapp.entity.response.repos.SingleCommitBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zpauly on 2016/10/22.
 */

public interface PullRequestsService {
    String OPEN = "open";

    String CLOSED = "closed";

    String ALL = "all";

    String CREATED = "created";

    String UPDATED = "updated";

    String POPULARITY = "popularity";

    String LONG_RUNNING = "long-running";

    String DESC = "desc";

    String ASC = "asc";

    @GET("/repos/{owner}/{repo}/pulls")
    Observable<List<PullRequestBean>> getPullRequests(@Header("Authorzation") String auth,
                                                      @Header("Accept") String acc,
                                                      @Path("owner") String owner,
                                                      @Path("repo") String repo,
                                                      @Query("state") String state,
                                                      @Query("head") String head,
                                                      @Query("base") String base,
                                                      @Query("sort") String sort,
                                                      @Query("direction") String direction,
                                                      @Query("page") int pageId);

    /**
     * List comments on a pull request
     * @param auth
     * @param owner
     * @param repo
     * @param number
     * @return
     */
    @GET("/repos/{owner}/{repo}/pulls/{number}/comments")
    Observable<List<CommentBean>> getAPullComments(@Header("Authorization") String auth,
                                                   @Header("Accept") String acc,
                                                   @Path("owner") String owner,
                                                   @Path("repo") String repo,
                                                   @Path("number") int number,
                                                   @Query("page") int pageId);

    /**
     * List commits on a pull request
     * @param auth
     * @param owner
     * @param repo
     * @param number
     * @return
     */
    @GET("/repos/{owner}/{repo}/pulls/{number}/commits")
    Observable<List<SingleCommitBean>> getAPullCommits(@Header("Authorization") String auth,
                                                       @Path("owner") String owner,
                                                       @Path("repo") String repo,
                                                       @Path("number") int number,
                                                       @Query("page") int pageId);

    /**
     * List pull requests files
     * @param auth
     * @param owner
     * @param repo
     * @param number
     * @param pageId
     * @return
     */
    @GET("/repos/{owner}/{repo}/pulls/{number}/files")
    Observable<List<FileBean>> getAPullFiles(@Header("Authorization") String auth,
                                             @Path("owner") String owner,
                                             @Path("repo") String repo,
                                             @Path("number") int number,
                                             @Query("page") int pageId);
}
