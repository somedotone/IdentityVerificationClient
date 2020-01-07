# IdentityVerificationClient

A (development) tool to interact with the [IdentityVerification](https://github.com/somedotone/IdentityVerification) contract.

## Installation

1. Download the .zip or .tar archive [here](https://github.com/somedotone/IdentityVerificationClient/releases)
2. Extract the archive
3. Go into the _bin_ folder of the extracted archive

#### Linux / MacOS

4.1 Run the **IdentityVerificationClient** script

#### Windows

4.2 Run the **IdentityVerificationClient.bat** file as administrator

## Building

If you don't want to download the preassembled files, build the tool with [gradle](https://gradle.org) and the following command:

`./gradlew assembleDist`

The assembled files can be found in the _./build/distributions/_ folder. Gradle version 4.8.1 was used to create the preassembled files

## Prerequisite

A Java Runtime Environment \(&gt;= Java 8\) must be installed

## How to

This tool creates the message strings used to go through the verification process. You can use it in two ways.

1. If you don't trust the tool, use it to just create the messages for you. Copy and paste the required messages into and from the tool and ardors official web interface (if you don't run a full node, use [jeluridas](https://www.jelurida.com) official [testnet node](https://testardor.jelurida.com) or some.ones [testnet node](https://testardor.some.one)). The messages transmitted by the contract runner can be found as (encrypted) attached messages inside a payment transaction. The messages transmitted by the user need to be included as (encrypted) attached messages, as well.
2. If you trust the tool (validate the code and build it from source if you don't trust), it can guide you through the whole verification process and handles the messaging and token creation for you. You can therefore use it as a stand alone tool without needing to switch to the web interface anymore.

It should be largely self descriptive if you've red the documentation from the [IdentityVerification](https://github.com/somedotone/IdentityVerification/wiki/How-To) contract.

See [IdentityVerification wiki](https://github.com/somedotone/IdentityVerification/wiki/How-To) for further informations

## Misc

This tool has been developed to develop the contract and is therefore not a really beautiful and \(end\) user friendly tool. It's some kind of development tool for the contract. Some thoughts for a more user friendly web based client can be found [here](https://github.com/somedotone/WebClient). It's inspired by a competitor from the hackathon who [built](https://github.com/aajajim/identityVerifier) a really nice [frontend](https://aajajim.github.io/identityVerifier/sessions/login) to demonstrate the goal of the Identity Verification hackathon Challenge.

