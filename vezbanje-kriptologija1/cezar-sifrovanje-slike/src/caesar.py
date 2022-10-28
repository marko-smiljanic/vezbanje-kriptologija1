#Funkcije su prilagodjene za rad sa slikama, znaci karakteri koje dobijemo kao rezultat primenom base 64 algoritma.
#Funkcija ima mogucnost rada sa    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+=/"  karakterima, dakle nemamo razmak, opet napominjem prilagodjeno karakterima iz zapisa slika!
#Ovde od klasicne cezarove sifre za npr. UPPER CASE karaktere cu da izmenim da bukavno npr. broj moze da bude zamenjen slovom i li obrnuto (tj. da neki karakter u predefinisanom alfabetu moze biti zamenjen bilo kojim karakterom iz istog tog alfabeta)
#KLJUCEVI SU: s (pomeraji) - iz parametra, alfabet definisan unutar funkcija

def encrypt(text, s):
    result = ""
    
    dozvoljeni_znakovi = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+=/"    #len je 65, indeks poslednjeg elementa je 64 
    dozvoljeni_znakovi_niz = []                                                                 

    if (s > 64 or s < 1):
        return "Nije moguce zadati vise pomeraja od 64 i manje od 1"                #mada nema ni smisla zadati bas 64 jer ce se onda dobiti ista poruka

    for i in range(len(dozvoljeni_znakovi)):                                        #pravim niz karaktera od mojih dozvoljenih znakova
        dozvoljeni_znakovi_niz.append(dozvoljeni_znakovi[i])


    for i in range(len(text)):
        char = text[i]
        for j in range(len(dozvoljeni_znakovi_niz)):                            
            if(dozvoljeni_znakovi_niz[j] == char):                              
                if(j + s > 64):                                                     #ako je pozicija karaktera u nizu + pomeraj veci od 64 onda krecemo od pocetka i brojimo pomeraje
                    temp = (j + s) - 64
                    result += dozvoljeni_znakovi_niz[temp - 1]                      #[temp - 1] se pise jer ako ne bi bilo - 1 pomeraj bi bio 3, ali i indeks bi bio 3, a to je kad se krene od pocetka to je 4ti element po redu a ne treci tj. pomeraj bi ipak bio 4
                else:
                    result += dozvoljeni_znakovi_niz[j + s]
        
    return result


def decrypt(text, s):
    result = ""
    
    dozvoljeni_znakovi = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+=/"
    dozvoljeni_znakovi_niz = []                                                                 

    if (s > 64 or s < 1):
        return "Nije moguce zadati vise pomeraja od 64 i manje od 1"

    for i in range(len(dozvoljeni_znakovi)):                                 
        dozvoljeni_znakovi_niz.append(dozvoljeni_znakovi[i])


    for i in range(len(text)):
        char = text[i]
        for j in range(len(dozvoljeni_znakovi_niz)):                                        
            if(dozvoljeni_znakovi_niz[j] == char):
                if(j - s < 0):                                                      #ako je pozicija karaktera u nizu - pomeraj manja od 0 onda krecemo od indeksa 64, tj. kraja
                    temp = (j - s) + 64
                    result += dozvoljeni_znakovi_niz[temp + 1]                      #ovo [temp + 1] ide jer je bez toga samo indeks, a ne koji je po redu (u ovom slucaju od pozadi)
                else:
                    result += dozvoljeni_znakovi_niz[j - s]
            
    return result


#provera da li funkcije rade
# text = "=/neka+poruka/KOJA=imitira+ZAPIS/1234=Slike"
# s=3

# sifrovana_poruka =  encrypt(text, s)
# desifrovana_poruka = decrypt(sifrovana_poruka, s)

# print("Tekst: ", text)
# print("Sifrovano: ", sifrovana_poruka)
# print("Desifrovano: ", desifrovana_poruka)

# if(text == desifrovana_poruka):
#     print()
#     print("Izvorni i desifrovani tekst su jednaki.")
# else:
#     print()
#     print("Izvorni i desifrovani tekst nisu jednaki.")










#################################################################################################
#funkcija koju sam kopirao sa interneta
#A python program to illustrate Caesar Cipher Technique
# def encrypt(text,s):
#     result = ""
 
#     # traverse text
#     for i in range(len(text)):
#         char = text[i]
 
#         # Encrypt uppercase characters
#         if (char.isupper()):
#             result += chr((ord(char) + s-65) % 26 + 65)   #zbog cega ide ovo s-65?
 
#         # Encrypt lowercase characters
#         else:
#             result += chr((ord(char) + s - 97) % 26 + 97)
 
#     return result
 


# text = "ATTACKATONCE"
# s = 4
# print ("Text  : " + text)
# print ("Shift : " + str(s))
# print ("Cipher: " + encrypt(text,s))


