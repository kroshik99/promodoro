# focus-flow

A Pomodoro-style focus timer. Every focus session you complete grows a plant in
your garden and builds up a daily streak — skip a day and the streak resets.

## What you need

- Java 17 or newer installed
- Nothing else. This project ships a Maven Wrapper (`mvnw` / `mvnw.cmd`), so you
  don't need Maven installed separately.

## Running it

From the `focus-flow` folder:

**Windows (PowerShell / cmd):**
```
.\mvnw.cmd spring-boot:run
```

**Mac / Linux:**
```
./mvnw spring-boot:run
```

Wait for a line that says `Started FocusFlowApplication`, then open
**http://localhost:8080** in your browser.

To stop it, go back to the terminal running it and press `Ctrl+C`.

> Data lives in memory (H2 database) and resets every time you restart the app.
> That's expected — there's no setup to lose data over while you're trying it out.

## Using the app

Open http://localhost:8080 and you'll see two panels:

- **Session** — type how many minutes you want to focus for and hit **Start**.
  A countdown timer runs while you work. When you're done, click **Complete**
  to bank the session (this grows your garden and counts toward your streak),
  or **Abandon** if you stopped early and don't want it to count.
- **Garden** — shows your total growth points, current streak, and longest
  streak, plus every plant you've grown. Plants move through four stages as
  they accumulate growth points: `seed` → `sprout` → `budding` → `bloomed`.
  Once a plant is fully grown, your next completed session starts a new one.

Growth points come from the *actual* minutes you focused (time between
starting and completing a session), not the number you typed in — so ending
early earns less growth, same as ending a real Pomodoro early would.

## Using the API directly

If you'd rather script it or hook up your own frontend, the same backend the
UI uses is a plain REST API:

| Method | Path | What it does |
|---|---|---|
| `POST` | `/api/sessions` | Start a session. Body: `{"minutes": 25}` |
| `POST` | `/api/sessions/{id}/complete` | Mark a session complete — grows the garden, updates the streak |
| `POST` | `/api/sessions/{id}/abandon` | Stop a session early — no growth, no streak credit |
| `GET` | `/api/sessions/current` | The currently running session, or `204 No Content` if none |
| `GET` | `/api/garden` | Garden state: plants, total growth, current and longest streak |

Example:
```
curl -X POST localhost:8080/api/sessions -H "Content-Type: application/json" -d "{\"minutes\":25}"
curl localhost:8080/api/garden
```

Only one session can be running at a time — starting a second one while
another is active returns `409 Conflict`.

## Configuration

Session length limits and the daily session goal live under `focus.rules` in
`src/main/resources/application.yml`:

```yaml
focus:
  rules:
    min-minutes: 5
    max-minutes: 120
    default-minutes: 25
    daily-goal-sessions: 4
```

Requested session lengths outside `min-minutes`/`max-minutes` are clamped to
that range automatically.

## Project layout

```
src/main/java/com/focusflow/
  config/    app-wide settings (session rules, the clock used for timing)
  domain/    the data: FocusSession, Plant, DailyStat
  repo/      database access for each of the above
  service/   the actual logic — starting/completing sessions, growing plants, streaks
  web/       the REST API and its request/response shapes
src/main/resources/
  application.yml   configuration
  static/           the browser UI (index.html, styles.css, app.js)
```
