/*
 * Copyright 2016 the original author or authors.
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

package org.gradle.internal.fingerprint.impl;

import org.gradle.internal.file.FileType;
import org.gradle.internal.hash.HashCode;
import org.gradle.internal.snapshot.PhysicalSnapshot;

public class DefaultFileFingerprint extends AbstractFileFingerprint {
    private final String normalizedPath;

    public DefaultFileFingerprint(String normalizedPath, FileType type, HashCode contentHash) {
        super(type, hashForType(type, contentHash));
        this.normalizedPath = normalizedPath;
    }

    public DefaultFileFingerprint(String normalizedPath, PhysicalSnapshot snapshot) {
        this(normalizedPath, snapshot.getType(), snapshot.getHash());
    }

    private static HashCode hashForType(FileType fileType, HashCode hash) {
        switch (fileType) {
            case Directory:
                return DIR_SIGNATURE;
            case Missing:
                return MISSING_FILE_SIGNATURE;
            case RegularFile:
                return hash;
            default:
                throw new IllegalStateException("Unknown file type: " + fileType);
        }
    }

    @Override
    public String getNormalizedPath() {
        return normalizedPath;
    }
}
