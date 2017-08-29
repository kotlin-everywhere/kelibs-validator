#!/bin/sh

echo "rootProject.name='kelibs-validator'\ninclude 'kelibs-validator-java', 'kelibs-validator-js'" > settings.gradle
touch build.gradle
kelibs-validator-java/gradlew wrapper
