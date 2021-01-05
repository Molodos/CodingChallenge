#Coding Challenge Lösung
##1. Analyse der Problemstellung
Bei der Aufgabenstellung handelt es sich um eine Variante des Knapsack Problems, welches ein Optimierungsproblem darstellt in dem man versucht Gegenstände, welche einen Wert und ein Gewicht haben, so in einen Rucksack mit begrenzter Traglast zu laden, dass der Gesamtwert aller Gegenstände im Rucksack maximal ist.  
Noch genauer handelt es sich dabei um das Multiple Knapsack Problem, da die Gegenstände auf mehrere Transporter aufgeteilt werden sollen.

##2. Ungeeignete Lösungsansätze
###2.1 Bestehende Lösungsmethoden speziell für das Knapsack-Problem
Da zum Knapsack-Problem bereits Lösungsmethoden bestehen, könnten diese auf die Aufgabenstellung angepasst und angewandt werden, um die Berechnung einer optimalen Lösung zu garantieren. Da bestehende Lösungsalgorithmen allerdings nicht für so große Mengen an Gegenständen wie in dieser Aufgabenstellung ausgelegt sind, erschien mir dies aus folgenden Gründen nicht der richtige Lösungsweg zu sein:

1. Bei bestehenden Algorithmen wird über die Kapazitäten aller Rucksäcke und die Menge der Gegenstände iteriert. Da in der Aufgabenstellung allein die Größe eines Transporters mehr als 1 Mio Gramm beträgt, lässt sich bei nur zwei Transportern schon eine Gesamtzahl von mehreren Billionen Iterationen errechnen.
2. Betrachtet man die Tatsache, dass die Ergebnisse der einzelnen Iterationen in einer mehrdimensionalen Matrix zwischengespeichert werden, so kann man sich errechnen, dass ohne signifikante Optimierungen mehrere TB Arbeitsspeicher benötigt werden, was die Kapazitäten jedes normalen Computers um weiten übersteigt.
3. Die Speicherauslastung kann zwar optimiert werden, allerdings ist auch die Laufzeit, welche sich bei dem Ausmaß der Aufgabenstellung auf mehrere Stunden bis Tage beläuft, ein grundlegendes Problem.

Aufgrund der Abwägung zwischen einem sehr geringen Risiko, dass das Ergebnis nicht zu 100% optimal ist und einer extrem hohen Berechnungszeit habe ich mich daher für erstere Möglichkeit entschieden. 

###2.2 Linear Programming
Die Aufgabenstellung könnte als lineares Gleichungssystem mit Constraints und einem Wert, welcher maximal werden soll, ausgedrückt werden. Mithilfe von Lösungsbibliotheken wie zum Beispiel dem *Glop Linear Solver* der *Google OR-Tools* könnte dann eine möglichst optimale Lösung bestimmt werden. Aufgrund folgender Gründe habe ich mich allerdings auch gegen diesen Ansatz entschieden:

1. Durch die Benutzung von entsprechenden Solver-Bibliotheken entstehen viele Abhängigkeiten meines Codes von anderen Code-Stücken und teilweise sogar von auf dem Computer installierten Programmen.
2. Da man bei diesem Lösungsansatz lediglich die Aufgabenstellung umformulieren würde und die eigentliche Lösung des Problems dem bereits vorhandenen Solver überlässt, denke ich, dass dies nicht das Ziel des Wettbewerbs ist.

##3. Eigener Algorithmus zur Lösung
1. Gegenstände aus der Datei `items.csv` und Transporter aus der Datei `trucks.csv` einlesen
2. Gegenstände nach Effizienz sortieren  
3. Alle Transporter mit den effizientesten Gegenständen befüllen  
4. Gegenstands-Gruppen zwischen je zwei Transportern versuchen so zu tauschen, dass das übrige Gewicht in einem Transporter maximal wird
5. Für jeden Transporter versuchen, Gegenstands-Gruppen mit Gegenstands-Gruppen aus dem übrigen nicht verladenen Vorrat so zu tauschen, dass der Gesamtwert der Gegenstände im Transporter steigt  
6. Falls in *4.* oder *5.* eine Änderung durchgeführt wurde, wieder zu *4.* springen und dort fortsetzen  
7. Die Finale Beladung der Transporter ausgeben und in der Datei `solution.csv` speichern

> Es werden bei Gegenstands-Gruppen immer nur alle möglichen Gruppen der Menge 1 bis 5 betrachtet, um die Laufzeit ohne signifikante Qualitätsverluste erheblich zu optimieren.

> Für die gegebene Aufgabenstellung findet der Algorithmus eine optimale Lösung

> Für andere Eingabewerte können die Dateien `items.csv` und `trucks.csv` editiert werden

##4. Ausführung des Lösungsprogramms
###4.1 Voraussetzungen
* Java JDK 8 muss installiert sein

###4.1 Ausführung
####4.1.1 Linux
1. Im Stammverzeichnis des Repositories das Skript `start.sh` lokalisieren
2. Das Skript entweder per Doppelklick oder aus der Konsole mit dem Befehle `sh ./start.sh` ausführen
3. Das Programm wird nun kompiliert um anschließend automatisch ausgeführt
4. Die Lösung erscheint nach der Berechnung in der Konsole und in der sich öffnenden Benutzeroberfläche. Außerdem wird sie in der Datei `solution.csv` gespeichert

####4.1.2 Windows
1. Im Stammverzeichnis des Repositories das Skript `start.bat` lokalisieren
2. Das Skript entweder per Doppelklick oder aus der Konsole mit dem Befehle `./start.bat` ausführen
3. Das Programm wird nun kompiliert um anschließend automatisch ausgeführt
4. Die Lösung erscheint nach der Berechnung in der Konsole und in der sich öffnenden Benutzeroberfläche. Außerdem wird sie in der Datei `solution.csv` gespeichert