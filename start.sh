#!/bin/sh
mkdir -p ./classes
printf "Compiling class files..."
javac ./src/com/molodos/codingchallenge/*.java ./src/com/molodos/codingchallenge/models/*.java ./src/com/molodos/codingchallenge/gui/*.java -d ./classes
cp ./src/com/molodos/codingchallenge/gui/*.css ./classes/com/molodos/codingchallenge/gui/
printf "done\n"
printf "Executing program..."
printf "done\n\n\n"
java -cp ./classes com.molodos.codingchallenge.ProblemSolver