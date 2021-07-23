# monkebotJ
rewrite of the monkebot in java.
host it yourself, have fun :D

example ``config.json``:
```JSON
{
  "token": "putYourDiscordBotTokenHere",
  "databaseLoc": "D:\\databases\\onetap.db",
  "databaseTableItaly": "blubber",
  "databaseTableMuseum": "italy",
  "databaseTableHighrise": "kills",
  "databaseTableAccounts": "accounts",
  "serverIp": "play.monkegame.online"
}
```

should be in the same folder as the jar ^

tables and databases  _can_ be the same (probably also the only thing that'll work but hey, feel free to fork)

### requires
- java 16
- sqlite database (otherwise the leaderboard doesn't work)

### support
join our [discord](https://discord.gg/ndtHezY7sz)

### accounts table???
("mcid" TEXT UNIQUE, "iddc" TEXT UNIQUE, PRIMARY KEY("mcid"))
