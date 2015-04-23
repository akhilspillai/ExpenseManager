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

package com.trip.expensemanager.backend.distributionendpoint.model;

/**
 * Model definition for Distribution.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the distributionendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Distribution extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String amount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long changerId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime creationDate;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long fromId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String paid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long toId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long tripId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAmount() {
    return amount;
  }

  /**
   * @param amount amount or {@code null} for none
   */
  public Distribution setAmount(java.lang.String amount) {
    this.amount = amount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getChangerId() {
    return changerId;
  }

  /**
   * @param changerId changerId or {@code null} for none
   */
  public Distribution setChangerId(java.lang.Long changerId) {
    this.changerId = changerId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getCreationDate() {
    return creationDate;
  }

  /**
   * @param creationDate creationDate or {@code null} for none
   */
  public Distribution setCreationDate(com.google.api.client.util.DateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getFromId() {
    return fromId;
  }

  /**
   * @param fromId fromId or {@code null} for none
   */
  public Distribution setFromId(java.lang.Long fromId) {
    this.fromId = fromId;
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
  public Distribution setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPaid() {
    return paid;
  }

  /**
   * @param paid paid or {@code null} for none
   */
  public Distribution setPaid(java.lang.String paid) {
    this.paid = paid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getToId() {
    return toId;
  }

  /**
   * @param toId toId or {@code null} for none
   */
  public Distribution setToId(java.lang.Long toId) {
    this.toId = toId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getTripId() {
    return tripId;
  }

  /**
   * @param tripId tripId or {@code null} for none
   */
  public Distribution setTripId(java.lang.Long tripId) {
    this.tripId = tripId;
    return this;
  }

  @Override
  public Distribution set(String fieldName, Object value) {
    return (Distribution) super.set(fieldName, value);
  }

  @Override
  public Distribution clone() {
    return (Distribution) super.clone();
  }

}