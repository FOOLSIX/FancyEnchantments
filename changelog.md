# 1.7.3

## change:
- Adjusted the configuration options for Serenity (more granular settings)

- 调整了平静的配置项(粒度更细了)

## fix:
- Fixed Duelist's Privilege not taking effect
- Fixed Pyromaniac not taking effect in some cases
- Fixed compatibility issues with Overhealing

- 决斗者的特权不生效
- 纵火狂某些情况下不生效
- 过量治疗的兼容性问题


# 1.7.2
## add:
- Added several configuration options for the enchanting table

- 为附魔台增加一些配置项

## fix:
- Fixed a null pointer exception when HitResult is null

- 修复HitResult为null时的空指针错误


# 1.7.1

## fix:
- Fixed the issue where the Elemental Enchanting Table could not be properly mined and dropped

- 修复元素附魔台无法正常挖掘掉落的问题

# 1.7.0
## add:
- Elemental Enchanting Table:A new enchanting station with custom enchantment offers, bookshelf scaling, and an element overview panel
- Catalysts:Catalyst items can bias the Elemental Enchanting Table toward specific elements and special enchantments
- JEI support for the Elemental Enchanting Table, including catalyst and upgrade material usage descriptions

- 元素附魔台:新增一个拥有自定义附魔候选,书架加成与元素信息面板的附魔工作台
- 催化剂:催化剂物品可以让元素附魔台更偏向特定元素与特殊附魔
- 为元素附魔台添加JEI支持,可查看催化剂与升级材料的用途说明

## change:
- Improved the enchantment selection algorithm and GUI of the Elemental Enchanting Table
- Added an upgrade material slot, and upgrading enchantments now consumes corresponding materials
- Reworked related textures, recipes, and data resources for the Elemental Enchanting Table and catalysts

- 优化了元素附魔台的附魔选取算法与界面表现
- 增加升级材料槽位,提升附魔时现在会消耗对应材料
- 重做了元素附魔台与催化剂相关的贴图,配方和数据资源

## fix:
- Fixed several issues in the Elemental Enchanting Table flow and interaction details
- Adjusted and corrected related catalyst and table behavior

- 修复了元素附魔台流程与交互细节中的多个问题
- 调整并修正了相关催化剂与附魔台行为

# 1.6.0
## add:
- Gale:Gain Haste for a short time after breaking blocks

- 疾风:破坏方块后获得短时间急迫效果
## fix:
- Element stats effects are calculated at the wrong time
- Fixed the issue where multiple event triggers occurred when starting the server multiple times

- 修复了元素属性效果错误频次计算的问题
- 修复了多次启动服务器会多次触发事件的问题


# 1.5.0
## add:
- Beyond The Flesh:When placed in the hot bar, each attack will inflict damage equivalent to the item's damage, consuming a large amount of durability. Only one item will be effective
- Ailment Devourer:Devours hostile magic, shortening the duration of negative effects
- Over Healing:Excessive healing will be converted into absorption

- 超越肉体:置于快捷栏,每次攻击都会附带该物品的伤害,消耗大量耐久,只会有一个物品生效
- 噬魔:缩短负面效果时间
- 过量治疗:过量的治疗会转化为伤害吸收效果

## changes:
- The portion of EPF greater than 20 due to the protection enchantment has been enhanced

- 为因保护附魔使得EPF大于20的部分做了增强处理

# 1.4.3
## fix:
- Fixed the server error related to the UnyieldingSpirit

- 修复了不屈的精神的服务端错误

# 1.4.2
## changes:
- Streamline no longer has the feature of ignoring gravity
- Make MultipleShot compatible with special arrows
- Elemental BUFF updates less frequently and lasts longer
- A simple HUD has been added to the Unyielding Spirit

- 流线型不再有忽视重力的特性
- 使万箭兼容特殊箭矢
- 元素BUFF更新更少,持续时间更长
- 为不屈的精神添加了一个简易的hud

# 1.4.1
## fix:
- Fix the issue where FallingStone always takes effect

- 修复落石附魔总是生效的问题

# 1.4.0
## add:
- Rocket Jump:Head down and attack, then rocket jump
- Dedication:Chance to consume the enchantment when hurt to enhance armor attributes

- 火箭跳:低头攻击,然后火箭跳
- 奉献:受伤时有概率消耗该附魔,增强盔甲属性

## fix:
- Fixed the issue where arrows with Streamline would not disappear properly after losing speed

- 修复具有流线型的箭矢失去速度后无法正常消失的问题

# 1.3.3
## fix:
- Fixed a bug where Condition Overload was ineffective

- 修复了异况超量无效的问题

# 1.3.2
## change:
- The effect given by element attributes can now be configured
- Now you can adjust the maximum level of enchantment found in chest loot

- 元素属性给的药水效果现在可以配置
- 现在可以调整从箱子里找到的附魔的最大等级

## fix:
- Add missing lang entries
- Correct the Streamline configuration name
- Fix the crash caused by the combination of Multiple Shot and apotheosis
- Fixed advanced flame attachment to prevent cooked meat from falling off
- Fix the crash caused by Blind Loyalty

- 添加缺失的lang条目
- 修正流线型的配置文件名
- 修复万箭与神化叠加导致崩溃
- 修复了高级火焰附加不掉落熟肉
- 修复愚忠导致的崩溃

