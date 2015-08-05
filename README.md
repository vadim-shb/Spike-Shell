# spike-Shell

Simple shell emulator on java.
Supports commands:
- echo
- exit
- ls (with -l and -a keys only)
- cd
- mkdir
- cp
- mv
- rm

##Goals:
- play with java IO/NIO file API.

##Not goals:
- Stability
- Tests
- Usefulness
 
#Usage
##cat
cat <FILE_NAME> - print _FILE_NAME_ on console.
##cd
cd _DIRECTORY_NAME_ - change current directory to _DIRECTORY_NAME_.
##cp
cp _FILE_OR_DIRECTORY_NAME_ _DESTINATION_ - copy file or directory in destination. Destination must contain name of copy file/directory.
