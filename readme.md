# PrimoCombat

PrimoCombat is a small plugin that changes some of the mechanics in post-1.9 pvp with the aim to balance it. This does not affect attack speed or anything of the sort.

## Features

PrimoCombat currently has 4 modules. 

- **Anti-BowBoosting** - Blocks Bow Boosting by not allowing you to hit yourself with arrows. 
- **Fishing Rods** - Restores 1.7 fishing rod functionality. Fishing rods now, once again, knock you back.
- **OffhandBow** - Prevents you from putting a bow in your off-hand slot.
- **Custom Food Regen** - This makes it so your hunger also goes down as you heal from full hunger. With a few configurable values.
- **Projectile Trajectories** - This gets rid of projectile trajectory randomness/arrow spread and odd behaviour moving while shooting. 
    - Arrow Spread:
        - Before (50 block distance): http://i.rdthrne.com/67b0845555.png
        - After (50 block distance): http://i.rdthrne.com/97f143a59a.png
    - Odd behaviour:
        - Before: http://i.rdthrne.com/6388fdf843.mp4
        - After: http://i.rdthrne.com/5e0964276b.mp4


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
  # Module that removes randomness from projectile trajectories.
  # In vanilla Minecraft arrows have a slight randomness to the trajectories, it's not very noticeable at close-range,
  # but it becomes exponentially more noticeable the further away the player is. At a certain distance it becomes pretty difficult to hit targets.
  # This also gets rid of odd projectile behaviour when you're moving around, falling, etc.
  trajectory:
    enabled: true
```


