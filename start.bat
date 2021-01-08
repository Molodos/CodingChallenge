@echo off
if not exist .\classes mkdir .\classes
echo | set /p=Compiling class files...
javac .\src\com\molodos\codingchallenge\*.java .\src\com\molodos\codingchallenge\models\*.java .\src\com\molodos\codingchallenge\gui\*.java -d .\classes
copy .\src\com\molodos\codingchallenge\gui\*.css .\classes\com\molodos\codingchallenge\gui\
echo done
echo | set /p=Executing program...
echo done
echo.
echo.
java -cp .\classes com.molodos.codingchallenge.ProblemSolver