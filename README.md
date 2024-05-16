# AuraSkillsLevelRestrict
A simple Plugin that allows placing and breaking a block behind (multiple) skill levels, based on the [AuraSkills](https://github.com/Archy-X/AuraSkills) Plugin.

### Here is a quick list of how a valid entry looks like:
```yaml
  DIAMOND_BLOCK: # Define a valid Material
    types:
      place:
        use: false # Tick wheter you want to lock placing the Material
        message: "&cYou can't place diamond blocks!" # The message that will be sent if disallowed
      break:
        use: true # Tick wheter you want to lock breaking the Material # Tick wheter you want to lock breaking the Material
        message: "&cYou can't break diamond blocks!" # The message that will be sent if disallowed
    levels:
      mining: 50 # Define an AuraSkills (No custom Skills!) Skill
    permissions:
      - example.permission # Define a permission
    excluded-worlds:
      - world # Define worlds that will be ignored
```
**Tips:**
- All valid Materials can be viewed here [Spigot Docs](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html)
- `levels`, `permissions` and `excluded-worlds` don't need to have values

### Questions/Bugs?
Join the Aurelium Discord [here]() and ask in [this]() post!