#######################################################################
#funkcija koju sam ja modifikovaom ali sam uvideo da nece bas moci da radi tako kao sto sam zamislio, potreban je potpuno drugi pristup
#s parametar je pomeraj(int)

# def encrypt(text, s):
#     result = ""
 
#     for i in range(len(text)):
#         char = text[i]
 
#         #uppercase characters
#         if (char.isupper()):
#             if (ord(char) + s > 90):                                #ovo je isto kao i chr((ord(char) + s-65) % 26 + 65)
#                 temp = (ord(char) + s) - 90
#                 result += chr(temp + 64)                            #posto velika slova u ascii krecu od 65, moramo da krenemo od 64 da ne bi smo preskocili A(koji je na ord 65)
#             else:
#                 result += chr(ord(char) + s)

#         #lowercase characters
#         elif (char.islower()):
#             if(ord(char) + s > 122):
#                 temp = (ord(char) + s) - 122
#                 result += chr(temp + 96)
#             else:
#                 result += chr(ord(char) + s)
            
#         elif (char.isnumeric()):                                    #brojevi u ascii idu od 48 do 57 (zaljucno sa njima)
#             if(ord(char) + s > 57):
#                 temp = (ord(char) + s) - 57
#                 result += chr(temp + 47)
#             else:
#                 result += chr(ord(char) + s)

#         #ovde sam shavio da ovo bas nece raditi ako su pomeraji veci od 10 (zbog brojeva) 
#         #a kako bih tek odradio do kraja za +, /, = koje bi tu pomeraje stavio na ovaj nacin? na ovaj nacin kako sam krenuo pomeraji bi bili 1 zbog ovih znakova, sto nema smisla!!!!!!!!
 
#     return result


#########################################
#ovako otvaram sliku, treba paziti na putanje jer mora ici druga kosa crta!!!!!

# with open('v3z1\src\Capture.PNG', 'rb') as binary_file:               #moram da zadam putanju ne mogu staviti samo ime slike.png !!!!!!
#     binary_file_data = binary_file.read()                             #rb je oznaka read binary, wb bi bilo write binary!!!!
#     base64_encoded_data = base64.b64encode(binary_file_data)
#     base64_message = base64_encoded_data.decode('utf-8')

#     print(base64_message)
###########################################


#### konacna resenja za encryptr i decrypt, ovo je produzena verzija koja je bila oslonjena na prethodne pokusaje implementacije ovoga
#posle sam uvideo da nema potrebe raditi na ovakav nacin, tj. da nemam posebnu proveru da li je karakter malo, veliko slovo broj itd.
#jer vec ako imam predefinisani alfabet onda prolazim kroz njega celog i nema potrebe da razdvajam provere kao sto bi morao da sam radio po ascii tabeli.




# def encrypt(text, s):
#     result = ""
    
#     dozvoljeni_znakovi = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+=/"    #len je 65, indeks poslednjeg elementa je 64 
#     dozvoljeni_znakovi_niz = []                                                                 
#                                                                                                 #VELIKA SLOVA: pocetni indeks [0], poslednjeg [25] 
#                                                                                                 #MALA SLOVA: pocetni indeks[26], poslednjeg[51]
#                                                                                                 #BROJEVI: pocetni indeks[52], zavrsni[61]
#                                                                                                 #+: indeks[62]
#                                                                                                 #=: indeks[63]
#                                                                                                 #/: indeks[64]

#     if (s > 64 or s < 1):
#         return "Nije moguce zadati vise pomeraja od 64 i manje od 1"            #mada nema ni smisla zadati bas 64 jer ce se onda dobiti ista poruka

#     for i in range(len(dozvoljeni_znakovi)):                                    #pravim niz karaktera od mojih dozvoljenih znakova
#         dozvoljeni_znakovi_niz.append(dozvoljeni_znakovi[i])

    
#     for i in range(len(text)):
#         char = text[i]

#         if(char.isupper()):
#             for j in range(0, 26):                                              #poslednji indeks za velika slova je 25, tako da sam ovde stavio 26 jer ide od 0, tj. prosledjena vrednost - 1
#                 if(dozvoljeni_znakovi_niz[j] == char):
#                     if(j + s > 64):                                             #ako je pozicija karaktera u nizu + pomeraj veci od 64 onda krecemo od pocetka i brojimo pomeraje
#                         temp = (j + s) - 64
#                         result += dozvoljeni_znakovi_niz[temp - 1]             #[temp - 1] jer ako ne bi ovo islo pomeraj bi bio 3, ali i indeks bi bio 3 a kad se krene od pocetka to je 4ti element po redu a ne treci
#                     else:
#                         result += dozvoljeni_znakovi_niz[j + s]
                
