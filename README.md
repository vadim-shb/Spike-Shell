# spike-Shell

Simple shell emulator on java.
Supports commands:
- cat
- cd
- cp
- echo
- exit
- ls 
- mkdir
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
cat \<FILE_NAME\> - print _FILE_NAME_ on console.
##cd
cd \<DIRECTORY_NAME\> - change current directory to _DIRECTORY_NAME_.
##cp
cp \<FILE_OR_DIRECTORY_NAME\>  \<DESTINATION\> - copy file or directory in destination. Destination must contain name of copy file/directory (not just exist folder where you want to put your file).
##echo
echo \<SOME_TEXT\> - print text after echo to console.
##exit
exit [EXIT_CODE]- exit with EXIT_CODE
##ls
ls [PARAMETERS] [DIRECTORY] - print information about files and folders contained in _DIRECTORY_. If _DIRECTORY_ not specified then _DIRECTORY_ is current directory.

PARAMETERS:

[-a] - list hidden files

[-l] - show extended info about files
##mkdir
mkdir \<DIRECTORY_PATH\> - create directory on specified path

##mkfile
mkdir \<FILE_PATH\> [FILE_CONTENT] - create text file on specified path witch contain _FILE_CONTENT_ inside
