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
- mkfile
- mv
- rm
- tail

##Goals:
- play with java IO/NIO file API.

##Not goals:
- Stability
- Tests
- Usefulness
 
#Usage

##cat
cat \<FILE_NAME\> - print _FILE_NAME_ content on console.

##cd
cd \<DIRECTORY_NAME\> - change current directory to _DIRECTORY_NAME_.

##cp
cp \<FILE_OR_DIRECTORY_PATH\>  \<DESTINATION_PATH\> - copy _FILE_OR_DIRECTORY_ into _DESTINATION_. _DESTINATION_ must contain name of file/directory to copy (not just exist folder where you want to put your file).

##echo
echo \<SOME_TEXT\> - print text after echo to console.

##exit
exit [EXIT_CODE]- exit with EXIT_CODE.

##ls
ls [PARAMETERS] [DIRECTORY] - print information about files and folders contained in _DIRECTORY_. If _DIRECTORY_ not specified then _DIRECTORY_ is current directory.

PARAMETERS:

[-a] - list hidden files.

[-l] - show extended info about files.
##mkdir
mkdir \<DIRECTORY_PATH\> - create directory on specified path.

##mkfile
mkfile \<FILE_PATH\> \<FILE_CONTENT\> - create text file (UTF-8) on specified path witch contain _FILE_CONTENT_ inside.

##mv 
mv \<FILE_OR_DIRECTORY_PATH\>  \<DESTINATION_PATH\> - move _FILE_OR_DIRECTORY_ into _DESTINATION_. _DESTINATION_ must contain name of file/directory to move (not just exist folder where you want to put your file).

##rm
rm \<FILE_OR_DIRECTORY_PATH\> - remove file or directory. If directory not empty remove it recursively.

##tail
tail [PARAMETERS] \<FILE_PATH\> - print last lines of text file (UTF-8 only).

PARAMETERS:

[-n \<LINES_NUMBER\>] - specify number of lines to print. If not specified - default number is 10.

[-f] - follow changes on file. If file changed, tail will be printed again in real time. To exit this mode enter 'q'.
