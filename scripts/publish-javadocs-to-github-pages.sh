#! /usr/bin/env sh
set -ev

echo "HELLO WORLD"

# Get to the Travis build directory, configure git and clone the repo
cd $HOME
currentAppVersion=`mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec`
git config --global user.email "travis@travis-ci.org"
git config --global user.name "travis-ci"
git clone --quiet --branch=gh-pages https://${GITHUB_JAVADOCS_PUBLISH_TOKEN}@github.com/digital-delivery-academy/selenium-pom-framework gh-pages > /dev/null

# Commit and Push the Changes
cd gh-pages
cp -Rf $HOME/target/site/apidocs ./javadoc-${currentAppVersion}
git add -f .
git commit -m "Lastest javadoc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages for app version ${currentAppVersion}"
git push -fq origin gh-pages > /dev/null