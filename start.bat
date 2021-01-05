@echo off
if not exist .\classes mkdir .\classes
echo | set /p=Compiling class files...
javac .\src\com\molodos\bwichallenge\*.java .\src\com\molodos\bwichallenge\models\*.java -d .\classes
echo done
echo | set /p=Executing program...
echo done
echo.
echo.
java -cp .\classes com.molodos.bwichallenge.ProblemSolver