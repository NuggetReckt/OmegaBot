# OmegaBot ®
A Discord bot written in Java, using the JDA library for the OmegaPog Discord Server.

## __Features:__
### Embeds
- When a member joins the server:
  - Welcome message
  - Verification message
- Rules message
- Message to take role to gain access to themed channels

### Counter
- Message verification & retrieval
- statistics system (`/stats [membre]`)
- Leaderboards (`/leaderboard`)
- Stats data autosave (via Tasks) 
- Messages scanning for the 1st time bot usage

### Autres
- `/suggestion` command that allows members to make suggestions

## __Admin Tools:__
- `/shutdown` command - Shutdowns the bot

## __Development:__
### Console
- Clear & complete logs
- Loading bar on first count channel scan
- SIGINT signal handling for CTRL+C in order to shutdown the bot correctly

### Code
- 3 main modules
  - `TaskHandler` - Loads, launches & stops tasks
  - `StatsHandler` - Personal & collective statistics handling
  - `LeaderboardHandler` - Loads & builds leaderboards
- Token (`.env`) stored in `/resources/env` and compiled with the program
- usage of the JDA library (version 5.6.1) in Java (version 21)

### Others
- Data is saved in a JSON file: editable if needed
- Waiting for tasks to finish if manually shutdown to prevent for data loss

## __Roadmap:__
- [ ] Improve in-code documentation
- [ ] Add more random status messages
- [ ] `/reload` command for reloading stats from the JSON file and update the leaderboards
- [ ] `/scan` command for rescanning the count channel and overwrite existing data