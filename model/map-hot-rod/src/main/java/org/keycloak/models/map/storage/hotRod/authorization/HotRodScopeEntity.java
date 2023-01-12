/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.keycloak.models.map.storage.hotRod.authorization;

import org.infinispan.api.annotations.indexing.Basic;
import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;
import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;
import org.keycloak.models.map.annotations.GenerateHotRodEntityImplementation;
import org.keycloak.models.map.annotations.IgnoreForEntityImplementationGenerator;
import org.keycloak.models.map.authorization.entity.MapScopeEntity;
import org.keycloak.models.map.storage.hotRod.common.AbstractHotRodEntity;
import org.keycloak.models.map.storage.hotRod.common.CommonPrimitivesProtoSchemaInitializer;
import org.keycloak.models.map.storage.hotRod.common.UpdatableHotRodEntityDelegateImpl;

import java.util.Objects;

@GenerateHotRodEntityImplementation(
        implementInterface = "org.keycloak.models.map.authorization.entity.MapScopeEntity",
        inherits = "org.keycloak.models.map.storage.hotRod.authorization.HotRodScopeEntity.AbstractHotRodScopeEntity",
        topLevelEntity = true,
        modelClass = "org.keycloak.authorization.model.Scope",
        cacheName = "authz"
)
@Indexed
@ProtoDoc("schema-version: " + HotRodScopeEntity.VERSION)
public class HotRodScopeEntity extends AbstractHotRodEntity {

    @IgnoreForEntityImplementationGenerator
    public static final int VERSION = 1;

    @AutoProtoSchemaBuilder(
            includeClasses = {
                    HotRodScopeEntity.class
            },
            schemaFilePath = "proto/",
            schemaPackageName = CommonPrimitivesProtoSchemaInitializer.HOT_ROD_ENTITY_PACKAGE)
    public interface HotRodScopeEntitySchema extends GeneratedSchema {
        HotRodScopeEntitySchema INSTANCE = new HotRodScopeEntitySchemaImpl();
    }


    @Basic(projectable = true)
    @ProtoField(number = 1)
    public Integer entityVersion = VERSION;

    @Basic(projectable = true, sortable = true)
    @ProtoField(number = 2)
    public String id;

    @Basic(sortable = true)
    @ProtoField(number = 3)
    public String realmId;

    @Keyword(sortable = true, normalizer = "lowercase")
    @ProtoField(number = 4)
    public String name;

    @ProtoField(number = 5)
    public String displayName;

    @ProtoField(number = 6)
    public String iconUri;

    @Basic(sortable = true)
    @ProtoField(number = 7)
    public String resourceServerId;

    public static abstract class AbstractHotRodScopeEntity extends UpdatableHotRodEntityDelegateImpl<HotRodScopeEntity> implements MapScopeEntity {

        @Override
        public String getId() {
            return getHotRodEntity().id;
        }

        @Override
        public void setId(String id) {
            HotRodScopeEntity entity = getHotRodEntity();
            if (entity.id != null) throw new IllegalStateException("Id cannot be changed");
            entity.id = id;
            entity.updated |= id != null;
        }

        @Override
        public void setName(String name) {
            HotRodScopeEntity entity = getHotRodEntity();
            entity.updated |= ! Objects.equals(entity.name, name);
            entity.name = name;
        }
    }

    @Override
    public boolean equals(Object o) {
        return HotRodScopeEntityDelegate.entityEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HotRodScopeEntityDelegate.entityHashCode(this);
    }
}
