# spike-Shell

Simple shell emulator on java.
Supports commands:
- cat
- cd
- cp
- echo
- exit
- ls (with -l and -a keys only)
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
