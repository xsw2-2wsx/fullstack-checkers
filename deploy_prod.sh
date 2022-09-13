#!/bin/bash

cd backend || exit
./gradlew build --no-daemon || exit

cd ../frontend || exit
npm install
npm run build
cd ..

docker compose up --detach --build
