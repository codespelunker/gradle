/*
 * Copyright 2012 the original author or authors.
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

package org.gradle.api.plugins.migration.fixtures.gradle

import org.gradle.util.ConfigureUtil
import org.gradle.util.GradleVersion

class ProjectOutputBuilder {

    MutableProjectOutput build(String name = "root", File dir = new File("."), Closure c) {
        def root = new MutableProjectOutput()
        root.name = name
        root.description = "root project"
        root.projectDirectory = dir
        root.gradleVersion = GradleVersion.current().toString()
        root.path = ":"

        ConfigureUtil.configure(c, root)
    }
}
