#!/bin/bash

if [ ! -z "$FULL_TAGS" ]; then
    sed -i "s/<cucumber.options>.*</<cucumber.options>$FULL_TAGS</" pom.xml
fi

mvn clean verify \
    -Dheadless=true \
    -Dskip.unit.tests=true \
    -Denvironment=${ENVIRONMENT} \
    ${SUB_URL} \
    ${ADMIN_URL} \
    -Ddevice=${DEVICE:-chrome} \
    -Dgtags=${TAGS} \
    -Dthreads=${THREADS:-1} \
    -Dfail.fast \
    -Dmaven.test.failure.ignore=false \
    -Ddefault.wait=${DEFAULT_WAIT:-15} \
    ${HUB} \
    ${OTHER}

export RESULT=$?
cp -r target/ test-output/

exit $RESULT