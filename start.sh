#!/bin/sh
mkdir -p ./classes
printf "Compiling class files..."
javac ./src/com/molodos/codingchallenge/*.java ./src/com/molodos/codingchallenge/models/*.java ./src/com/molodos/codingchallenge/gui/*.java -d ./classes
printf "done\n"
printf "Executing program..."
printf "done\n\n\n"
java -cp ./classes com.molodos.codingchallenge.ProblemSolver