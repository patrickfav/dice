# Dice

A simple tool that generates random text encoded byte arrays from the best random source from your machine* and encodes them in various formats: normal byte encodings like hex or base64 and for many programming languages.

 [![GitHub release](https://img.shields.io/github/release/patrickfav/dice.svg)](https://github.com/patrickfav/dice/releases/latest)
[![Build Status](https://travis-ci.org/patrickfav/dice.svg?branch=master)](https://travis-ci.org/patrickfav/dice)
[![Coverage Status](https://coveralls.io/repos/github/patrickfav/dice/badge.svg?branch=master)](https://coveralls.io/github/patrickfav/dice?branch=master)

<sup>*as promised by the contract of Java's `SecureRandom`</sup>

Main features:

 * Supports all common byte encodings and more (hex, base32, base36, base64, base85, etc.)
 * Automatic secure seeding of random generator with [random.org](https://www.random.org/)
 * Generates code for random byte arrays for many programming languages (java, c, c#, kotlin, phyton, swifth, go,...)
 * Entropy warnings if seed is weak
 * Additional output configuration like "www-form-urlencoding" and padding of output

Example usage generating randoms with 24 byte length and default encoding:

    java -jar dice.jar 24

More examples:
    
    java -jar dice.jar 16 --count 100
    java -jar dice.jar 16 --encoding "base64"
    java -jar dice.jar 32 --encoding "base85" --urlencode
    java -jar dice.jar 16 --encoding "kotlin"
    java -jar dice.jar 16 --seed "myBadRandomSeed"
    java -jar dice.jar 16 --offline

This should run on any Windows, Mac or Linux machine,

### Requirements

* JDK 8

## Download

**[Grab jar from latest Release](https://github.com/patrickfav/uber-adb-tools/releases/latest)**

### Using the *.exe Launcher

 ![logo](misc/icon_sm.png)

 [Launch4J](http://launch4j.sourceforge.net/) is used to wrap the `.jar` into an Windows executable. It should automatically download the needed JRE if required.

## Tl;dr: How should I use it?

Depends. Either you want manually create tokens or nounces, then I'll recommend base32 or base36 if it needs to be url safe and base64 if not. 16 byte usually suffice for globally unique, very hard to guess randoms.

    java -jar dice.jar 16 -e "base36"

Or you want to create static salts, or randoms to harcode, then just use:

    java -jar dice.jar 16 -e "java"

## Demo

## Command Line Interface

    -c,--count <number>      How many randoms should be generated. Automatically chosen if this argument is omitted.
    -d,--debug               Prints additional info for debugging.
    -e,--encoding <string>   Output byte-to-text encoding. Available encodings include:
                             binary, octal, dec, base16, BASE16, base32, base36, base64,
                             base64-url, base85, c, c#, java, go, kotlin, node, php, python3, swift, utf8
    -h,--help                Shows this page.
    -o,--offline             Skips request to Random.org to seed random generator (use when offline).
    -s,--seed <string>       Uses the utf-8 byte representation of given parameter to seed the internal random
                             generator. Warns if entropy is low.
    -u,--urlencode           Uses 'www-form-urlencoded' encoding scheme, also misleadingly known as URL encoding, on the
                             output strings
    -v,--version             Prints application version.

## Supported Encodings

### Byte-to-Text Encodings

| Name | Example | Efficiency | Padding | Description |
| ------------- | ------------- | -------------: | :-------------: | ------------- |
| binary       | `11010000 00111010 01001010 11101110 01100100 00010001` | 12.5 % | false | A simple binary representation with '0' and '1' divided into 8 bit groups. |
| octal        | `426235622435320`    | 25.0 % | false | The octal numeral system, is the base-8 number system, and uses the digits 0 to 7. |
| dec          | `19125192243920`     | 33.0 % | false | Decimal positive sign-magnitude representation representation in big-endian byte-order. |
| base16       | `1164ee4a3ad0`       | 50.0 % | false | Base16 or hex stores each byte as a pair of hexadecimal digits. Lowercase (a-f) letters are used for digits greater than 9. |
| BASE16       | `1164EE4A3AD0`       | 50.0 % | false | Base16 or hex stores each byte as a pair of hexadecimal digits. Uppercase (A-F) letters are used for digits greater than 9. |
| base32       | `CFSO4SR22A`         | 62.5 % | true | Base32 uses a 32-character subset of the twenty-six letters A-Z and ten digits 0-9. Uses the alphabet defined in RFC 4648. |
| base36       | `6s1zpnwsw`          | 65.0 % | true | Base36 translating into a radix-36 (aka Hexatrigesimal) representation. |
| base64       | `EWTuSjrQ`           | 75.0 % | true | Base64 represent binary data in an ASCII string format by translating it into a radix-64 representation. |
| base64-url   | `EWTuSjrQ`           | 75.0 % | true | Base64 represent binary data in an ASCII string format by translating it into a radix-64 representation.. Uses url safe mode |
| base85      | `&S1<%3m[`           | 80.0 % | true | Base85 uses an 85 character ASCII alphabet to encode. It's main use is with the PDF format and GIT. |
| utf8         | `d�J:�`             | 100.0 % | false | UTF-8 is a compromise character encoding that can be as compact as ASCII (if the file is just plain English text) but can also contain any unicode characters (with some increase in file size). |

### Programming Languages

| Name | Example |
| :-------------: | ------------- |
| c            | `{0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11};` |
| c#           | `new byte[]{0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11};` |
| java         | `new byte[]{(byte) 0xD0, 0x3A, 0x4A, (byte) 0xEE, 0x64, 0x11};` |
| go           | `[...]byte = {0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11}` |
| kotlin       | `byteArrayOf(0xD0.toByte(), 0x3A, 0x4A, 0xEE.toByte(), 0x64, 0x11)` |
| node         | `new Buffer([0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11]);` |
| php          | `array(208, 58, 74, 238, 100, 17);` |
| python3      | `bytes([0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11])` |
| swift        | `[UInt8] = [0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11]` |

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
