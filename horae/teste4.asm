@ /0
JP INICIO_0
0_TESTE + FALSE ; NOP
SC POP
LD TEMP
MM D
LD EA_1
PD /0100
W_2 + FALSE ; NOP
LD D
-  EA_2
JN CO_2
LD FALSE
JP CO_3
CO_2 LD TRUE
CO_3 MM CO_1
JZ W_1
LD EA_3
PD /0100
LD D
+  EA_4
MM EA_5
LD EA_5
MM D
JP W_2
W_1 + FALSE ; NOP
LD D
MM RETORNO
RS 0_TESTE
INICIO_0 + FALSE ; NOP
LD EA_6
MM B
LD EA_7
MM E
W_4 + FALSE ; NOP
LD B
-  EA_8
JN CO_5
LD FALSE
JP CO_6
CO_5 LD TRUE
CO_6 MM CO_4
JZ W_3
LD EA_9
PD /0100
LD B
+  EA_10
MM EA_11
LD EA_11
MM B
LD B
MM TEMP
SC PUSH
SC 0_TESTE
LD RETORNO
MM EA_12
LD EA_12
MM E
JP W_4
W_3 + FALSE ; NOP
HM /00
B K /0000
E K /0000
D K /0000
EA_1 K /002e
EA_2 K /000a
CO_1 K /0000
EA_3 K /003a
EA_4 K /0001
EA_5 K /0000
EA_6 K /0001
EA_7 K /0006
EA_8 K /000a
CO_4 K /0000
EA_9 K /002e
EA_10 K /0001
EA_11 K /0000
EA_12 K /0000
TRUE K /0001
FALSE K /0000
CONST_SHIFT K /0100
PUSH LD PONTEIRO_TOPO
+ INST_MM
MM GRAVAR
LD TEMP
GRAVAR 	K /0000
LD PONTEIRO_TOPO
+ DOIS
MM PONTEIRO_TOPO
RS PUSH
POP	LD PONTEIRO_TOPO
- DOIS
MM PONTEIRO_TOPO
+ INST_LD
MM LER
LER 	K /0000
MM TEMP
RS POP
DOIS K /0002
INST_LD LD /0000
INST_MM MM /0000
PONTEIRO_TOPO K TOPO
RETORNO K /0000
TEMP K /0000
TOPO K /0000