#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="$SCRIPT_DIR/.env"

# The system-wide JDK 21 is JRE-only (no javac); use a real JDK instead.
export JAVA_HOME=/home/alex/Downloads/jdk-21.0.5_linux-x64_bin/jdk-21.0.5
export PATH="$JAVA_HOME/bin:$PATH"

if [[ -f "$ENV_FILE" ]]; then
  set -a
  # shellcheck disable=SC1090
  source "$ENV_FILE"
  set +a
else
  echo "Missing $ENV_FILE. Copy .env.example to .env and fill in your values." >&2
  exit 1
fi

: "${SONAR_TOKEN:?SONAR_TOKEN not set in .env}"
: "${SONAR_HOST_URL:?SONAR_HOST_URL not set in .env}"
: "${SONAR_ORGANIZATION:?SONAR_ORGANIZATION not set in .env}"
: "${SONAR_PROJECT_KEY:?SONAR_PROJECT_KEY not set in .env}"

# Scope the build to the two modules that hold the included files (plus their
# dependencies via -am), instead of building the whole ~30-module reactor.
mvn install sonar:sonar \
  -pl storm-client,storm-server -am \
  -DskipTests \
  -Dcheckstyle.skip=true \
  -Dpmd.skip=true \
  -Drat.skip=true \
  -Dsonar.token="$SONAR_TOKEN" \
  -Dsonar.host.url="$SONAR_HOST_URL" \
  -Dsonar.organization="$SONAR_ORGANIZATION" \
  -Dsonar.projectKey="$SONAR_PROJECT_KEY" \
  -Dsonar.inclusions="**/Utils.java,**/NodeSorterHostProximity.java"
