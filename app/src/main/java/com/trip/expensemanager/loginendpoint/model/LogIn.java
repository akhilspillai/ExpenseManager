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
 * on 2015-03-18 at 15:37:05 UTC 
 * Modify at your own risk.
 */

package com.trip.expensemanager.loginendpoint.model;

/**
 * Model definition for LogIn.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the loginendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class LogIn extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> deviceIDs;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String prefferedName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String purchaseId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.util.List<java.lang.Long> tripIDs;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String username;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getDeviceIDs() {
    return deviceIDs;
  }

  /**
   * @param deviceIDs deviceIDs or {@code null} for none
   */
  public LogIn setDeviceIDs(java.util.List<java.lang.Long> deviceIDs) {
    this.deviceIDs = deviceIDs;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public LogIn setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public LogIn setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPrefferedName() {
    return prefferedName;
  }

  /**
   * @param prefferedName prefferedName or {@code null} for none
   */
  public LogIn setPrefferedName(java.lang.String prefferedName) {
    this.prefferedName = prefferedName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPurchaseId() {
    return purchaseId;
  }

  /**
   * @param purchaseId purchaseId or {@code null} for none
   */
  public LogIn setPurchaseId(java.lang.String purchaseId) {
    this.purchaseId = purchaseId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.Long> getTripIDs() {
    return tripIDs;
  }

  /**
   * @param tripIDs tripIDs or {@code null} for none
   */
  public LogIn setTripIDs(java.util.List<java.lang.Long> tripIDs) {
    this.tripIDs = tripIDs;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUsername() {
    return username;
  }

  /**
   * @param username username or {@code null} for none
   */
  public LogIn setUsername(java.lang.String username) {
    this.username = username;
    return this;
  }

  @Override
  public LogIn set(String fieldName, Object value) {
    return (LogIn) super.set(fieldName, value);
  }

  @Override
  public LogIn clone() {
    return (LogIn) super.clone();
  }

}
