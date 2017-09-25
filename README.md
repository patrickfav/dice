# Dice

A pseudo random bit generator that generates text encoded byte arrays with entropy from the best random source from your machine* optionally externally seeded by multiple true random number generators and encodes them in various formats: normal byte encodings like hex or base64 and for many programming languages. This implementation uses the HMAC Deterministic random bit generator schema as defined in NIST SP800-90A.

 [![GitHub release](https://img.shields.io/github/release/patrickfav/dice.svg)](https://github.com/patrickfav/dice/releases/latest)
[![Build Status](https://travis-ci.org/patrickfav/dice.svg?branch=master)](https://travis-ci.org/patrickfav/dice)
[![Coverage Status](https://coveralls.io/repos/github/patrickfav/dice/badge.svg?branch=master)](https://coveralls.io/github/patrickfav/dice?branch=master)

<sup>* depending on the used provider</sup>

Main features:

 * Supports all common byte encodings and more (hex, base32, base36, base64, base85, etc.)
 * Optional automatic secure seeding of random generator with [random.org](https://www.random.org/), [Hotbits](https://www.fourmilab.ch/hotbits/) and [ANU Quantum Random Numbers Server](https://qrng.anu.edu.au/)
 * Generates code for random byte arrays for many programming languages (java, c, c#, kotlin, phyton, swifth, go,...)
 * NIST SP800-90A HMAC_DRBG (better than standard Java 8 PRNG)
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

## Tl;dr How should I use it?

Depends. Either you want manually create tokens or nonces, then I'll recommend base32 or base36 if it needs to be url safe and base64 if not. 16 byte usually suffice for globally unique, very hard to guess randoms.

    java -jar dice.jar 16 -e "base36"

Or you want to create static salts, or randoms to harcode, then just use:

    java -jar dice.jar 16 -e "java"

## Demo

## Command Line Interface

    -c,--count <number>      How many randoms should be generated. Automatically chosen if this argument is omitted.
       --crc32               If this flag is set, 4 bytes of CRC32 checksum will be appended to every random value. If
                             you need to check the integrity of the data.
    -d,--debug               Prints additional info for debugging.
    -e,--encoding <string>   Output byte-to-text encoding. Available encodings include:
                             binary, octal, dec, base16, BASE16, base26, base32, base36, base58, base64, base64-url,
                             base85, c, c#, java, go, kotlin, node, perl, php, python3, ruby, rust, swift, raw, utf8
    -f,--file <path>         Prints the random data to given file instead of the command line. Will create the file if
                             it does not exist or append the data if it does.
    -h,--help                Shows this page.
    -o,--offline             Skips request to Random.org to seed random generator (use when offline).
    -p,--padding             If this flag is set, byte-to-text output will be padded to full byte if needed.
    -r,--robot               If this flag is set, output will be more friendly for scripting (ie. no verbose text, only
                             the randoms 1 per line)
    -s,--seed <string>       Uses the utf-8 byte representation of given parameter to seed the internal random
                             generator. Warns if entropy is low.
    -u,--urlencode           Uses 'www-form-urlencoded' encoding scheme, also misleadingly known as URL encoding, on the
                             output strings
    -v,--version             Prints application version.


## Supported Encodings

### Byte-to-Text Encodings

| Name | Example | Efficiency | Padding | Description |
| ------------- | ------------- | -------------: | :-------------: | ------------- |
| binary       | `11101101 10101111 00011110 11111111 11111101 10010100 01001010` | 12.5 % | false | A simple binary representation with '0' and '1' divided into 8 bit groups. |
| octal        | `1124517677707527755` | 37.5 % | true | The octal numeral system, is the base-8 number system, and uses the digits 0 to 7. |
| dec          | `20992966904426477`  | 41.5 % | true | Decimal positive sign-magnitude representation representation in big-endian byte-order. |
| base16       | `4a94fdff1eafed`     | 50.0 % | false | Base16 or hex stores each byte as a pair of hexadecimal digits. Lowercase (a-f) letters are used for digits greater than 9. |
| BASE16       | `4A94FDFF1EAFED`     | 50.0 % | false | Base16 or hex stores each byte as a pair of hexadecimal digits. Uppercase (A-F) letters are used for digits greater than 9. |
| base26       | `FSSLZZFNQZQZ`       | 58.8 % | true | Base26 uses the twenty-six letters A-Z. |
| base32       | `JKKP37Y6V7WQ`       | 62.5 % | true | Base32 uses a 32-character subset of the twenty-six letters A-Z and ten digits 0-9. Uses the alphabet defined in RFC 4648. |
| base36       | `5qpdvuwjvu5`        | 64.6 % | true | Base36 translating into a radix-36 (aka Hexatrigesimal) representation. |
| base58       | `3pvkeHJmHN`         | 73.2 % | true | Base58 is similar to Base64 but has been modified to avoid both non-alphanumeric characters and letters which might look ambiguous when printed. This version uses the alphabet common for Bitcoin protocol. |
| base64       | `SpT9/x6v7Q`         | 75.0 % | true | Base64 represent binary data in an ASCII string format by translating it into a radix-64 representation. |
| base64-url   | `SpT9_x6v7Q`         | 75.0 % | true | Base64 represent binary data in an ASCII string format by translating it into a radix-64 representation. Uses url safe mode |
| base85       | `8sK;S*j=r`          | 80.1 % | true | Base85 uses an 85 character ASCII alphabet to encode. It's main use is with the PDF format and GIT. |
| raw          | `Jýÿ¯í`            | 100.0 % | false | Prints the raw byte array encoded in ISO_8859_1 which does not change the byte output. |
| utf8         | `J�����`            | 100.0 % | false | Prints the byte array interpreted as UTF-8 encoded text. Only for testing purpose. |


### Programming Languages

| Name | Example |
| :-------------: | ------------- |
| c            | `{0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11};` |
| c#           | `new byte[]{0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11};` |
| java         | `new byte[]{(byte) 0xD0, 0x3A, 0x4A, (byte) 0xEE, 0x64, 0x11};` |
| go           | `[...]byte = {0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11}` |
| kotlin       | `byteArrayOf(0xD0.toByte(), 0x3A, 0x4A, 0xEE.toByte(), 0x64, 0x11)` |
| node         | `new Buffer([0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11]);` |
| perl         | `pack 0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11;` |
| php          | `array(208, 58, 74, 238, 100, 17);` |
| python3      | `bytes([0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11])` |
| ruby         | `[0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11]` |
| rust         | `[u8; 6] = [0xd0, 0x3a, 0x4a, 0xee, 0x64, 0x11];` |
| swift        | `[UInt8] = [0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11]` |

## Digital Signatures

### Signed Jar

The provided JARs in the Github release page are signed with my private key:

    CN=Patrick Favre-Bulle, OU=Private, O=PF Github Open Source, L=Vienna, ST=Vienna, C=AT
    Validity: Thu Sep 07 16:40:57 SGT 2017 to: Fri Feb 10 16:40:57 SGT 2034
    SHA1: 06:DE:F2:C5:F7:BC:0C:11:ED:35:E2:0F:B1:9F:78:99:0F:BE:43:C4
    SHA256: 2B:65:33:B0:1C:0D:2A:69:4E:2D:53:8F:29:D5:6C:D6:87:AF:06:42:1F:1A:EE:B3:3C:E0:6D:0B:65:A1:AA:88

Use the jarsigner tool (found in your `$JAVA_HOME/bin` folder) folder to verify.

### Signed Commits

All tags and commits by me are signed with git with my private key:

    GPG key ID: 4FDF85343912A3AB
    Fingerprint: 2FB392FB05158589B767960C4FDF85343912A3AB

## Deterministic Random Bit Generation

As cryptographic pseudo-random generator (PRNG), the [NIST SP 800-90A](http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf) recommendation `HMAC-DRBG` is used in an implementation derived from [google/rappor](https://github.com/google/rappor) project. HMAC-DRBG seems to be a [better choice than the also recommended HASH-DRBG approach](https://crypto.stackexchange.com/questions/1393/is-hmac-drbg-or-hash-drbg-stronger). [Java 9](http://openjdk.java.net/jeps/273) is expected to have it's own provider for it. There [is no known issue with Java's current SHA1-PRNG](https://security.stackexchange.com/questions/47871/how-securely-random-is-oracles-java-security-securerandom) implementation, but it is less studied thant the NIST recommendation.

This implementation uses HMAC-SHA512 internally and reseeds itself after
32 KiB of random data generation which is well below the maximum NIST
recommendation.

**Further Readings:**

* [Bruce Schneider: Proof that HMAC-DRBG has No Back Doors](https://www.schneier.com/blog/archives/2017/08/proof_that_hmac.html)
* [Formal Verficiation of the HMAC-DRBG Pseudo Random Number Generator](https://www.cs.cmu.edu/~kqy/resources/thesis.pdf)
* [Security Analysis of DRBG Using HMAC in NIST SP 800-90](http://repo.flib.u-fukui.ac.jp/dspace/bitstream/10098/2126/1/art.pdf)

### DRBG Seeding & Input Sources

A DRGB needs to be seeded by strong entropy sources so it can safely
be expanded to create unpredictable pseudo random output. SP 800-90A defines
different types of input for the DRGB. This implementation uses the following
types:

#### Entropy Input

This implementation uses multiple entropy sources to seed it's random
bit generator. All these sources are combined and a weak source will not
weaken the overall output. This ensures that even if one source fails
the output is still cryptographically strong. Below is a detailed
description of the used sources:

##### Strong Secure Random Seed

This is the main entropy source. This implementation uses the `SecureRandom` class with
its `getStrongInstance()` constructor to get [the best cryptographic random generator available](https://www.synopsys.com/blogs/software-security/proper-use-of-javas-securerandom/). Internally `SecureRandom` chooses among [providers available at runtime](https://docs.oracle.com/javase/8/docs/technotes/guides/security/SunProviders.html#SecureRandomImp). The best of those access the OS own entropy pools (e.g. `/dev/random` in *nix systems) since the OS has better access to various random sources.

_Further reading:_

* [The Right Way to use Secure Random](https://tersesystems.com/2015/12/17/the-right-way-to-use-securerandom/)
* [Discussion on seeding random generators](https://crypto.stackexchange.com/questions/51218/practical-way-to-generate-random-numbers-from-prng-which-are-indistinguishable-f)

##### External Random Service Seeding

Per default the tool tries to fetch a seed from an external (supposedly true) random source.

Because there are different opinions what technique delivers truly random data, this tool
incorporates 3 different services backed by different hardware RNG. Also to mitigate the fact
that if 1 source is either compromised or produces predictable outcome, the other source
will mitigate that flaw.

Using an external random might open a new attack vector if, for example,
an attacker might read the seed send over the network. There are 2
measures against this:

* The connections is encrypted with TLS (ie. HTTPS) and the random
is singed by the creator which will be verified by a local pinned certificate (only random.org).
* The seed is only a part of the entropy source and the knowledge of it does not
make it possible to guess the random bits. Therfore there is no sole trust in
an external service. Every generation of random data will see seeding from both
local and external sources.

HKDF is used to expand the external seed to the desired length.

###### Random.org

[Random.org](https://www.random.org/) is a website that produces true random numbers based on atmospheric noise captured by several radios tuned between stations. The service has existed since 1998 and was built by Dr. Mads Haahr of the School of Computer Science and Statistics at Trinity College, Dublin in Ireland.
Random.org offers TLS encrypted access and signed random data with JSON-RPC 2.0

_References:_
* [Statistical Analysis](https://www.random.org/analysis/)
* [Wikipedia Link](https://en.wikipedia.org/wiki/Random.org)

###### Hotbits

[Hotbits](https://www.fourmilab.ch/hotbits/) is "genuine random numbers" service generated by timing successive
[pairs of radioactive decays detected](https://www.fourmilab.ch/hotbits/how3.html) by a
[Geiger-Müller tube](https://en.wikipedia.org/wiki/Geiger%E2%80%93M%C3%BCller_tube)
interfaced to a computer. This service was created by John Walker in 1996.
Hotbits offers raw bytes with a simple HTTP GET request over TLS.

_References:_
* [Statistical Analysis](https://www.fourmilab.ch/hotbits/statistical_testing/stattest.html)
* [Wikipedia Reference](https://en.wikipedia.org/wiki/List_of_random_number_generators#Random_number_servers)

###### ANU Quantum Random Numbers Server

A quantum random number generator offered by the Australian National University. The random numbers are generated in real-time by measuring the quantum fluctuations of the vacuum. The services provides
a TLS encrypted JSON/REST API.

_References:_
* [Statistical Analysis](http://qrng.anu.edu.au/NIST.php)
* [Random Generator Paper](http://aip.scitation.org/doi/10.1063/1.3597793)

##### Local seeding

The caller may provide a string that additionally seeds the random bit generator. A seed provided by the user is seen as weak seed and will always
be combined with the internal state of a strong `SecureRandom` instance.

### Nonce Input

The nonce is composed of:

* Monotonically increasing sequence number with a starting value of current JVM startup time
* System nano second time (which has arbitrary starting point)
* JVM uptime in milliseconds
* Current elapsed milliseconds since January 1, 1970 UTC.

The four 8 byte values will be hashed with HKDF.

### Personalization String

The goal of a personalization string is to gather as much information about
e.g. runtime, machine identifiers and static identifiers to make the call as
unique as possible for this particular machine/runtime/version/etc.

For this the following data will be gathered:

* MAC address of all network adapters
* Runtime & OS information (e.g. uptime, current cpu usage, processor count, classpath)
* SCM information (e.g. commit hash, committer, etc.)
* Tool version name

The resulting data will be hashed with HKDF.

## Build

### Jar Sign

If you want to jar sign you need to provide a file `keystore.jks` in the
root folder with the correct credentials set in environment variables (
`OPENSOURCE_PROJECTS_KS_PW` and `OPENSOURCE_PROJECTS_KEY_PW`); alias is
set as `pfopensource`.

If you want to skip jar signing just change the skip configuration in the
`pom.xml` jar sign plugin to true:

    <skip>true</skip>

### Build with Maven

Use maven (3.1+) to create a jar including all dependencies

    mvn clean install

## Tech Stack

* Java 8
* Maven
* rxjava2, apache-commons-codec, apache-commons-cli, Retrofit 2
* Proguard, Launch4j, Jar Signing

# Credits

* HMAC_DRBG implementation derived from [google/rappor](https://github.com/google/rappor)
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
