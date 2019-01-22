# IdentityVerificationClient
client tool to interact with the IdentityVerification contract

## Installation

1. Download the .zip or .tar archive [here](https://github.com/somedotone/IdentityVerificationClient/releases)
2. Extract the archive
3. Go into the _bin_ folder of the extracted archive

#### Linux / MacOS

4.1 Run the script

#### Windows

4.2 Run the file as administrator

## Building

If you don't want to download the preassembled files, build the tool with gradle and the following command:

`./gradlew assembleDist`

The assembled files can be found in the _./build/distributions/_ folder. Gradle version 4.8.1 was used to create the preassembled files

## Prerequisite

A Java Runtime Environment must be installed

## How to

This tool creates the message strings used to go through the verification process. You use the interface and Copy and paste the messages into and from the message field of the payment transaction of ardors web interface. It even creates the tagged verification token for you.

See [IdentityVerification wiki](https://github.com/somedotone/IdentityVerification/wiki/How-To) for further informations
