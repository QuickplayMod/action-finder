# Action Finder Mod

Minecraft mod to find ClickEvents on IChatComponents which are displayed in chat or a book GUI.

For Quickplay, we use this to find new `/play` commands used by Hypixel.

When installed, the mod will log events to your log file at `.minecraft/logs/latest.log`. Example:

```
[21:39:57] [Client thread/INFO]: [dev.ecr.actionfinder.ActionFinder:clickEventDetected:70]: Click event detected
[21:39:57] [Client thread/INFO]: [dev.ecr.actionfinder.ActionFinder:clickEventDetected:71]: run_command
[21:39:57] [Client thread/INFO]: [dev.ecr.actionfinder.ActionFinder:clickEventDetected:72]: /tp 0 100 0
```