#!/bin/bash

echo "Checking build..."
mvn compile

exit_code=$?
if [ ${exit_code} -ne 0 ]; then
  echo "Build failed."
  exit ${exit_code}
fi

echo "Build succeeded."

echo "Running tests..."
mvn test
