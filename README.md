# umapyoi
An attempt at a Forge 1.19.2 port of Umapyoi mod.

以《赛马娘 Pretty Derby》为主题的“玩家育成”模组。  
准备中。  
***
## 属性设定  
| 英文名 | 中文名 | 描述 |
| :----: | :----: | :----: |
| speed | 速度 | 决定玩家移动速度 |
| stamina | 耐力/持久力 | 决定玩家额外血量以及饱食度消耗速度 |
| strength | 力量 | 决定玩家攻击力 |
| mentality | 毅力/根性/意志力 | 决定玩家的额外护甲韧性 |
| wisdom | 智力 | 决定玩家技能学习的成功率以及主动技能的使用效果。 |
  
最大属性上限根据自身因子水平决定。  
在决定结束育成前，该马魂不可作为种马使用。  
***  
## 马娘与服装
每一个马娘都会有对应的“马娘之魂”，作为灵魂和模型的载体。  
马娘具有具体的上限和初始属性，到达上限之后属性将不再增加。  
属性具有临界值，根据当前服务器配置决定。属性超过临界值后会衰减特定比例，同样根据配置决定。  
马娘可以装备不同的服饰，部分服饰也会限定专属马娘。类型如下表。  
| 类型 | 加成 | 描述 |
| :----: | :----: | :----: |
| 常服 | 无加成 | 没有任何加成的时装类服饰，仅供装饰。 |
| 训练服 | 训练加成，全属性减益 | 装备训练服时才能开始训练提升属性，其余时间无法通过任何训练增强属性。 |
| 决胜服 | 根据具体定义进行属性增益 | 每一件不同的决胜服都会有不同的属性增益 |  

初始模型为马娘的默认决胜服，没有属性增益。  
训练加成计算：若未装备训练服，训练加成固定为0；若装备训练服，训练加成 = 马娘本体训练加成 + 训练服训练加成。  
单次训练 * 总训练加成 = 实际加成数值。
例如：速子的速度训练加成 = 速子本体的速度训练加成0.2 + 特雷森训练服加成1.0 = 1.2，单次训练效果5 * 总加成1.2 = 6。  
决胜服除了属性增益外还可能有自带的专有技能可供学习。  
***  
## 模型骨骼要求  
除特别强调之外，骨骼的父子关系可以任意处理。  
以BB中基岩版模型X轴正方向为右方向，具体参考目前的模型。  
   
| 骨骼名 | 中文名 | 描述 |
| :----: | :----: | :----: |
| head | 头 | 头部骨骼，包括眼镜但不包括帽子等头饰 |
| hat | 头饰 | 头饰骨骼，**与head平级**，玩家戴上头盔时不显示此部分骨骼 |
| long_hair | 长发 | 长发骨骼，父骨骼为head，用于处理模型的长发效果问题 |
| right_ear | 右马耳 | 右马耳骨骼，父骨骼为head，用于处理动画效果 |
| left_ear | 左马耳 | 左马耳骨骼，父骨骼为head，用于处理动画效果 |
| right_ear_hide_parts | 右马耳罩 | 父骨骼为head，用于处理动画效果，玩家不戴上头盔或不被服饰覆盖时隐藏对应马耳！ |
| left_ear_hide_parts | 左马耳罩 | 父骨骼为head，用于处理动画效果，玩家不戴上头盔或不被服饰覆盖时隐藏对应马耳！ |
| body | 胸部 | 除了四肢和头之外的身体部分 |
| hide_parts | 身体隐藏部 | 父骨骼为body，玩家穿上胸甲时不显示此部分骨骼 |
| right_arm | 右臂 | 右臂主骨骼 |
| left_arm | 左臂 | 左臂主骨骼 |
| right_arm_down | 右小臂 | 右臂下半部分骨骼，父骨骼为right_arm，用于处理动画效果 |
| left_arm_down | 左小臂 | 左臂下半部分骨骼，父骨骼为left_arm，用于处理动画效果 |
| tail | 马尾 | 马尾主骨骼 |
| tail_down | 马尾下半部分 | 马尾下半部分骨骼，父骨骼为tail，用于处理动画效果 |
| right_leg | 右腿 | 右腿主骨骼 |
| left_leg | 左腿 | 左腿主骨骼 |
| right_leg_down | 右小腿 | 右臂下半部分骨骼，父骨骼为right_leg，用于处理动画效果 |
| left_leg_down | 左小腿 | 左臂下半部分骨骼，父骨骼为left_leg，用于处理动画效果 |
| right_foot | 右脚 | 右脚骨骼，父骨骼为right_leg，玩家穿上靴子时不显示此部分骨骼 |
| left_foot | 左脚 | 左脚骨骼，父骨骼为left_leg，玩家穿上靴子时不显示此部分骨骼 |
| right_leg_hide_parts | 右腿隐藏部 | 父骨骼为right_leg，玩家穿上护腿时不显示此部分骨骼 |
| left_leg_hide_parts | 左腿隐藏部 | 父骨骼为left_leg，玩家穿上护腿时不显示此部分骨骼 |
