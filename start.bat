@echo off
chcp 65001
if not exist .\classes mkdir .\classes
echo | set /p=Compiling class files...
javac .\src\com\molodos\codingchallenge\*.java .\src\com\molodos\codingchallenge\models\*.java -d .\classes
echo done
echo | set /p=Executing program...
echo done
echo.
echo.
java -cp .\classes com.molodos.codingchallenge.ProblemSolver