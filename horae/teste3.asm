@ /0
LD EA_1
MM K
W_2 + FALSE ; NOP
LD K
-  EA_2
JZ CO_2
LD TRUE
CO_2 MM CO_1
JZ W_1
LD K
PD /0100
LD K
+  EA_3
MM EA_4
LD EA_4
MM K
LD EA_5
MM J
W_4 + FALSE ; NOP
LD EA_6
-  J
JN CO_4
LD FALSE
JP CO_5
CO_4 LD TRUE
CO_5 MM CO_3
JZ W_3
LD EA_7
PD /0100
LD J
-  EA_8
MM EA_9
LD EA_9
MM J
JP W_4
W_3 + FALSE ; NOP
JP W_2
W_1 + FALSE ; NOP
HM /00
K K /0000
J K /0000
EA_1 K /0030
EA_2 K /003a
CO_1 K /0000
EA_3 K /0001
EA_4 K /0000
EA_5 K /0003
EA_6 K /0000
CO_3 K /0000
EA_7 K /002e
EA_8 K /0001
EA_9 K /0000
TRUE K /0001
FALSE K /0000
CONST_SHIFT K /0100