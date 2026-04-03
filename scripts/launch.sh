#!/bin/bash

echo "==============================================="
echo "        Lancement de l'application"
echo "==============================================="
echo

cd "$(dirname "$0")/.."

echo "Répertoire courant : $(pwd)"
echo

echo "Java utilise :"
java -version
echo
echo "Programme en cours de lancement..."
echo

JAR_PATH="bin/video_game_collaborative_plateform.jar"

while true
do
    java -jar "$JAR_PATH"

    echo
    echo "==============================================="
    echo "Programme terminé."
    echo "==============================================="
    echo

    read -p "Appuyez sur R pour relancer, Q pour quitter : " choice

    if [[ "$choice" == "Q" || "$choice" == "q" ]]; then
        exit
    fi
done