#!/bin/sh
mkdir -p ./classes
printf "Compiling class files..."
javac ./src/com/molodos/bwichallenge/*.java ./src/com/molodos/bwichallenge/models/*.java -d ./classes
printf "done\n"
printf "Executing program..."
printf "done\n\n\n"
java -cp ./classes com.molodos.bwichallenge.ProblemSolver