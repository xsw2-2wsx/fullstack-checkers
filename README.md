# Simple fullstack checkers

This is a simple and quick implementation of online multiplayer checkers, built mainly
so I can try out React.

## Setup

### Build dependencies

Make sure you have `jdk` >= 11, `nodejs` >= 14.19.03, `npm`

### Development   

- For backend, use `./gradlew bootRun`
- For frontend, `npm install` and `npm run start`

### Production with containers

- Make sure you have `Docker` >= 20.10.18 installed
- Edit the `frontend/env.production.js` config file
- Edit the `backend/app/src/main/resources/application.properties`
- (Optional) Adjust `docker-compose.yaml`
- Execute `./deploy_prod.sh` script