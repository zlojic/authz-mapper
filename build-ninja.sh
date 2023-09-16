#!/bin/sh


rm *.run  2> /dev/null
./gradlew clean shadowJar

printf '#!/bin/sh\n\n'              >> authz-mapper.run
printf 'exec java -jar "$0" "$@"\n' >> authz-mapper.run
printf 'exit 1\n\n'                 >> authz-mapper.run

cat ./build/libs/*.jar              >> authz-mapper.run
chmod +x authz-mapper.run
