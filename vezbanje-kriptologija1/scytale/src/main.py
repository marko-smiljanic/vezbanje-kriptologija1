from Scytale import *

poruka = "Ovo je neka poruka za sifrovanje"
print("poruka koja se sifruje: ", poruka)

sifrovano = ScytaleCipher.encrypt(poruka, 10)               #prosledjena izvorna poruka na sifrovanje
print("poruka sifrovana: ", sifrovano)

desifrovano = ScytaleCipher.decrypt(sifrovano, 10)          #prosledjena sifrovana poruka (sifrat) na desifrovanje
print("poruka desifrovana: ", desifrovano)

