import base64
from caesar import *


#ucitavamo sliku
with open('v3z1\slike\slika1.PNG', 'rb') as binary_file:                 #da bih mogao da radim sa slikom moram da je konvertujem u tekst preko base64 algoritma
    binary_file_data = binary_file.read()
    base64_encoded_data = base64.b64encode(binary_file_data)
    base64_message = base64_encoded_data.decode('utf-8')
    binary_file.close()
    #print(base64_message)

poruka = base64_message                                                   #x su pomeraji, odnosno kljuc. Alfabet je predefinisan unutar funkcije i prilagodjen je za slike
x = 3                                                                    

sifrovana_poruka = encrypt(poruka, x)
desifrovana_poruka = decrypt(sifrovana_poruka, x)


bajtovi = base64.b64decode(desifrovana_poruka)

novi_fajl = open ("v3z1\slike\slikaPosleSifrovanja.png", "wb")
novi_fajl.write(bajtovi)
novi_fajl.close()


#ako je sifrovanje uspesno kreirace se fajl slikaPosleSifrovanja.png i kad se pokrene to ce biti ta slika


#bajtovi = base64.b64decode(base64_message)      #proba da li radi fajl i provera sta se posle ucitavanja stvarno nalazi kada primenimo base64
