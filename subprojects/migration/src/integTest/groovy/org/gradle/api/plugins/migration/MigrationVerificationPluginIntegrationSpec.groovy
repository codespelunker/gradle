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

package org.gradle.api.plugins.migration

import org.gradle.integtests.fixtures.AbstractIntegrationSpec
import org.gradle.integtests.fixtures.TestResources
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Rule

class MigrationVerificationPluginIntegrationSpec extends AbstractIntegrationSpec {
    @Rule TestResources testResources

    def compareArchives() {
        executer.withForkingExecuter()

        when:
        run("compare")

        then:
        def html = html("build/comparison.html")

        // Name of outcome
        html.select("h3").text() == ":jar"

        // Entry comparisons
        def rows = html.select("tr").tail().collectEntries { [it.select("td")[0].text(), it.select("td")[1].text()] }
        rows.size() == 4
        rows["org/gradle/ChangedClass.class"] == "from is 409 bytes - to is 486 bytes (+77)"
        rows["org/gradle/DifferentCrcClass.class"] == "files are same size but with different content"
        rows["org/gradle/SourceBuildOnlyClass.class"] == "from only"
        rows["org/gradle/TargetBuildOnlyClass.class"] == "to only"
    }

    Document html(path) {
        Jsoup.parse(file(path), "utf8")
    }
}
