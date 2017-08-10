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
Examples:
```
Create a new project
agennt --create --project Test

Add a SSN to the project
agennt --add-ssn sample.xgmml --project Test

Filter SSN with threshold 40 and taxonomy filter
agennt --filter-ssn --project Test --th 40 --tax true

Request GNN with co-occurrence of 20 and neighborhood size 10
agennt --add-gnn --co 20 --nh 10 --email "test@example.com" --project Test

Filter GNN with integrated Pfam filter
agennt --filter-gnn --project Test

Delete a project
agennt --delete --project Test
```

Command overview:
```
usage: agennt
    --add-gnn          Request GNN for first filtered SSN in project
    --add-ssn <FILE>   Add SSN file FILE
    --co <CO>          Co-occurrence
    --create           Create project
    --delete           Delete project
    --email <EMAIL>    EMail
    --filter <FILE>    Filter file for GNN filtering (optional)
    --filter-gnn       Filter first GNN in project
    --filter-ssn       Filter first SSN in project
    --help             Show information about usage
    --nh <NH>          Neighborhood Size
    --project <NAME>   Specifies project with NAME
    --tax <TAXONOMY>   Apply taxonomy filter (true,false)
    --th <THRESHOLD>   Apply specified threshold
```
