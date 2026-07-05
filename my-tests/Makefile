# Pick which sonar target class to work with: CLASS=utils (default) or CLASS=resource
CLASS ?= utils

ifeq ($(CLASS),utils)
MODULE       := storm-client
TARGET_CLASS := org.apache.storm.utils.Utils
TEST_CLASS   := org.apache.storm.utils.UtilsStubTest
else ifeq ($(CLASS),resource)
MODULE       := storm-server
TARGET_CLASS := org.apache.storm.scheduler.resource.normalization.NormalizedResourceOffer
TEST_CLASS   := org.apache.storm.scheduler.resource.normalization.NormalizedResourceOfferStubTest
else
$(error Unknown CLASS '$(CLASS)'. Use CLASS=utils or CLASS=resource)
endif

TARGET_SIMPLE_NAME := $(lastword $(subst ., ,$(TARGET_CLASS)))

# Full JDK with javac (the system JRE at /usr/lib/jvm/java-21-openjdk-amd64 has no compiler)
JAVA_HOME := /home/alex/Downloads/jdk-21.0.5_linux-x64_bin/jdk-21.0.5
export JAVA_HOME
PATH := $(JAVA_HOME)/bin:$(PATH)
export PATH

# '!unix_py_tests' disables storm-client's python CLI test, which isn't a JUnit test and
# runs unconditionally in the test phase otherwise.
MVN        := mvn -f ../pom.xml -pl $(MODULE) -P '!unix_py_tests'
PIT_COMMON := test-compile org.pitest:pitest-maven:mutationCoverage \
	-DtargetClasses=$(TARGET_CLASS) \
	-DtargetTests=$(TEST_CLASS) \
	-DtestPlugin=junit5

.PHONY: help install test jacoco pit pit-debug pit-echo-command

help:
	@echo "Usage (add CLASS=utils or CLASS=resource to pick the target, default: utils):"
	@echo "  make test             - run unit tests"
	@echo "  make jacoco           - run tests with JaCoCo coverage report"
	@echo "  make pit              - run PIT mutation testing"
	@echo "  make pit-debug        - run PIT with verbose output"
	@echo "  make install          - recompile full Storm project (skip tests)"
	@echo ""
	@echo "Current selection: CLASS=$(CLASS) -> module=$(MODULE) target=$(TARGET_CLASS) test=$(TEST_CLASS)"

install:
	mvn -f ../pom.xml install -DskipTests

test:
	$(MVN) test -Dtest=$(TEST_CLASS)

jacoco:
	$(MVN) test -Dtest=$(TEST_CLASS) -Djacoco.report.includes="**/$(TARGET_SIMPLE_NAME).class"
	@echo ""
	@echo "JaCoCo report: ../$(MODULE)/target/site/jacoco/index.html"

pit-echo-command:
	@echo "Running PIT with command: $(MVN) $(PIT_COMMON)"

pit:
	$(MVN) $(PIT_COMMON)

pit-debug:
	$(MVN) -e $(PIT_COMMON) -Dverbosity=VERBOSE 2>&1 | tee /tmp/pit-debug.log
	@echo ""
	@echo "Full log saved to /tmp/pit-debug.log"
