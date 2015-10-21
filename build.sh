#!/bin/sh
rm dist/cassette.zip
ant dist
rm -rf ~/Documents/Processing/libraries/cassette/
unzip dist/cassette.zip -d ~/Documents/Processing/libraries/