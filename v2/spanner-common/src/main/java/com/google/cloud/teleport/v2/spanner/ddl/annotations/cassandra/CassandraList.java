/*
 * Copyright (C) 2025 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.cloud.teleport.v2.spanner.ddl.annotations.cassandra;

import com.google.auto.value.AutoValue;
import java.io.Serializable;

@AutoValue
public abstract class CassandraList implements Serializable {
  public abstract String elementType();

  public static Builder builder() {
    return new AutoValue_CassandraList.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setElementType(String value);

    public abstract CassandraList build();
  }
}