#         elif(char.islower()):
#             for j in range(26, 52):
#                 if(dozvoljeni_znakovi_niz[j] == char):
#                     if(j + s > 64):
#                         temp = (j + s) - 64
#                         result += dozvoljeni_znakovi_niz[temp - 1]
#                     else:
#                         result += dozvoljeni_znakovi_niz[j + s]
        
#         elif(char.isnumeric()):
#             for j in range(52, 62):
#                 if(dozvoljeni_znakovi_niz[j] == char):
#                     if(j + s > 64):
#                         temp = (j + s) - 64
#                         result += dozvoljeni_znakovi_niz[temp - 1]
#                     else:
#                         result += dozvoljeni_znakovi_niz[j + s]
        
#         elif(char == "+"):
#             if(62 + s > 64):
#                 temp = (62 + s) - 64
#                 result += dozvoljeni_znakovi_niz[temp - 1]
#             else:
#                 result += dozvoljeni_znakovi_niz[62 + s]
        
#         elif(char == "="):
#             if(63 + s > 64):                                        #ovde i dalje postoji sansa da pomeraj moze biti 1 a to nije vece od 64
#                 temp = (63 + s) - 64
#                 result += dozvoljeni_znakovi_niz[temp - 1]
#             else:
#                 result += dozvoljeni_znakovi_niz[63 + s]

#         elif(char == "/"):                                          #ovde ne moram raditi proveru jer ce svakako 64 + nesto biti vece od 64.                           
#             temp = (64 + s) - 64
#             result += dozvoljeni_znakovi_niz[temp - 1]

 
#     return result



# def decrypt(text, s):
#     result = ""
    
#     dozvoljeni_znakovi = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+=/"    #len je 65, indeks poslednjeg elementa je 64 
#     dozvoljeni_znakovi_niz = []                                                                 
#                                                                                                 #VELIKA SLOVA: pocetni indeks [0], poslednjeg [25] 
#                                                                                                 #MALA SLOVA: pocetni indeks[26], poslednjeg[51]
#                                                                                                 #BROJEVI: pocetni indeks[52], zavrsni[61]
#                                                                                                 #+: indeks[62]
#                                                                                                 #=: indeks[63]
#                                                                                                 #/: indeks[64]

#     if (s > 64 or s < 1):
#         return "Nije moguce zadati vise pomeraja od 64 i manje od 1"

#     for i in range(len(dozvoljeni_znakovi)):                                 
#         dozvoljeni_znakovi_niz.append(dozvoljeni_znakovi[i])


#     for i in range(len(text)):
#         char = text[i]

#         if(char.isupper()):
#             for j in range(0, 26):                                        
#                 if(dozvoljeni_znakovi_niz[j] == char):
#                     if(j - s < 0):                                                  #ako je pozicija karaktera u nizu - pomeraj manja od 0 onda krecemo od indeksa 64, tj. kraja
#                         temp = (j - s) + 64
#                         result += dozvoljeni_znakovi_niz[temp + 1]                  #ovo [temp + 1] ide jer je bez toga samo indeks, a ne koji je po redu
#                     else:
#                         result += dozvoljeni_znakovi_niz[j - s]
                
#         elif(char.islower()):
#             for j in range(26, 52):
#                 if(dozvoljeni_znakovi_niz[j] == char):
#                     if(j - s < 0):
#                         temp = (j - s) + 64
#                         result += dozvoljeni_znakovi_niz[temp + 1]
#                     else:
#                         result += dozvoljeni_znakovi_niz[j - s]
        
#         elif(char.isnumeric()):
#             for j in range(52, 62):
#                 if(dozvoljeni_znakovi_niz[j] == char):
#                     if(j - s < 0):
#                         temp = (j - s) + 64
#                         result += dozvoljeni_znakovi_niz[temp + 1]
#                     else:
#                         result += dozvoljeni_znakovi_niz[j - s]
        
#         elif(char == "+"):
#             if(62 - s < 0):
#                 temp = (62 - s) + 64
#                 result += dozvoljeni_znakovi_niz[temp + 1]
#             else:
#                 result += dozvoljeni_znakovi_niz[62 - s]
        
#         elif(char == "="):
#             if(63 - s < 0):        
#                 temp = (63 - s) + 64
#                 result += dozvoljeni_znakovi_niz[temp + 1]
#             else:
#                 result += dozvoljeni_znakovi_niz[63 - s]

#         elif(char == "/"):                                                      #ovde mi ne trebaju dodatne provere jer 64 - 64 nikad ne mogu biti manji od 0  
#                 result += dozvoljeni_znakovi_niz[64 - s]

 
#     return result

