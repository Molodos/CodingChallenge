@echo off
chcp 65001
if not exist .\classes mkdir .\classes
echo | set /p=Compiling class files...
javac -encoding utf8 .\src\com\molodos\codingchallenge\*.java .\src\com\molodos\codingchallenge\models\*.java .\src\com\molodos\codingchallenge\displaydata\*.java -d .\classes
echo done
echo | set /p=Executing program...
echo done
echo.
echo.
java -Dfile.encoding=UTF-8 -cp .\classes com.molodos.codingchallenge.ProblemSolver
echo Zum Beenden eine beliebige Taste drücken...
pause>nul