# 1.3.1

## change:
- Spawn probability can now be adjusted based on the rarity of the enchantment
- Special loot enchantments will now only appear once per chest
- Adjusted the generation conditions of some enchantments
- Increased the dodge limit of the afterimage, and will gain extra invincibility time after dodging

- 现在可以根据附魔的稀有度调整生成概率
- 现在特殊战利品附魔一个箱子只会出现一个
- 调整了一些附魔的生成条件
- 提高了残影的闪避上限,闪避后会获得额外无敌时间

## fix:
- Fixed the spawn rate with Sighs of Ashes(If you update from 1.3.0, you need to remove the configuration file)

- 修复了余烬之叹息的生成概率问题(如果从1.3.0升级,需要移除配置文件)

# 1.3.0
## add:
- Abyssal Maelstrom:The target hit will become a maelstrom involving enemies
- Lithic Siphon:Mining 'stone' no longer drops blocks, but has a chance to drop minerals
- Blind Loyalty:Even if forsaken, bound to return

- 噬渊漩涡:命中的目标将会变为卷入敌方的漩涡
- 地髓虹吸:挖掘'石头'不再掉落方块,而是有概率掉落矿物
- 愚忠:纵使抛弃,注定归来
## change:
- Improved the calculation of forging armor enchantment

- 改进了锻甲术的锻甲计算

## fix:
- Fixed the issue of incorrect enchantment categories for Multiple Shot

- 修复了万箭类别不正确的问题

# 1.2.1
## fix:
- A bug related to item nbt

- 修复了一个有关物品nbt的bug

# 1.2.0
## add:
- Purification Slash:Significantly reduce damage to players and pets, and remove their negative effects after attacking
- Sighs of Ashes:Increase damage based on remaining burning time
- Advanced Fire Aspect:Increase target burning time when hit
- Advanced Flame:Increase target burning time when hit
- Frozen Heart:Apply slowness to the attacker when attacked
- Multiple Shot:Shoot additional arrows
- Blood Feed:Have a probability of increasing maximum health attribute after killing

- 净化斩:大幅减少对玩家和宠物的伤害,攻击后移除其负面效果
- 余烬之叹息:根据剩余燃烧时间增加伤害
- 高级火焰附加:击中时增加目标燃烧时间
- 高级火矢:击中时增加目标燃烧时间
- 冰封之心:受击时对攻击者施加缓慢
- 冰爆:拥有缓慢的目标死亡时释放冰爆,造成伤害和缓慢效果
- 万箭:散射出额外箭矢
- 饮血:杀敌后武器概率获得提高最大生命值的属性
## change:
- Now the Ignis element stats still gives strength effect
- You can now configure the maximum level of effect given by element stats


- 现在Ignis元素属性还会给予力量效果
- 现在可以配置元素属性给予效果的最高等级

## fix:
- The effect given by element stats does not start from tier1 but from tier2
- Fixed a bug where jei information may not display correctly after disabling enchantments
- Fixed a bug where Condition Overload was determined not by the target but by the attacker


- 元素属性给予的效果不是从1级而是从2级开始
- 修复了jei信息在禁用附魔后可能不能正确显示的bug
- 修复了异况超量不是根据目标而是根据攻击者判定的bug

## Note:
- I have rewritten some of the event code. If there are some differences in values compared to before, it is normal, but unexpected situations may also occur. If there are any problems, please let me know

- 我重写了一部分事件的代码,如果数值和以前相比有一些差异是正常的,但也可能出现意料之外的情况,请向我提issue


# 1.1.1
## fix:
- Fixed the crash caused by the conflict between Unyielding Spirit and SlashBlade Resharped
- Fixed the problem that the equipment of Armor Forging could not be stacked

- 修复了不屈的精神和拔刀剑重锋冲突崩溃的问题
- 修复了百炼锻甲术装备间不能叠加的问题

# 1.1.0
## add:
- Cracked Crown (helmet,curse):Enhance the damage caused and received
- 破碎皇冠(头盔,诅咒):提升造成和受到的伤害

## change:
- Fearless Challenger is Special Loot now.
- 无畏的挑战者附魔现在是特殊战利品附魔.

## fix:
- Fixed bug where element stats cannot be calculated correctly when discarding items
- Fixed the issue where the speed effect of element stats cannot be overlaid

- 修复了丢弃物品时元素属性不能正确计算的bug
- 修复元素属性的速度效果无法叠加的问题

# 1.0.0
从1.19.2移植
## add:
- Streamline (bow): Speeds up arrows and ignores gravity
- Heavy Arrow (bow): Increases arrow damage and knockback and applies slowness
- Downwind (weapon): Attacks apply an upward force to the target, causing more damage to targets in the air
- Dexterity (weapon): Increases attack range
- Nightmare (helmet): When going to sleep, an explosion always occurs

- 流线型(弓):加速箭矢,并无视重力
- 重箭(弓):增加箭矢伤害和击退并施加缓慢
- 顺风球(武器):攻击会对目标施加向上的力,对在空中的目标造成更多伤害
- 灵巧(武器):增加攻击距离
- 噩梦(头盔):当要睡觉时,总是会发生爆炸