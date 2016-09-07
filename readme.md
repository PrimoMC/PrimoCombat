# PrimoCombat

PrimoCombat is a small plugin that changes some of the mechanics in post-1.9 pvp with the aim to balance it. This does not affect attack speed or anything of the sort.

## Features

PrimoCombat currently has 4 modules. 

- **Anti-BowBoosting** - Blocks Bow Boosting by not allowing you to hit yourself with arrows. 
- **Fishing Rods** - Restores 1.7 fishing rod functionality. Fishing rods now, once again, deal a slight bit of damage and knock you back.
- **OffhandBow** - Prevents you from putting a bow in your off-hand slot.
- **Custom Food Regen** - This makes it so your hunger also goes down as you heal from full hunger. With a few configurable values.


## Config


```yaml
modules:
  # Module that stops bowboosting.
  # Bowboosting is where you're running and lightly use a punch 2 bow so it hits you and propels you forward
  # This is unnatural and practically a bug. The module stops you from hitting yourself with arrows.
  anti-bowboost:
    enabled: true
  # Module that prevents bows in your offhand.
  # Bows in your off-hand simply make zero sense. It allows you to use bows, a double handed weapon, together with your sword without switching items.
  # This is both unbalanced and just plain dumb.
  offhandbow:
    enabled: true
  # Module that restores 1.7 fishing rod functionality.
  # Fishing rods should damage and knockback. This adds more depth to pvp.
  fishingrod:
    enabled: true
  # Module that balances 1.9 health regen.
  # In 1.9 when you have a full bar of hunger you will recover hearts at godspeed with little to no downsides. This makes regen and health potions practically worthless.
  # This module takes hunger in relation to the health regenerated. This makes you burn through food and time eating well. It also makes gapples and regen/health potions very valuable.
  customfoodregen:
    enabled: true
    # the amount of health to regenerate. 1 health = 0.5 hearts
    max-regen: 6
    # The amount of food use up to regenerate 1 health. 1 food = .5 bars of hunger
    food-per-health: 1
    # The amount of time should be between health recovery, in ticks. 1 tick = 50ms
    # Example: If max-regen = 6 and regen-time = 10, then it will recover 1 health every 500ms. So it would take 6*500ms = 3 seconds to heal 6 health (or 3 hearts)
    regen-time: 10
```
