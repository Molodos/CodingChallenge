#!/bin/sh
mkdir -p ./classes
printf "Compiling class files..."
javac -encoding utf8 ./src/com/molodos/codingchallenge/*.java ./src/com/molodos/codingchallenge/models/*.java ./src/com/molodos/codingchallenge/gui/*.java ./src/com/molodos/codingchallenge/displaydata/*.java -d ./classes
cp ./src/com/molodos/codingchallenge/gui/*.css ./classes/com/molodos/codingchallenge/gui/
printf "done\n"
printf "Executing program..."
printf "done\n\n\n"
java -Dfile.encoding=UTF-8 -cp ./classes com.molodos.codingchallenge.gui.AlgorithmGUI