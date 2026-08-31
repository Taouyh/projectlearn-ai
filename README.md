# ProjectLearn AI

> An AI-powered project-based learning system for developers.

ProjectLearn AI is an open-source learning system designed to help developers truly understand and master real-world software projects through AI-assisted project-based learning.

## Status

🚧 Early Development

The project is currently in the MVP development stage.

Completed foundation work:

- Vue 3 + TypeScript + Vite frontend skeleton with a basic home page.
- Spring Boot 3 + Java 17 backend skeleton with a `GET /health` endpoint.
- Local MySQL 8 and Redis configuration files for the planned Docker Compose setup.

The frontend type check and production build, and the backend test and package build, currently pass. Docker Desktop is not installed on the current development machine, so the MySQL/Redis containers and their actual connections have not been verified.

## Getting Started

### Frontend

```bash
cd frontend
npm install
npm run dev
```

For a production build and type check:

```bash
npm test
npm run build
```

### Backend

The backend requires Java 17 and Maven:

```bash
mvn -f backend/pom.xml spring-boot:run
```

The health endpoint is available at `http://localhost:8080/health`.

### Local infrastructure configuration

`docker-compose.yml`, `.env.example`, and `backend/src/main/resources/application.yml` contain the planned local MySQL and Redis configuration. Docker Desktop is not currently installed, and this repository has not yet verified `docker compose up` or actual MySQL/Redis connections.

## Project Structure

```text
projectlearn-ai/
├── frontend/             Vue 3 + TypeScript + Vite frontend
├── backend/              Spring Boot 3 + Java 17 backend
├── docs/                 Product, architecture, roadmap, and development documents
├── tasks/                Task specifications and implementation records
├── docker-compose.yml    Planned local MySQL and Redis services
├── .env.example          Environment variable template
├── LICENSE
└── README.md
```

## Core Idea

ProjectLearn AI transforms:

Project Materials
→ Knowledge Map
→ Learning Path
→ AI Guidance
→ Practice
→ Output
→ Evaluation
→ Review

## Documentation

- [Product Requirements](docs/PRD.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Roadmap](docs/ROADMAP.md)
- [Development Guide](docs/DEVELOPMENT.md)

## License

MIT