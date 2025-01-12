/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Referred from Apache Spark's connector/expressions implementation
// sql/catalyst/src/main/java/org/apache/spark/sql/connector/expressions/Expression.java
package com.datastrato.gravitino.rel.expressions;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/** Base class of the public logical expression API. */
public interface Expression {

  /**
   * `EMPTY_EXPRESSION` is only used as an input when the default `children` method builds the
   * result.
   */
  Expression[] EMPTY_EXPRESSION = new Expression[0];

  /**
   * `EMPTY_NAMED_REFERENCE` is only used as an input when the default `references` method builds
   * the result array to avoid repeatedly allocating an empty array.
   */
  NamedReference[] EMPTY_NAMED_REFERENCE = new NamedReference[0];

  /** @return An array of the children of this node. Children should not change. */
  Expression[] children();

  /** @return A list of fields or columns that are referenced by this expression. */
  default NamedReference[] references() {
    Set<NamedReference> set = new HashSet<>();
    for (Expression e : children()) {
      Collections.addAll(set, e.references());
    }
    return set.toArray(EMPTY_NAMED_REFERENCE);
  }
}
