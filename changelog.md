# 0.0.4
## change:
- The code for the tick event has been modified, and now Tick events should be correctly called only once

## add:
- Floating(Cursed):Reduce damage when the player is stationary
- Cumbersome(Cursed):After the attack, there is a probability that the player will attack slowly for a period of time
## fix
- Fixed a bug where Empathy does not work when on offhand
--- 
# 0.0.3
## change:
- Now enchantment has more attributes that can be configured(rarity,isTreasure,isTradeable,isAllowedOnBooks,isDiscoverable for ALL)
- Add particle effects to Gift of Fire
- Modify the code, now Solid As A Rock can directly display item attributes
- Now Ocean Current will be ineffective when on fire(configurable)

## add:
- heavy blow(weapon):Chance to hit a heavy blow,slightly reduce attack speed

## fix:
- Fixed a bug where Gift of Fire would not reduce damage correctly with Fire Aspect
- Fixed a bug where attacking in water would always reduce damage(important,my stupid mistake)
- Fixed a bug where Solid As A Rock increased armor beyond expectations

---

# 0.0.2

## change:
- The durability value of chest armor reduced each time Pyromaniac takes effect(configurable)
- The durability value of shield reduced each time Reflecting takes effect(configurable now)
- Increased the default speed attribute of the Lightness
- Pyromaniac is now incompatible with Blast Protection
- Pyromaniac is now less likely to appear on the enchantment table
- Adjust the cost value so that the enchantment can appear correctly in vanilla lv30 enchant table

## add:
- Rolling Stone(feet):Reduces damage taken and causes damage to enemies collided with while sprinting(Does not harm friendly or neutral creatures)
- Blessed Wind(feet):Slightly increases movement speed, increase speed and step height while sprinting

## fix:
- Fixed some debugging code that shouldn't exist
- Fixed misleading comments for Lightness in config
- Fixed bug where the same enchantment appears multiple times on the same item
- Fixed some bugs related to durability with Reflecting
---

# 0.0.1

First release
## ADD:
- Advanced Looting
- Advanced Sharpness
- Counterattack
- Eater Of Souls
- Empathy
- Fire Disaster
- Gift Of Fire
- Lightness
- Ocean Current
- Overflow
- Pyromaniac
- Reflecting
- Solid As A Rock
- TheFallen