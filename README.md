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
AGeNNT provides an additional CLI with the following commands:
```
usage: agennt
    --add-ssn <FILE> ><NAME>   Add SSN file FILE to project NAME
    --create-project <NAME>    Create project with NAME
    --delete-project <NAME>    Create project with NAME
    --help                     Show information about usage
```