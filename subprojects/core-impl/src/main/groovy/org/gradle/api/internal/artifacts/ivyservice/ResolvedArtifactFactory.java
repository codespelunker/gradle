/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.internal.artifacts.ivyservice;

import org.apache.ivy.core.module.descriptor.Artifact;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.internal.artifacts.DefaultResolvedArtifact;
import org.gradle.internal.Factory;

import java.io.File;

public class ResolvedArtifactFactory {
    private final CacheLockingManager lockingManager;

    public ResolvedArtifactFactory(CacheLockingManager lockingManager) {
        this.lockingManager = lockingManager;
    }

    public ResolvedArtifact create(ResolvedDependency owner, final Artifact artifact, final ArtifactResolver resolver) {
        return new DefaultResolvedArtifact(owner, artifact, new Factory<File>() {
            public File create() {
                return lockingManager.useCache(String.format("download %s", artifact), new Factory<File>() {
                    public File create() {
                        return resolver.resolve(artifact).getFile();
                    }
                });
            }
        });
    }
}
