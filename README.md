**WORK IN PROGRESS**

# Dice
A simple tool that generates random string from the best random source of your machine and encodes them in various formats: normal byte encodings like hex or base64, custom encodings like alpha-numeric strings and for many programming languages.

 [![GitHub release](https://img.shields.io/github/release/patrickfav/dice.svg)](https://github.com/patrickfav/dice/releases/latest)
[![Build Status](https://travis-ci.org/patrickfav/dice.svg?branch=master)](https://travis-ci.org/patrickfav/dice)
[![Coverage Status](https://coveralls.io/repos/github/patrickfav/dice/badge.svg?branch=master)](https://coveralls.io/github/patrickfav/dice?branch=master)

Main features:


Basic usage:

    java -jar dice.jar 32

More features:
    
    java -jar dice.jar 16 --count 100
    java -jar dice.jar 16 --encoding "base32"
    java -jar dice.jar 16 --encoding "base85" --urlencode
    java -jar dice.jar 24 --seed "myBadRandomSeed"

This should run on any Windows, Mac or Linux machine,

### Requirements

* JDK 8

## Download

**[Grab jar from latest Release](https://github.com/patrickfav/uber-adb-tools/releases/latest)**

### Using the *.exe Launcher

 ![logo](misc/icon_sm.png)

 [Launch4J](http://launch4j.sourceforge.net/) is used to wrap the `.jar` into an Windows executable. It should automatically download the needed JRE if required.

## Demo

## Command Line Interface


## Build

Use maven (3.1+) to create a jar including all dependencies

    mvn clean package

## Tech Stack

* Java 8
* Maven
* apache-commons-codec
* apache-commons-cli
* Retrofit2
* Proguard
* Launch4j

# Credits

* Icon made by [Maxim Basinski](https://www.flaticon.com/authors/maxim-basinski) from www.flaticon.com

# License

Copyright 2017 Patrick Favre-Bulle

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
