# LinkPulse Backend

## Docker

Set the required runtime values before starting the stack:

```powershell
$env:JWT_SECRET = "replace-with-a-secure-secret-of-at-least-32-characters"
$env:APP_BASE_URL = "https://links.example.com"
```

Build the backend image:

```bash
docker build -t linkpulse-backend .
```

Start the backend and MySQL:

```bash
docker compose up --build -d
```

Stop the containers:

```bash
docker compose down
```

Use `docker compose down -v` to also remove the persistent MySQL volume.
