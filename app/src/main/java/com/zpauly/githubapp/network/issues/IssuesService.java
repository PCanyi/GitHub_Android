package com.zpauly.githubapp.network.issues;


import android.support.annotation.Nullable;

import com.zpauly.githubapp.entity.response.CommentBean;
import com.zpauly.githubapp.entity.response.issues.IssueBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zpauly on 16/9/1.
 */
public interface IssuesService {
    String FILTER_ASSIGNED = "assigned"; //Default

    String FILTER_CREATED = "created";

    String FILTER_MENTIONED = "mentioned";

    String FILTER_SUBSCRIBED = "subscribed";

    String FILTER_ALL = "all";

    String STATE_OPEN = "open";//Default

    String STATE_CLOSED = "closed";

    String STATE_ALL = "all";

    String SORT_CREATED = "created";//Default

    String SORT_UPDATED = "updated";

    String SORT_COMMENTS = "comments";

    String DIRECTION_ASC = "asc";

    String DIRECTION_DESC = "desc";//Default

    /**
     * List all issues assigned to the authenticated user across all visible repositories including owned repositories, member repositories, and organization repositories:
     * @param auth
     * @param filter
     * @param state
     * @param labels
     * @param sort
     * @param dir
     * @param since
     * @return
     */
    @GET("/issues")
    Observable<List<IssueBean>> getIssues(@Header("Authorization") String auth,
                                          @Nullable @Query("filter") String filter,
                                          @Nullable @Query("state") String state,
                                          @Nullable @Query("labels") String labels,
                                          @Nullable @Query("sort") String sort,
                                          @Nullable @Query("direction") String dir,
                                          @Nullable @Query("since") String since,
                                          @Nullable @Query("page") int pageId);

    /**
     * List all issues across owned and member repositories assigned to the authenticated user:
     * @param auth
     * @param filter
     * @param state
     * @param labels
     * @param sort
     * @param dir
     * @param since
     * @return
     */
    @GET("/user/issues")
    Observable<List<IssueBean>> getUserIssues(@Header("Authorization") String auth,
                                              @Nullable @Query("filter") String filter,
                                              @Nullable @Query("state") String state,
                                              @Nullable @Query("labels") String labels,
                                              @Nullable @Query("sort") String sort,
                                              @Nullable @Query("direction") String dir,
                                              @Nullable @Query("since") String since,
                                              @Nullable @Query("page") int pageId);

    /**
     * List all issues for a given organization assigned to the authenticated user:
     * @param auth
     * @param org
     * @param filter
     * @param state
     * @param labels
     * @param sort
     * @param dir
     * @param since
     * @return
     */
    @GET("/orgs/{org}/issues")
    Observable<List<IssueBean>> getOrgIssues(@Header("Authorization") String auth,
                                             @Path("org") String org,
                                             @Nullable @Query("filter") String filter,
                                             @Nullable @Query("state") String state,
                                             @Nullable @Query("labels") String labels,
                                             @Nullable @Query("sort") String sort,
                                             @Nullable @Query("direction") String dir,
                                             @Nullable @Query("since") String since,
                                             @Nullable @Query("page") int pageId);

    /**
     * List issues for a repository
     * @param auth
     * @param owner
     * @param repo
     * @param milestone
     * @param state DEFAULT:open
     * @param assignee
     * @param creator
     * @param mentioned
     * @param sort DEFAULT:created
     * @param direction DEFAULT:desc
     * @param since
     * @param labels
     * @return
     */
    @GET("/repos/{owner}/{repo}/issues")
    Observable<List<IssueBean>> getARepoIssues(@Header("Authorization") String auth,
                                               @Path("owner") String owner,
                                               @Path("repo") String repo,
                                               @Nullable @Query("milestone") String milestone,
                                               @Nullable @Query("state") String state,
                                               @Nullable @Query("assignee") String assignee,
                                               @Nullable @Query("creator") String creator,
                                               @Nullable @Query("mentioned") String mentioned,
                                               @Nullable @Query("sort") String sort,
                                               @Nullable @Query("direction") String direction,
                                               @Nullable @Query("since") String since,
                                               @Nullable @Query("labels") String[] labels,
                                               @Nullable @Query("page") int pageId);

    /**
     * Get a single issue
     * @param auth
     * @param owner
     * @param repo
     * @param number
     * @return
     */
    @GET("/repos/{owner}/{repo}/issues/{number}")
    Observable<IssueBean> getASingleIssue(@Header("Authorization") String auth,
                                          @Path("owner") String owner,
                                          @Path("repo") String repo,
                                          @Path("number") int number,
                                          @Nullable @Query("page") int pageId);

    /**
     * List comments on an issue
     * @param auth
     * @param owner
     * @param repo
     * @param number
     * @param since
     * @return
     */
    @GET("/repos/{owner}/{repo}/issues/{number}/comments")
    Observable<List<CommentBean>> getAnIssueComments(@Header("Authorization") String auth,
                                                     @Path("owner") String owner,
                                                     @Path("repo") String repo,
                                                     @Path("number") int number,
                                                     @Nullable @Query("since") String since,
                                                     @Nullable @Query("page") int pageId);
}