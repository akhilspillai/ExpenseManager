/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-01-14 17:53:03 UTC)
 * on 2015-03-18 at 15:36:51 UTC 
 * Modify at your own risk.
 */

package com.trip.expensemanager.distributionendpoint;

/**
 * Service definition for Distributionendpoint (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link DistributionendpointRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Distributionendpoint extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.18.0-rc of the distributionendpoint library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://healthy-dolphin-679.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "distributionendpoint/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Distributionendpoint(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Distributionendpoint(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "getDistribution".
   *
   * This request holds the parameters needed by the distributionendpoint server.  After setting any
   * optional parameters, call the {@link GetDistribution#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public GetDistribution getDistribution(java.lang.Long id) throws java.io.IOException {
    GetDistribution result = new GetDistribution(id);
    initialize(result);
    return result;
  }

  public class GetDistribution extends DistributionendpointRequest<com.trip.expensemanager.distributionendpoint.model.Distribution> {

    private static final String REST_PATH = "distribution/{id}";

    /**
     * Create a request for the method "getDistribution".
     *
     * This request holds the parameters needed by the the distributionendpoint server.  After setting
     * any optional parameters, call the {@link GetDistribution#execute()} method to invoke the remote
     * operation. <p> {} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected GetDistribution(java.lang.Long id) {
      super(Distributionendpoint.this, "GET", REST_PATH, null, com.trip.expensemanager.distributionendpoint.model.Distribution.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetDistribution setAlt(java.lang.String alt) {
      return (GetDistribution) super.setAlt(alt);
    }

    @Override
    public GetDistribution setFields(java.lang.String fields) {
      return (GetDistribution) super.setFields(fields);
    }

    @Override
    public GetDistribution setKey(java.lang.String key) {
      return (GetDistribution) super.setKey(key);
    }

    @Override
    public GetDistribution setOauthToken(java.lang.String oauthToken) {
      return (GetDistribution) super.setOauthToken(oauthToken);
    }

    @Override
    public GetDistribution setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetDistribution) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetDistribution setQuotaUser(java.lang.String quotaUser) {
      return (GetDistribution) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetDistribution setUserIp(java.lang.String userIp) {
      return (GetDistribution) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public GetDistribution setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public GetDistribution set(String parameterName, Object value) {
      return (GetDistribution) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "insertDistribution".
   *
   * This request holds the parameters needed by the distributionendpoint server.  After setting any
   * optional parameters, call the {@link InsertDistribution#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.trip.expensemanager.distributionendpoint.model.Distribution}
   * @return the request
   */
  public InsertDistribution insertDistribution(com.trip.expensemanager.distributionendpoint.model.Distribution content) throws java.io.IOException {
    InsertDistribution result = new InsertDistribution(content);
    initialize(result);
    return result;
  }

  public class InsertDistribution extends DistributionendpointRequest<com.trip.expensemanager.distributionendpoint.model.Distribution> {

    private static final String REST_PATH = "distribution";

    /**
     * Create a request for the method "insertDistribution".
     *
     * This request holds the parameters needed by the the distributionendpoint server.  After setting
     * any optional parameters, call the {@link InsertDistribution#execute()} method to invoke the
     * remote operation. <p> {} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link com.trip.expensemanager.distributionendpoint.model.Distribution}
     * @since 1.13
     */
    protected InsertDistribution(com.trip.expensemanager.distributionendpoint.model.Distribution content) {
      super(Distributionendpoint.this, "POST", REST_PATH, content, com.trip.expensemanager.distributionendpoint.model.Distribution.class);
    }

    @Override
    public InsertDistribution setAlt(java.lang.String alt) {
      return (InsertDistribution) super.setAlt(alt);
    }

    @Override
    public InsertDistribution setFields(java.lang.String fields) {
      return (InsertDistribution) super.setFields(fields);
    }

    @Override
    public InsertDistribution setKey(java.lang.String key) {
      return (InsertDistribution) super.setKey(key);
    }

    @Override
    public InsertDistribution setOauthToken(java.lang.String oauthToken) {
      return (InsertDistribution) super.setOauthToken(oauthToken);
    }

    @Override
    public InsertDistribution setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (InsertDistribution) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public InsertDistribution setQuotaUser(java.lang.String quotaUser) {
      return (InsertDistribution) super.setQuotaUser(quotaUser);
    }

    @Override
    public InsertDistribution setUserIp(java.lang.String userIp) {
      return (InsertDistribution) super.setUserIp(userIp);
    }

    @Override
    public InsertDistribution set(String parameterName, Object value) {
      return (InsertDistribution) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listDistribution".
   *
   * This request holds the parameters needed by the distributionendpoint server.  After setting any
   * optional parameters, call the {@link ListDistribution#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListDistribution listDistribution() throws java.io.IOException {
    ListDistribution result = new ListDistribution();
    initialize(result);
    return result;
  }

  public class ListDistribution extends DistributionendpointRequest<com.trip.expensemanager.distributionendpoint.model.CollectionResponseDistribution> {

    private static final String REST_PATH = "distribution";

    /**
     * Create a request for the method "listDistribution".
     *
     * This request holds the parameters needed by the the distributionendpoint server.  After setting
     * any optional parameters, call the {@link ListDistribution#execute()} method to invoke the
     * remote operation. <p> {} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListDistribution() {
      super(Distributionendpoint.this, "GET", REST_PATH, null, com.trip.expensemanager.distributionendpoint.model.CollectionResponseDistribution.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListDistribution setAlt(java.lang.String alt) {
      return (ListDistribution) super.setAlt(alt);
    }

    @Override
    public ListDistribution setFields(java.lang.String fields) {
      return (ListDistribution) super.setFields(fields);
    }

    @Override
    public ListDistribution setKey(java.lang.String key) {
      return (ListDistribution) super.setKey(key);
    }

    @Override
    public ListDistribution setOauthToken(java.lang.String oauthToken) {
      return (ListDistribution) super.setOauthToken(oauthToken);
    }

    @Override
    public ListDistribution setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListDistribution) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListDistribution setQuotaUser(java.lang.String quotaUser) {
      return (ListDistribution) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListDistribution setUserIp(java.lang.String userIp) {
      return (ListDistribution) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String cursor;

    /**

     */
    public java.lang.String getCursor() {
      return cursor;
    }

    public ListDistribution setCursor(java.lang.String cursor) {
      this.cursor = cursor;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Long tripId;

    /**

     */
    public java.lang.Long getTripId() {
      return tripId;
    }

    public ListDistribution setTripId(java.lang.Long tripId) {
      this.tripId = tripId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer limit;

    /**

     */
    public java.lang.Integer getLimit() {
      return limit;
    }

    public ListDistribution setLimit(java.lang.Integer limit) {
      this.limit = limit;
      return this;
    }

    @Override
    public ListDistribution set(String parameterName, Object value) {
      return (ListDistribution) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeDistribution".
   *
   * This request holds the parameters needed by the distributionendpoint server.  After setting any
   * optional parameters, call the {@link RemoveDistribution#execute()} method to invoke the remote
   * operation.
   *
   * @param id
   * @return the request
   */
  public RemoveDistribution removeDistribution(java.lang.Long id) throws java.io.IOException {
    RemoveDistribution result = new RemoveDistribution(id);
    initialize(result);
    return result;
  }

  public class RemoveDistribution extends DistributionendpointRequest<Void> {

    private static final String REST_PATH = "distribution/{id}";

    /**
     * Create a request for the method "removeDistribution".
     *
     * This request holds the parameters needed by the the distributionendpoint server.  After setting
     * any optional parameters, call the {@link RemoveDistribution#execute()} method to invoke the
     * remote operation. <p> {} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param id
     * @since 1.13
     */
    protected RemoveDistribution(java.lang.Long id) {
      super(Distributionendpoint.this, "DELETE", REST_PATH, null, Void.class);
      this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
    }

    @Override
    public RemoveDistribution setAlt(java.lang.String alt) {
      return (RemoveDistribution) super.setAlt(alt);
    }

    @Override
    public RemoveDistribution setFields(java.lang.String fields) {
      return (RemoveDistribution) super.setFields(fields);
    }

    @Override
    public RemoveDistribution setKey(java.lang.String key) {
      return (RemoveDistribution) super.setKey(key);
    }

    @Override
    public RemoveDistribution setOauthToken(java.lang.String oauthToken) {
      return (RemoveDistribution) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveDistribution setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveDistribution) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveDistribution setQuotaUser(java.lang.String quotaUser) {
      return (RemoveDistribution) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveDistribution setUserIp(java.lang.String userIp) {
      return (RemoveDistribution) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Long id;

    /**

     */
    public java.lang.Long getId() {
      return id;
    }

    public RemoveDistribution setId(java.lang.Long id) {
      this.id = id;
      return this;
    }

    @Override
    public RemoveDistribution set(String parameterName, Object value) {
      return (RemoveDistribution) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "updateDistribution".
   *
   * This request holds the parameters needed by the distributionendpoint server.  After setting any
   * optional parameters, call the {@link UpdateDistribution#execute()} method to invoke the remote
   * operation.
   *
   * @param content the {@link com.trip.expensemanager.distributionendpoint.model.Distribution}
   * @return the request
   */
  public UpdateDistribution updateDistribution(com.trip.expensemanager.distributionendpoint.model.Distribution content) throws java.io.IOException {
    UpdateDistribution result = new UpdateDistribution(content);
    initialize(result);
    return result;
  }

  public class UpdateDistribution extends DistributionendpointRequest<com.trip.expensemanager.distributionendpoint.model.Distribution> {

    private static final String REST_PATH = "distribution";

    /**
     * Create a request for the method "updateDistribution".
     *
     * This request holds the parameters needed by the the distributionendpoint server.  After setting
     * any optional parameters, call the {@link UpdateDistribution#execute()} method to invoke the
     * remote operation. <p> {} must be called to initialize this instance immediately
     * after invoking the constructor. </p>
     *
     * @param content the {@link com.trip.expensemanager.distributionendpoint.model.Distribution}
     * @since 1.13
     */
    protected UpdateDistribution(com.trip.expensemanager.distributionendpoint.model.Distribution content) {
      super(Distributionendpoint.this, "PUT", REST_PATH, content, com.trip.expensemanager.distributionendpoint.model.Distribution.class);
    }

    @Override
    public UpdateDistribution setAlt(java.lang.String alt) {
      return (UpdateDistribution) super.setAlt(alt);
    }

    @Override
    public UpdateDistribution setFields(java.lang.String fields) {
      return (UpdateDistribution) super.setFields(fields);
    }

    @Override
    public UpdateDistribution setKey(java.lang.String key) {
      return (UpdateDistribution) super.setKey(key);
    }

    @Override
    public UpdateDistribution setOauthToken(java.lang.String oauthToken) {
      return (UpdateDistribution) super.setOauthToken(oauthToken);
    }

    @Override
    public UpdateDistribution setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (UpdateDistribution) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public UpdateDistribution setQuotaUser(java.lang.String quotaUser) {
      return (UpdateDistribution) super.setQuotaUser(quotaUser);
    }

    @Override
    public UpdateDistribution setUserIp(java.lang.String userIp) {
      return (UpdateDistribution) super.setUserIp(userIp);
    }

    @Override
    public UpdateDistribution set(String parameterName, Object value) {
      return (UpdateDistribution) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Distributionendpoint}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Distributionendpoint}. */
    @Override
    public Distributionendpoint build() {
      return new Distributionendpoint(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link DistributionendpointRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setDistributionendpointRequestInitializer(
        DistributionendpointRequestInitializer distributionendpointRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(distributionendpointRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
