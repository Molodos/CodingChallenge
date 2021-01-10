#!/bin/sh
mkdir -p ./classes
printf "Compiling class files..."
javac -encoding utf8 ./src/com/molodos/codingchallenge/*.java ./src/com/molodos/codingchallenge/models/*.java ./src/com/molodos/codingchallenge/displaydata/*.java -d ./classes
printf "done\n"
printf "Executing program..."
printf "done\n\n\n"
java -Dfile.encoding=UTF-8 -cp ./classes com.molodos.codingchallenge.ProblemSolver
printf "Zum Beenden eine beliebige Taste drücken...\n"
read