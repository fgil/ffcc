@ /0
LD EA_1
MM K
GD /0000
/ CONST_SHIFT
MM J
LD K
+  CO_1
MM CO_2
JZ IF_1
LD EA_2
MM J
JP IF_2
IF_1 + FALSE ; NOP
LD J
-  EA_3
JZ CO_4
LD FALSE
JP CO_5
CO_4 LD TRUE
CO_5 MM CO_3
JZ IF_3
LD EA_4
MM J
IF_3 + FALSE ; NOP
IF_2 + FALSE ; NOP
LD J
PD /0100
HM /00
K K /0000
J K /0000
EA_1 K /0001
CO_1 K /0000
CO_2 K /0000
EA_2 K /0003
EA_3 K /0004
CO_3 K /0000
EA_4 K /0005
TRUE K /0001
FALSE K /0000
CONST_SHIFT K /0100
