@echo off
chcp 65001
if not exist .\classes mkdir .\classes
echo | set /p=Compiling class files...
javac -encoding utf8 .\src\com\molodos\codingchallenge\*.java .\src\com\molodos\codingchallenge\models\*.java .\src\com\molodos\codingchallenge\gui\*.java -d .\classes
copy .\src\com\molodos\codingchallenge\gui\*.css .\classes\com\molodos\codingchallenge\gui\
echo done
echo | set /p=Executing program...
echo done
echo.
echo.
java -Dfile.encoding=UTF-8 -cp .\classes com.molodos.codingchallenge.ProblemSolver -nogui
echo Zum Beenden eine beliebige Taste drücken...
pause>nul