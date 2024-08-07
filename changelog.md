# 0.0.13
## add:
- Wind Fire Wheel (feet): Run, Jump, and Take Off
- Spreading Spores (weapon): Continuously striking, accumulating spores, and then erupting
- Thrilling Thunder (weapon): Chance to paralyze the target and cause loss of life
- Added special texture for element enchanted book(can be configured to enable or disable. Currently incompatible with Neko's enchanted book)

- 风火轮(靴子):跑,跳,然后起飞
- 传播孢子(武器):连续打击,积累孢子,然后爆发造成大量伤害和中毒效果
- 颤栗雷霆(武器):有概率令对目标造成麻痹并流逝生命
- 为元素附魔添加了特殊的材质(可以配置是否开启.目前和Neko的附魔书不兼容,总是会覆盖或者被覆盖)

## change:
- Now Feather has changed to legging enchant

- 现在羽落改为护腿附魔

## fix:
- Fixed a bug where the enableIncompatible configuration did not work
- Fixed a bug of invalid airSupplyRatio configuration for bubble shield

- 修复了是否启用附魔不兼容配置不起效的bug
- 修复了气泡护盾airSupplyRatio配置无效的问题

# 0.0.12
## fix:
- Correct an incorrect description in Purifying(thank you to the_real_adobo)
- Fix bug where blood sacrifice self injury configuration does not work
- Added missing language entries
- Fixed bug where adding nbt caused items to not stack (**important**)

- 修复血祭自伤配置不起效的bug
- 增加了缺失的语言条目
- 修复了错误添加nbt导致物品无法堆叠的bug(**重要**)

# 0.0.11
## add:
- Pure Fate (Chest): Helps to give birth to life to get rid of the curse
- Blood Sacrifice (Weapon): Hit the target hard at the cost of own blood
- Sharp Stone (Weapon/Shield): Adds damage based on armor value
- Delayed Execution (Weapon): After attacking, the target enters a state of absorbing damage, and causes execution damage after a period of time
- Sacred Supreme Sharpness (Weapon): There is endless evil waiting for judgment
- Greedy Supreme Looting (Weapon): Greed is never satisfied

- 纯洁宿命（胸甲）：帮助孕育生命，以摆脱诅咒的困扰
- 血祭（武器）：以自身的鲜血为代价重创目标
- 尖锐硬甲（武器/盾牌）：附加基于护甲值的伤害
- 秋后问斩（武器）：攻击后使目标进入吸收伤害的状态，一段时间后造成处决伤害
- 圣洁超级锋利（武器）：还有无尽的邪恶等待着审判
- 贪婪超级抢夺（武器）：贪欲从未感到餍足

## change:
- Corrected Eater Of Souls damage growth and added a cap in the configuration
- Optimized the damage display of The Fallen
- Modified the effect of Nirvana
- Modified the Templar Shield BUFF of Eucharist, and you can set the proportion of damage reduction
- Reduced the initial probability of obtaining mod enchantments from chests
- Increased the initial maximum level of Gift Of Fire
- Increased the maximum level of special enchantments obtained from treasure chests
- Change curse prefix to suffix

- 修正了噬魂者的伤害增长，并在配置中添加上限
- 优化了了堕落者的伤害显示
- 修改了涅槃的效果
- 修改了圣餐的圣盾之佑BUFF，可以设置减免伤害的比例
- 降低了从宝箱获得模组附魔的初始概率
- 提高火之恩赐的初始最大等级
- 提高了从宝箱获得的特殊附魔的最大等级
- 将诅咒前缀修改为后缀

## fix:
- Fixed some issues with JEI configuration
- Fixed a bug where certain monsters were not judged as enemy monsters, resulting in the invalidation of enchantments (thank you to Kong)

- 修复JEI配置的一些问题
- 修复某些怪物不被判断为敌怪导致附魔失效的bug(感谢 空)

# 0.0.10
## add:
- Lava Burst (Weapon): There is a chance of explosion when attacking an enemy
- Armor Forging (Armor): Taking damage can strengthen the armor
- Nirvana (Chest): When the health is low and on fire, health recovery is faster
- Self-immolation (Weapon): Attacking the enemy has a small chance of igniting yourself, and the probability is higher when the target is on fire
- Bubble shield (Chest): When the character has sufficient oxygen, consume a part of the oxygen to reduce the damage
- Drowning (Weapon): Each attack consumes oxygen, and when the oxygen value is 0, the attack will cause damage
- Perversion (Leggings): Likes to wear nothing
- New mechanism: Element attributes must meet certain conditions in order to obtain some special enchantments from treasure chest loot
- Add JEI compatibility, currently only adding partial information, can be configured


- 熔岩爆发（武器）：攻击敌人时有概率产生爆炸
- 百炼锻甲术（盔甲）：承受伤害可以强化盔甲
- 涅槃（胸甲）：低生命值且处于着火状态时,生命回复加快
- 自焚（武器）：攻击敌人小概率点燃自身,目标处于着火状态时概率更高
- 气泡护盾（胸甲）： 当角色氧气值充足时,消耗一部分氧气值减少受到的伤害
- 溺亡（武器）：每次攻击都会消耗氧气值,氧气值为0时攻击则受到伤害
- 变态（护腿）：喜欢真空出行
- 新的机制：需要在元素属性满足一定条件，才能从宝箱战利品获得一些特殊的附魔
- 添加JEI兼容，目前仅仅添加部分信息，可以配置

## change:
- Increased the maximum enchanting level for most enchantments

- 提高了大部分附魔的最大附魔等级

## fix:
- Fix bugs where advanced sharpness and advanced snatching of certain configurations do not work

- 修复高级锋利和高级抢夺某些配置不起效的bug


# 0.0.9
## add:
- StackingWaves(Weapon):Reduces the initial attack speed of the weapon, and increases the attack speed during continuous attacks
- Charge(Weapon):Charge forward a certain distance after attacking the enemy, and become invincible during the charge

- 叠浪（武器）：降低武器攻击初始速度，连续攻击时增加攻击速度
- 冲锋（武器）：攻击敌人后向前冲刺一段距离，冲刺过程无敌

## change:
- Weakened the initial attack speed bonus of ocean currents
- Enhanced the initial damage bonus of the Gift Of Fire

- 削弱了洋流的初始攻速加成
- 增强了火之恩赐的初始伤害加成

## fix:
- Fixed a bug where Empathy doesn't work

- 修复了换位思考附魔失效的bug
- 添加了缺失的语言文件条目

# 0.0.8
## add:
- Eucharist(Chest):Gain a templar shield buff after eating high quality food
- Melter(Weapon):Reduces weapon damage and temporarily reduces enemy armor when attacking

## change:
- Added configurable attributes for Advanced Sharpness
- Adjust damage of Rolling Stone
- Enhance UnyieldingSpirit, which will remove all debuffs upon resurrection, add the configuration of consuming durability
- Feint Attack and Duellist's Prerogative are incompatible now
- Add modded enchantment books to the chest loot that can configure probability 

## fix:
- Fixed a bug where the configuration file for Bloodthirsty might not work correctly
- Fixed a bug where the tooltip file for Eater of Souls didn't display

# 0.0.7
## add:
- add Element Stat system(Apply effects based on the level of the special elemental enchantment)
- add a command to get Element Stat (/fancyenchantments elementstat)
- add Duellist's Prerogative(weapon):When there are no other creatures around you and the target, the damage you deal to it is increased
- add Paladin's Shield(shield):Reduce the damage you receive, and take some of the damage for nearby companions or other players at a lower cost

## fix:
- Fixed a bug where the isDiscoverable might not work correctly on Advanced Sharpness and Advanced Looting

# 0.0.6
## change:
- Added configurable attributes for Unyielding Spirit and Cursed Gaze

## add:
- Calmer(Chest):Gain damage absorption when taking damage
- Feint Attack(Weapon):Reduces damage to the enemy that attack directly, and deals heavy damage to enemies around it
- Purifying(Weapon):A more powerful smith,and can purify zombie villagers

## fix:
- Fixed a bug where pyromaniac didn't work on certain types of explosions
- Fix a bug where the triggering condition for Unyielding Sprite is incorrect

# 0.0.5

## add:
- Unyielding Spirit(head):Get extra survival time before death, kill monsters to resurrect
- Cursed Gaze(head):Give the monsters you gaze upon weakness and slowness effects

# 0.0.4
## change:
- The code for the tick event has been modified, and now Tick events should be correctly called only once
- Increase the default damage multiplier of Eater of Souls
- Now you can configure whether to enable incompatibility between mod's enchantments

## add:
- Floating(Cursed):Reduce damage when the player is stationary
- Cumbersome(Cursed):After the attack, there is a probability that the player will attack slowly for a period of time
- Hungry:Mobs drop more food
- Bloodthirsty:Feeling hungry, obtaining saturation from slaughter
- Dominion:The power of a weapon increases with sum of its enchantment levels
- Falling Stone:Fall from a height to cause damage to enemies and avoid falling injuries
- Feather Fall(feet):Sneaking in the air to gain a Slow Falling effect 

## fix:
- Fixed a bug where Empathy does not work when on offhand
- Fixed bug where RollingStone's damage was not considered player damage
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