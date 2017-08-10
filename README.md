# AGeNNT
AGeNNT (Automatic GEneration of Neigbourhood NeTworks)

## Starting AGeNNT
AGeNNT can be started without any additional configuration
by executing `agennt.bat` (Windows) or `agennt` (Linux) in the
subdirectory `.\bin`.

## Runtime Environment
AGeNNT needs at least Oracle Java SE Runtime Environment
Version 8 Update 101. It can be downloaded for most operating
systems from https://www.java.com/download/

## User-defined whitelist
The file `user_whitelist.txt` is a copy of the built-in whitelist.
Its content can be altered by the user and used alternatively.

## Command Line Interface
AGeNNT provides an additional CLI.

```
Create a new project
agennt --create --project Test

Add a SSN to the project
agennt --add-ssn sample.xgmml --project Test

Filter SSN with threshold 40 and taxonomy filter
agennt --filter-ssn --project Test --th 40 --tax true

Delete a project
agennt --delete --project Test
```
